package NameLater.GameStateM;

import NameLater.GameConfigM;
import NameLater.GamePlayersM;
import Snakes.GameState;

import java.util.ArrayList;
import java.util.List;

public class GameStateM {
    private int stateOrder;
    private List<SnakeM> snakes;
    private List<CoordM> foods;
    private GamePlayersM players;
    private GameConfigM config;

    public GameState getMessage(){
        List<GameState.Snake> snakes = new ArrayList<>();
        this.snakes.forEach(snake -> snakes.add(snake.getMessage()));
        List<GameState.Coord> foods = new ArrayList<>();
        this.foods.forEach(food -> foods.add(food.getMessage()));
        return GameState.newBuilder()
                .setStateOrder(stateOrder)
                .addAllSnakes(snakes)
                .addAllFoods(foods)
                .setPlayers(players.getMessage())
                .setConfig(config.getMessage())
                .build();
    }

    public static GameStateM getFromMessage(GameState message){
        List<SnakeM> snakes = new ArrayList<>();
        message.getSnakesList().forEach(snake -> snakes.add(SnakeM.getFromMessage(snake)));
        List<CoordM> foods = new ArrayList<>();
        message.getFoodsList().forEach(food -> foods.add(CoordM.createFromMessage(food)));
        return getBuilder()
                .setStateOrder(message.getStateOrder())
                .setSnakes(snakes)
                .setFoods(foods)
                .setPlayers(GamePlayersM.getFromMessage(message.getPlayers()))
                .setConfig(GameConfigM.createFromMessage(message.getConfig()))
                .build();
    }

    public int getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(int stateOrder) {
        this.stateOrder = stateOrder;
    }

    public List<SnakeM> getSnakes() {
        return snakes;
    }

    public void setSnakes(List<SnakeM> snakes) {
        this.snakes = snakes;
    }

    public List<CoordM> getFoods() {
        return foods;
    }

    public void setFoods(List<CoordM> foods) {
        this.foods = foods;
    }

    public GamePlayersM getPlayers() {
        return players;
    }

    public void setPlayers(GamePlayersM players) {
        this.players = players;
    }

    public GameConfigM getConfig() {
        return config;
    }

    public void setConfig(GameConfigM config) {
        this.config = config;
    }

    public static Builder getBuilder(){
        return Builder.getBuilder();
    }

    public static final class Builder {
        private int stateOrder;
        private List<SnakeM> snakes;
        private List<CoordM> foods;
        private GamePlayersM players;
        private GameConfigM config;

        private Builder() {
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder setStateOrder(int stateOrder) {
            this.stateOrder = stateOrder;
            return this;
        }

        public Builder setSnakes(List<SnakeM> snakes) {
            this.snakes = snakes;
            return this;
        }

        public Builder setFoods(List<CoordM> foods) {
            this.foods = foods;
            return this;
        }

        public Builder setPlayers(GamePlayersM players) {
            this.players = players;
            return this;
        }

        public Builder setConfig(GameConfigM config) {
            this.config = config;
            return this;
        }

        public GameStateM build() {
            GameStateM gameStateM = new GameStateM();
            gameStateM.setStateOrder(stateOrder);
            gameStateM.setSnakes(snakes);
            gameStateM.setFoods(foods);
            gameStateM.setPlayers(players);
            gameStateM.setConfig(config);
            return gameStateM;
        }
    }
}
