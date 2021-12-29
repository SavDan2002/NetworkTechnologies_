package NameLater.GameMessage;

import NameLater.GameConfigM;
import NameLater.GamePlayersM;
import Snakes.GameMessage;

public class AnnouncementMessageM {
    private GamePlayersM gamePlayers;
    private GameConfigM gameConfig;
    private boolean canJoin;
    private String serverIp;
    private int serverPort;

    public GamePlayersM getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(GamePlayersM gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public GameConfigM getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfigM gameConfig) {
        this.gameConfig = gameConfig;
    }

    public boolean isCanJoin() {
        return canJoin;
    }

    public void setCanJoin(boolean canJoin) {
        this.canJoin = canJoin;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public GameMessage.AnnouncementMsg getMessage(){
         return GameMessage.AnnouncementMsg.newBuilder()
                .setConfig(gameConfig.getMessage())
                .setPlayers(gamePlayers.getMessage())
                .setCanJoin(canJoin)
                .build();
    }

    public static AnnouncementMessageM getFromMessage(GameMessage.AnnouncementMsg msg){
        return getBuilder()
                .setCan_join(msg.getCanJoin())
                .setGameConfig(GameConfigM.createFromMessage(msg.getConfig()))
                .setGamePlayers(GamePlayersM.getFromMessage(msg.getPlayers()))
                .build();
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static final class Builder {
        private GamePlayersM gamePlayers;
        private GameConfigM gameConfig;
        private boolean canJoin;

        private Builder() {
        }

        public static Builder anAnnouncementMessageM() {
            return new Builder();
        }

        public Builder setGamePlayers(GamePlayersM gamePlayers) {
            this.gamePlayers = gamePlayers;
            return this;
        }

        public Builder setGameConfig(GameConfigM gameConfig) {
            this.gameConfig = gameConfig;
            return this;
        }

        public Builder setCan_join(boolean canJoin) {
            this.canJoin = canJoin;
            return this;
        }

        public AnnouncementMessageM build() {
            AnnouncementMessageM announcementMessageM = new AnnouncementMessageM();
            announcementMessageM.setGamePlayers(gamePlayers);
            announcementMessageM.setGameConfig(gameConfig);
            announcementMessageM.setCanJoin(canJoin);
            return announcementMessageM;
        }
    }
}
