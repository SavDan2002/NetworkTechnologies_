package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class Server {

        private static Socket clientSocket;
        private static ServerSocket serverSocket;

        public static void main(String[] args) {
            try {
                serverSocket = new ServerSocket(Integer.parseInt(args[0]));
                System.out.println("Server has been started");
            }
            catch (IOException ex){
                ex.printStackTrace();
                return;
            }

            File uploadDir = new File(Paths.get("").toAbsolutePath().toString() + "\\uploads");
            uploadDir.mkdir();
            while(true){
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Starting new download from " + clientSocket.getInetAddress().toString());
                    new Transfer(clientSocket, uploadDir).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
}
