package NameLater;

import Snakes.GamePlayer;

public class GamePlayerM {
    private String name;
    private int id;
    private String ip_address;
    private int port;
    private NodeRoleM role;
    private PlayerTypeM type;
    private int score;

    public GamePlayer getMessage(){
        return GamePlayer.newBuilder()
                .setName(name)
                .setId(id)
                .setIpAddress(ip_address)
                .setPort(port)
                .setRole(role.getMessage())
                .setType(type.getMessage())
                .setScore(score)
                .build();
    }

    public static GamePlayerM getFromMessage(GamePlayer message){
        return GamePlayerM.getBuilder()
                .setName(message.getName())
                .setId(message.getId())
                .setIpAddress(message.getIpAddress())
                .setPort(message.getPort())
                .setRole(NodeRoleM.getFromMessage(message.getRole()))
                .setType(PlayerTypeM.getFromMessage(message.getType()))
                .setScore(message.getScore())
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NodeRoleM getRole() {
        return role;
    }

    public void setRole(NodeRoleM role) {
        this.role = role;
    }

    public PlayerTypeM getType() {
        return type;
    }

    public void setType(PlayerTypeM type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static Builder getBuilder(){
        return Builder.getBuilder();
    }

    public static final class Builder {
        private String name;
        private int id;
        private String ip_adress;
        private int port;
        private NodeRoleM nodeRole;
        private PlayerTypeM playerType;
        private int score;

        private Builder() {
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setIpAddress(String ip_adress) {
            this.ip_adress = ip_adress;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setRole(NodeRoleM nodeRole) {
            this.nodeRole = nodeRole;
            return this;
        }

        public Builder setType(PlayerTypeM playerType) {
            this.playerType = playerType;
            return this;
        }

        public Builder setScore(int score) {
            this.score = score;
            return this;
        }

        public GamePlayerM build() {
            GamePlayerM gamePlayerM = new GamePlayerM();
            gamePlayerM.id = this.id;
            gamePlayerM.name = this.name;
            gamePlayerM.score = this.score;
            gamePlayerM.role = this.nodeRole;
            gamePlayerM.type = this.playerType;
            gamePlayerM.ip_address = this.ip_adress;
            gamePlayerM.port = this.port;
            return gamePlayerM;
        }
    }
}
