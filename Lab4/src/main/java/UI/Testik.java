package UI;

import GameModel.GameModel;
import NameLater.*;
import NameLater.GameStateM.CoordM;
import NameLater.GameStateM.GameStateM;
import NameLater.GameStateM.SnakeM;
import Network.Client.ClientThread;
import Network.Client.MulticastReceiver;
import Network.Net;
import Snakes.*;
import UI.UI;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class Testik {

    public static GameModel model;

    public static void main(String[] args) {
        /*GameCfg gameCfg = new GameCfg();
        GameConfig gameConfig = gameCfg.getMessage();
        byte[] binary = gameConfig.toByteArray();
        GameCfg test = GameCfg.createFromBinary(binary);
        System.out.println(test);*/

        GamePlayerM player1 = GamePlayerM.getBuilder()
                .setName("Вова")
                .setId(1)
                .setIpAddress("1.1.1.1")
                .setPort(1234)
                .setRole(NodeRoleM.MASTER)
                .setType(PlayerTypeM.HUMAN)
                .setScore(50)
                .build();
        GamePlayerM player2 = GamePlayerM.getBuilder()
                .setName("Матвей")
                .setId(5)
                .setIpAddress("2.2.2.2")
                .setPort(1234)
                .setRole(NodeRoleM.DEPUTY)
                .setType(PlayerTypeM.HUMAN)
                .setScore(40)
                .build();
        GamePlayerM player3 = GamePlayerM.getBuilder()
                .setName("Данил")
                .setId(9)
                .setIpAddress("3.3.3.3")
                .setPort(1235)
                .setRole(NodeRoleM.NORMAL)
                .setType(PlayerTypeM.HUMAN)
                .setScore(60)
                .build();
        List<GamePlayerM> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        GamePlayersM gamePlayers = new GamePlayersM(playerList);
        GameConfigM config = new GameConfigM();
        List<SnakeM> snakes = new ArrayList<>();
        List<CoordM> coords = new ArrayList<>();
        coords.add(0, new CoordM(2, 1));
        coords.add(1, new CoordM(0, 1));
        snakes.add(SnakeM.getBuilder()
                .setPlayerId(1)
                .setPoints(coords)
                .setState(SnakeM.StateM.ALIVE)
                .setDirection(DirectionM.UP)
                .build());
        coords = new ArrayList<>();
        coords.add(0, new CoordM(15, 15));
        coords.add(1, new CoordM(-5, 0));
        coords.add(2, new CoordM(0, 2));
        coords.add(3, new CoordM(10, 0));
        snakes.add(SnakeM.getBuilder()
                .setPlayerId(4)
                .setPoints(coords)
                .setState(SnakeM.StateM.ALIVE)
                .setDirection(DirectionM.RIGHT)
                .build());
        coords = new ArrayList<>();
        coords.add(0, new CoordM(25, 12));
        coords.add(1, new CoordM(20, 0));
        coords.add(2, new CoordM(0, -2));
        coords.add(3, new CoordM(-5, 0));
        snakes.add(SnakeM.getBuilder()
                .setPlayerId(9)
                .setPoints(coords)
                .setState(SnakeM.StateM.ALIVE)
                .setDirection(DirectionM.LEFT)
                .build());
        coords = new ArrayList<>();
        GameStateM gameState = GameStateM.getBuilder()
                .setStateOrder(0)
                .setSnakes(snakes)
                .setFoods(coords)
                .setPlayers(gamePlayers)
                .setConfig(config)
                .build();

        model = new GameModel(gameState);

        /*UI.getInstance().updateGame(gameState);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GameModel.GameModelMessage msg = model.nextMove();

                GameStateM newGameState = GameStateM.getBuilder()
                        .setStateOrder(1)
                        .setSnakes(msg.getSnakes())
                        .setFoods(msg.getFoods())
                        .setPlayers(new GamePlayersM(msg.getPlayers()))
                        .setConfig(msg.getGameConfig())
                        .build();

                UI.getInstance().updateGame(newGameState);
            }
        }, 100, 100);

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GamePlayerM player4 = GamePlayerM.getBuilder()
                .setName("Вася")
                .setId(8)
                .setIpAddress("3.3.3.3")
                .setPort(1235)
                .setRole(NodeRoleM.NORMAL)
                .setType(PlayerTypeM.HUMAN)
                .setScore(0)
                .build();

        System.out.println(model.addPlayer(player4));
         */


        Net net = Net.getInstance();
        net.start();
        ClientThread client = ClientThread.getInstance();
        /**/
        try {
            new MulticastReceiver().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            sleep(5000);
//            System.out.println("ready");
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        GameModel.GameModelMessage msg = model.nextMove();
//
//        GameStateM newGameState = GameStateM.getBuilder()
//                .setStateOrder(1)
//                .setSnakes(msg.getSnakes())
//                .setFoods(msg.getFoods())
//                .setPlayers(new GamePlayersM(msg.getPlayers()))
//                .setConfig(msg.getGameConfig())
//                .build();
//
//        UI.getInstance().updateGame(newGameState);

        /*try {
            sleep(5000);
            System.out.println("ready");
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerList.get(0).setScore(110);
        playerList.get(1).setScore(100);
        playerList.get(2).setScore(90);
        GamePlayerM player4 = GamePlayerM.getBuilder()
                .setName("Миша")
                .setId(8)
                .setIpAddress("5.5.5.5")
                .setPort(1234)
                .setRole(NodeRoleM.NORMAL)
                .setScore(95)
                .setType(PlayerTypeM.HUMAN)
                .build();
        playerList.add(player4);
        gameState.getFoods().remove(0);
        gameState.getFoods().add(new CoordM(5, 25));
        gameState.setPlayers(new GamePlayersM(playerList));
        UI.getInstance().updateGame(gameState);*/
    }
}
