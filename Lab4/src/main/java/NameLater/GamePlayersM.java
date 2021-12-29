package NameLater;

import Snakes.GamePlayer;
import Snakes.GamePlayers;

import java.util.ArrayList;
import java.util.List;

public class GamePlayersM {
    private List<GamePlayerM> players;

    public GamePlayersM(List<GamePlayerM> players){
        this.players = players;
    }

    public GamePlayers getMessage(){
        List<GamePlayer> result = new ArrayList<>();
        players.forEach((player) -> result.add(player.getMessage()));
        return GamePlayers.newBuilder().addAllPlayers(result).build();
    }

    public static GamePlayersM getFromMessage(GamePlayers message){
        List<GamePlayerM> result = new ArrayList<>();
        message.getPlayersList().forEach((player) -> result.add(GamePlayerM.getFromMessage(player)));
        return new GamePlayersM(result);
    }

    public List<GamePlayerM> getPlayers() {
        return players;
    }

    public void setPlayers(List<GamePlayerM> players) {
        this.players = players;
    }
}
