package Network.Client;

import NameLater.GameMessage.AnnouncementMessageM;
import NameLater.GameMessageM;
import Network.Constants;
import Snakes.GameMessage;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class MulticastReceiver extends Thread{

    private byte[] buffer = new byte[1024];
    MulticastSocket socket;
    private boolean isRunning;

    public MulticastReceiver() throws IOException {
        socket = new MulticastSocket(Constants.MULTICAST_PORT);
        InetAddress group = InetAddress.getByName(Constants.MULTICAST_IP);
        socket.joinGroup(group);
    }

    public void stopRunning(){
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                ClientThread.getInstance().getAnnouncement(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
