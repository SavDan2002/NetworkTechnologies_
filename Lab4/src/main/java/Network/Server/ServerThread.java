/*package Network.Server;

import GameModel.GameModel;
import NameLater.*;
import NameLater.GameStateM.GameStateM;
import Network.Net;
import Snakes.GameMessage;

import java.net.DatagramPacket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ServerThread extends Thread{

    GameModel game;
    Net net;
    List<GamePlayerM> players;
    Timer updateFieldTimer;
    Timer pingTimer;
    Timer announcementTimer;
    GameConfigM config;


    public ServerThread(GameConfigM config){
        game = new GameModel(config);
        net = Net.getInstance();
        updateFieldTimer = new Timer();
    }

    public void getSteerMsg(GameMessage message){
        game.setDirection(message.getSenderId(), DirectionM.getFromMessage(message.getSteer().getDirection()));
    }

    /*public void getJoinMsg(GameMessage message, DatagramPacket packet){
        var join = message.getJoin();
        packet.get
    }*/

    /*public void getRoleChangeMsg(GameMessage message){

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

            }
        }, config.getStateDelayMs(), config.getStateDelayMs());
        pingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //players.forEach();
            }
        }, config.getPingDelayMs(), config.getPingDelayMs());
        while(true){

        }
    }
}*/