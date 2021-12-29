package Network;

import NameLater.GameMessageM;
import Network.Client.ClientThread;
import Snakes.GameMessage;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Net extends Thread{

    private class Message{
        public GameMessage msg;
        String ip;
        int port;
        long sendTime;

        public Message(GameMessage msg, String ip, int port, long sendTime) {
            this.msg = msg;
            this.ip = ip;
            this.port = port;
            this.sendTime = sendTime;
        }
    }

    private DatagramSocket socket;
    private boolean isRunning;
    private static Net instance;
    private byte[] buffer = new byte[1024];
    private Map<Long, Message> ackWait;
    Timer ackTimer;
    private long pingDelay = 10000;

    public int getPort(){
        return socket.getLocalPort();
    }

    public static Net getInstance() {
        if (instance == null) {
            instance = new Net();
        }

        return instance;
    }

    private Net(){
        socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        ackWait = new HashMap<>();
        ackTimer = new Timer(false);
    }

    public void setPingDelay(long pingDelay) {
        this.pingDelay = pingDelay;
        ackTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAck();
            }
        }, pingDelay, pingDelay);
    }

    private void checkAck(){
        ackWait.forEach((seq, message)->{
            if(System.currentTimeMillis() - message.sendTime > pingDelay){
                try {
                    sendMessage(message.msg, message.ip, message.port, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendMessage(GameMessage message, String ip, int port, boolean needsAck) throws IOException {
        DatagramPacket packet = new DatagramPacket(message.toByteArray(), message.getSerializedSize(), InetAddress.getByName(ip), port);
        socket.send(packet);
        if(needsAck)
            ackWait.put(message.getMsgSeq(), new Message(message, ip, port, System.currentTimeMillis()));
    }

    public void sendMulticast(GameMessage message) throws IOException {
        DatagramPacket packet = new DatagramPacket(message.toByteArray(), message.getSerializedSize(), InetAddress.getByName(Constants.MULTICAST_IP), Constants.MULTICAST_PORT);
        socket.send(packet);
    }

    public void stopRunning(){
        isRunning = false;
        socket.close();
    }

    @Override
    public void run() {
        ackTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAck();
            }
        }, pingDelay, pingDelay);
        isRunning = true;
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (isRunning){
            try {
                socket.receive(packet);
                byte[] byteArray = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
                GameMessage gameMessage = GameMessage.parseFrom(byteArray);
                GameMessageM message = GameMessageM.getFromMessage(gameMessage);
                switch (message.getType()) {
                    case ACK -> {
                        ackWait.remove(message.getMesSeq());
                        ClientThread.getInstance().setId(message.getReceiverId());
                    }
                    case STEER -> ClientThread.getInstance().getServer().getSteerMsg(gameMessage, packet.getAddress().getHostAddress(), packet.getPort());
                    case JOIN -> ClientThread.getInstance().getServer().getJoinMsg(gameMessage, packet);
                    case STATE -> ClientThread.getInstance().getState(gameMessage, packet);
                    default -> isRunning = true;
                }
                //do something
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
