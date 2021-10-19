package com.company.server;

import com.company.Constants;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Transfer extends Thread{

    private final Socket socket;
    private final DataInputStream socketInput;
    private final DataOutputStream socketOutput;
    private File outFile;
    private long fileSize;
    private final File uploadDir;
    private FileOutputStream fileOutput;
    private int bufferLen;
    private final byte[] byteArray;
    private long bytesGot;

    public Transfer(Socket s, File uploadDir) throws IOException {
        socket = s;
        socketInput = new DataInputStream(s.getInputStream());
        socketOutput = new DataOutputStream(s.getOutputStream());
        byteArray = new byte[Constants.BUF_SIZE];
        this.uploadDir = uploadDir;
        bytesGot = 0;
    }

    private void createFile(String fileName) throws IOException {
        int attempts = 0;
        outFile = new File(uploadDir.getAbsolutePath() + "\\" + fileName);
        String[] fileNameArray = fileName.split("\\.");
        while (!outFile.createNewFile()){
            outFile = new File(uploadDir.getAbsolutePath() + "\\" + fileNameArray[0] + attempts + "." + fileNameArray[1]);
            attempts++;
        }
        fileOutput = new FileOutputStream(outFile);
    }

    private void startDownloading() throws IOException {

        socketOutput.write("start".getBytes());

        while(bytesGot != fileSize){
            bufferLen = socketInput.read(byteArray);
            bytesGot += bufferLen;
            fileOutput.write(byteArray, 0, bufferLen);
        }
    }

    private String getFileName() throws IOException {
        bufferLen = socketInput.read(byteArray);
        String tmp = new String(byteArray, 0, bufferLen);
        while(!(tmp.split("\\?").length == 3 && tmp.endsWith("?"))){
            bufferLen = socketInput.read(byteArray);
            tmp = tmp.concat(new String(byteArray, 0, bufferLen));
        }
        String[] arr = tmp.split("\\?");

        fileSize = Long.parseLong(arr[2]);
        return arr[1];
    }

    private void closeAll(){
        try {
            fileOutput.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishMsg() throws IOException {
        socketOutput.writeBytes("?FINISHED?");
    }

    private void errorMsg() {
        try {
            socketOutput.writeBytes("?ERROR?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SpeedCheck{

        private long prevBytesGot = 0;
        private long lastTimeUpdate;
        private final long timeStart;
        private final String fileName;
        private final Timer timer;
        private final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(fileName + " | " +
                        (float)(bytesGot - prevBytesGot)/((float)(System.currentTimeMillis() - lastTimeUpdate)/1000.0)/1024/1024 + " MB/s"
                        + " | " + (float)bytesGot / ((float)(System.currentTimeMillis() - timeStart) / 1000)/1024/1024 + " MB/s"
                        + " | " + (float)(fileSize - bytesGot)/1024/1024 + " MB left.");
                prevBytesGot = bytesGot;
                lastTimeUpdate = System.currentTimeMillis();
            }
        };

        public SpeedCheck(String fileName){
            this.fileName = fileName;
            timer = new Timer();
            startSpeedCheck();
            lastTimeUpdate = System.currentTimeMillis();
            timeStart = System.currentTimeMillis();
        }

        public void stop(){
            timerTask.run();
            timer.cancel();
        }

        private void startSpeedCheck(){
            lastTimeUpdate = System.currentTimeMillis();
            timer.scheduleAtFixedRate(timerTask, 3000, 3000);
        }
    }

    @Override
    public void run() {
        String fileName;
        try {
            fileName = getFileName();
            createFile(fileName);
            SpeedCheck speed = new SpeedCheck(fileName);
            startDownloading();
            finishMsg();
            speed.stop();
            closeAll();
        } catch (IOException e) {
            errorMsg();
            closeAll();
            e.printStackTrace();
        }
        System.out.println("Thread closed");
    }

}
