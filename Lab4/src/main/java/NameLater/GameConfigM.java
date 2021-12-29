package NameLater;

import Snakes.GameConfig;
import com.google.protobuf.InvalidProtocolBufferException;

public class GameConfigM {
    private int width;
    private int height;
    private int foodStatic;
    private float foodPerPlayer;
    private int stateDelayMs;
    private float deadFoodProb;
    private int pingDelayMs;
    private int nodeTimeoutMs;

    public GameConfigM() {
        width = 40;
        height = 40;
        foodStatic = 3;
        foodPerPlayer = 1f;
        stateDelayMs = 100;
        deadFoodProb = 0.25f;
        pingDelayMs = 100;
        nodeTimeoutMs = 800;
    }



    @Override
    public String toString() {
        return "GameCfg{" +
                "width=" + width +
                ", height=" + height +
                ", foodStatic=" + foodStatic +
                ", foodPerPlayer=" + foodPerPlayer +
                ", stateDelayMs=" + stateDelayMs +
                ", deadFoodProb=" + deadFoodProb +
                ", pingDelayMs=" + pingDelayMs +
                ", nodeTimeoutMs=" + nodeTimeoutMs +
                '}';
    }

    public GameConfig getMessage(){
        return GameConfig.newBuilder()
                .setWidth(width)
                .setHeight(height)
                .setFoodStatic(foodStatic)
                .setFoodPerPlayer(foodPerPlayer)
                .setStateDelayMs(stateDelayMs)
                .setDeadFoodProb(deadFoodProb)
                .setPingDelayMs(pingDelayMs)
                .setNodeTimeoutMs(nodeTimeoutMs)
                .build();
    }

    public byte[] getBinary(){
        return getMessage().toByteArray();
    }

    public static GameConfigM createFromMessage(GameConfig gameConfig){
        return GameConfigM.Builder.getBuilder()
                .setWidth(gameConfig.getWidth())
                .setHeight(gameConfig.getHeight())
                .setFoodStatic(gameConfig.getFoodStatic())
                .setFoodPerPlayer(gameConfig.getFoodPerPlayer())
                .setStateDelayMs(gameConfig.getStateDelayMs())
                .setDeadFoodProb(gameConfig.getDeadFoodProb())
                .setPingDelayMs(gameConfig.getPingDelayMs())
                .setNodeTimeoutMs(gameConfig.getNodeTimeoutMs())
                .build();
    }

    public static GameConfigM createFromBinary(byte[] binary){
        GameConfigM gameConfigM = null;
        try {
            GameConfig gameConfig = GameConfig.parseFrom(binary);
            gameConfigM = createFromMessage(gameConfig);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return gameConfigM;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFoodStatic() {
        return foodStatic;
    }

    public void setFoodStatic(int foodStatic) {
        this.foodStatic = foodStatic;
    }

    public float getFoodPerPlayer() {
        return foodPerPlayer;
    }

    public void setFoodPerPlayer(float foodPerPlayer) {
        this.foodPerPlayer = foodPerPlayer;
    }

    public int getStateDelayMs() {
        return stateDelayMs;
    }

    public void setStateDelayMs(int stateDelayMs) {
        this.stateDelayMs = stateDelayMs;
    }

    public float getDeadFoodProb() {
        return deadFoodProb;
    }

    public void setDeadFoodProb(float deadFoodProb) {
        this.deadFoodProb = deadFoodProb;
    }

    public int getPingDelayMs() {
        return pingDelayMs;
    }

    public void setPingDelayMs(int pingDelayMs) {
        this.pingDelayMs = pingDelayMs;
    }

    public int getNodeTimeoutMs() {
        return nodeTimeoutMs;
    }

    public void setNodeTimeoutMs(int nodeTimeoutMs) {
        this.nodeTimeoutMs = nodeTimeoutMs;
    }

    public static final class Builder {
        private int width;
        private int height;
        private int foodStatic;
        private float foodPerPlayer;
        private int stateDelayMs;
        private float deadFoodProb;
        private int pingDelayMs;
        private int nodeTimeoutMs;

        private Builder() {
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFoodStatic(int foodStatic) {
            this.foodStatic = foodStatic;
            return this;
        }

        public Builder setFoodPerPlayer(float foodPerPlayer) {
            this.foodPerPlayer = foodPerPlayer;
            return this;
        }

        public Builder setStateDelayMs(int stateDelayMs) {
            this.stateDelayMs = stateDelayMs;
            return this;
        }

        public Builder setDeadFoodProb(float deadFoodProb) {
            this.deadFoodProb = deadFoodProb;
            return this;
        }

        public Builder setPingDelayMs(int pingDelayMs) {
            this.pingDelayMs = pingDelayMs;
            return this;
        }

        public Builder setNodeTimeoutMs(int nodeTimeoutMs) {
            this.nodeTimeoutMs = nodeTimeoutMs;
            return this;
        }

        public GameConfigM build() {
            GameConfigM gameConfigM = new GameConfigM();
            gameConfigM.height = this.height;
            gameConfigM.foodStatic = this.foodStatic;
            gameConfigM.deadFoodProb = this.deadFoodProb;
            gameConfigM.width = this.width;
            gameConfigM.pingDelayMs = this.pingDelayMs;
            gameConfigM.nodeTimeoutMs = this.nodeTimeoutMs;
            gameConfigM.foodPerPlayer = this.foodPerPlayer;
            gameConfigM.stateDelayMs = this.stateDelayMs;
            return gameConfigM;
        }
    }
}
