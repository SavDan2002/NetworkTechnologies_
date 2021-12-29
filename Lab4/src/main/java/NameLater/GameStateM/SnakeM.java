package NameLater.GameStateM;

import NameLater.DirectionM;
import Snakes.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SnakeM {

    public enum StateM{
        ALIVE, ZOMBIE;

        public int getValue(){
            return switch (this){
                case ALIVE -> 0;
                case ZOMBIE -> 1;
            };
        }

        public GameState.Snake.SnakeState getMessage(){
            return switch (this){
                case ALIVE -> GameState.Snake.SnakeState.ALIVE;
                case ZOMBIE -> GameState.Snake.SnakeState.ZOMBIE;
            };
        }

        public static StateM getFromMessage(GameState.Snake.SnakeState message){
            return switch (message){
                case ALIVE -> ALIVE;
                case ZOMBIE -> ZOMBIE;
            };
        }
    }

    private int playerId;
    private List<CoordM> points;
    private StateM state;
    private DirectionM direction;

    public GameState.Snake getMessage(){
        List<GameState.Coord> pointList = new ArrayList<>();
        points.forEach((point) -> pointList.add(point.getMessage()));
        return GameState.Snake.newBuilder()
                .setPlayerId(playerId)
                .setState(state.getMessage())
                .addAllPoints(pointList)
                .setHeadDirection(direction.getMessage())
                .build();
    }

    public static SnakeM getFromMessage(GameState.Snake message){
        List<CoordM> points = new ArrayList<>();
        message.getPointsList().forEach(point -> points.add(CoordM.createFromMessage(point)));
        return getBuilder()
                .setPlayerId(message.getPlayerId())
                .setPoints(points)
                .setState(StateM.getFromMessage(message.getState()))
                .setDirection(DirectionM.getFromMessage(message.getHeadDirection()))
                .build();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public List<CoordM> getPoints() {
        return points;
    }

    public void setPoints(List<CoordM> points) {
        this.points = points;
    }

    public StateM getState() {
        return state;
    }

    public void setState(StateM state) {
        this.state = state;
    }

    public DirectionM getDirection() {
        return direction;
    }

    public void setDirection(DirectionM direction) {
        this.direction = direction;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static final class Builder {
        private int playerId;
        private List<CoordM> points;
        private StateM state;
        private DirectionM direction;

        private Builder() {
        }

        public static Builder aSnakeM() {
            return new Builder();
        }

        public Builder setPlayerId(int playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder setPoints(List<CoordM> points) {
            this.points = new ArrayList<>(points);
            return this;
        }

        public Builder setState(StateM state) {
            this.state = state;
            return this;
        }

        public Builder setDirection(DirectionM direction) {
            this.direction = direction;
            return this;
        }

        public SnakeM build() {
            SnakeM snakeM = new SnakeM();
            snakeM.setPlayerId(playerId);
            snakeM.setPoints(points);
            snakeM.setState(state);
            snakeM.setDirection(direction);
            return snakeM;
        }
    }
}
