package Network.Client;

import GameModel.GameModel;
import NameLater.*;
import NameLater.GameMessage.AnnouncementMessageM;
import NameLater.GameStateM.GameStateM;
import Network.Constants;
import Network.Net;
import Snakes.GameMessage;
import UI.UI;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class ClientThread implements Runnable{



    public class ServerThread extends Thread{

        private GameModel game;
        private Net net;
        private List<GamePlayerM> players;
        private Timer updateFieldTimer;
        private Timer pingTimer;
        private Timer announcementTimer;
        private GameConfigM config;
        private int idCounter;


        public ServerThread(GameConfigM config){
            players = new ArrayList<>();
            game = new GameModel(config);
            net = Net.getInstance();
            updateFieldTimer = new Timer();
            pingTimer = new Timer();
            announcementTimer = new Timer();
            this.config = config;
            net.setPingDelay(10000);
            UI.getInstance().setId(1);
            idCounter = 2;
        }

        public ServerThread(GameStateM state){
            game = new GameModel(state);
            net = Net.getInstance();
            updateFieldTimer = new Timer();
            pingTimer = new Timer();
            announcementTimer = new Timer();
            this.config = state.getConfig();
            net.setPingDelay(config.getPingDelayMs());
        }

        public void getSteerMsg(GameMessage message, String ip, int port) throws IOException {
            if(message.hasSteer())
                game.setDirection(message.getSenderId(), DirectionM.getFromMessage(message.getSteer().getDirection()));
            net.sendMessage(GameMessage.newBuilder().setAck(GameMessage.AckMsg.newBuilder().build())
                    .setMsgSeq(message.getMsgSeq())
                    .setSenderId(1)
                    .setReceiverId(1)
                    .build(), ip, port, false);
        }

        public void getJoinMsg(GameMessage message, DatagramPacket packet) throws IOException {
            if(message.hasJoin()){
                var join = message.getJoin();
                GamePlayerM player = GamePlayerM.getBuilder()
                        .setName(join.getName())
                        .setId(idCounter++)
                        .setIpAddress(packet.getAddress().getHostAddress())
                        .setPort(packet.getPort())
                        .setRole(NodeRoleM.NORMAL)
                        .setType(PlayerTypeM.HUMAN)
                        .setScore(0)
                        .build();
                if(game.getPlayers().size() == 0){
                    player.setRole(NodeRoleM.MASTER);
                }
                players.add(player);
                if(!join.getOnlyView()) {
                    game.addPlayer(player);
                    net.sendMessage(GameMessage.newBuilder().setAck(GameMessage.AckMsg.newBuilder().build())
                            .setMsgSeq(message.getMsgSeq())
                            .setSenderId(1)
                            .setReceiverId(player.getId())
                            .build(), packet.getAddress().getHostAddress(), packet.getPort(), false);
                }
            }
        }

        @Override
        public void run() {
            updateFieldTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    var msg = game.nextMove();
                    GameStateM gameState = GameStateM.getBuilder()
                            .setConfig(config)
                            .setPlayers(new GamePlayersM(msg.getPlayers()))
                            .setFoods(msg.getFoods())
                            .setSnakes(msg.getSnakes())
                            .setStateOrder(msg.getStateOrder())
                            .build();
                    var message = gameState.getMessage();
                    players.forEach(player ->{
                        try {
                            net.sendMessage(GameMessage.newBuilder()
                                            .setState(GameMessage.StateMsg.newBuilder().setState(message).build())
                                            .setMsgSeq(msgSeq++)
                                            .setSenderId(1)
                                            .setReceiverId(player.getId()).build(), player.getIp_address(), player.getPort(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }, config.getStateDelayMs(), config.getStateDelayMs());
            pingTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //players.forEach();
                }
            }, config.getPingDelayMs(), config.getPingDelayMs());
            announcementTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    var msg = AnnouncementMessageM
                            .getBuilder()
                            .setCan_join(true)
                            .setGameConfig(game.getConfig())
                            .setGamePlayers(new GamePlayersM(game.getPlayers()))
                            .build().getMessage();
                    synchronized (msgSeq){
                        GameMessage message = GameMessage.newBuilder()
                            .setAnnouncement(msg)
                            .setMsgSeq(msgSeq++)
                            .build();
                        try {
                            net.sendMulticast(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 1000, 1000);
        }
    }

    private class GameHolder{
        public GameHolder(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        public String ip;
        public int port;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameHolder that = (GameHolder) o;
            return port == that.port && Objects.equals(ip, that.ip);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ip, port);
        }
    }

    private static ClientThread instance;
    private ServerThread server = null;
    private Long msgSeq;
    private Map<GameHolder, AnnouncementMessageM> ongoingGames = new HashMap<>();
    UI ui;
    boolean inGame;
    private String serverIp;
    private int serverPort;
    private int serverId;
    private int id;

    public static ClientThread getInstance() {
        if (instance == null) {
            instance = new ClientThread();
        }

        return instance;
    }

    private ClientThread(){
        msgSeq = 0L;
        inGame = false;
        ui = UI.getInstance();
        id = 0;
    }

    public void startServer() throws UnknownHostException {
        server = new ServerThread(new GameConfigM());
        server.start();
        serverIp = InetAddress.getLocalHost().getHostAddress();
        serverPort = Net.getInstance().getPort();
        inGame = true;
        id = 1;
    }

    public void startServer(GameStateM state){
        server = new ServerThread(state);
        server.start();
    }

    @Override
    public void run() {

    }

    public void getAnnouncement(DatagramPacket packet) throws InvalidProtocolBufferException {
        byte[] result = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
        GameMessage message = GameMessage.parseFrom(result);
        if(!message.hasAnnouncement())
            return;
        AnnouncementMessageM msg = GameMessageM.getFromMessage(message).getAnnouncementMsg();
        msg.setServerIp(packet.getAddress().getHostAddress());
        msg.setServerPort(packet.getPort());
        ongoingGames.put(new GameHolder(packet.getAddress().getHostAddress(), packet.getPort()), msg);
        ui.updateAnnouncements(ongoingGames.values().toArray(new AnnouncementMessageM[0]));
    }

    public void setId(int id){
        if(this.id == 0) {
            this.id = id;
            ui.setId(id);
            if (!inGame) {
                inGame = true;
            }
        }
    }

    public ServerThread getServer() {
        return server;
    }

    public void getSteer(DirectionM direction){
        if(inGame){
            GameMessage message = GameMessage.newBuilder()
                    .setSteer(GameMessage.SteerMsg.newBuilder().setDirection(direction.getMessage()).build())
                    .setMsgSeq(msgSeq++)
                    .setSenderId(id)
                    .setReceiverId(serverId)
                    .build();
            try {
                Net.getInstance().sendMessage(message, serverIp, serverPort, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void joinServer(AnnouncementMessageM server, boolean onlyView){
        id = 0;
        if(this.server != null) {
            System.out.println(123);
            this.server.interrupt();
        }
        GameMessage message = GameMessage.newBuilder()
                .setJoin(GameMessage.JoinMsg.newBuilder().setName("Игрок").setOnlyView(onlyView).build())
                .setMsgSeq(msgSeq++)
                .build();
        try {
            Net.getInstance().sendMessage(message, server.getServerIp(), server.getServerPort(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverIp = server.getServerIp();
        serverPort = server.getServerPort();
    }

    public void getState(GameMessage message, DatagramPacket packet) throws IOException {
        if(message.hasState()){
            UI.getInstance().updateGame(GameStateM.getFromMessage(message.getState().getState()));
        }
        Net.getInstance().sendMessage(GameMessage.newBuilder()
            .setAck(GameMessage.AckMsg.newBuilder().build())
            .setMsgSeq(message.getMsgSeq())
            .setReceiverId(message.getSenderId())
            .setSenderId(message.getReceiverId())
            .build(), packet.getAddress().getHostAddress(), packet.getPort(), false);
    }
}
