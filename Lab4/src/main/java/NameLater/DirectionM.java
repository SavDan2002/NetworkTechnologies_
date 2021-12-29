package NameLater;

import Snakes.Direction;
import Snakes.GameMessage;

public enum DirectionM {
    UP, DOWN, LEFT, RIGHT;

    public Direction getMessage(){
        return switch (this){
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
        };
    }

    public static DirectionM getFromMessage(Direction message){
        return switch (message){
            case UP -> UP;
            case RIGHT -> RIGHT;
            case LEFT -> LEFT;
            case DOWN -> DOWN;
        };
    }

    public boolean isOpposite(DirectionM direction){
        return switch (this){
            case UP -> direction.equals(DOWN);
            case DOWN -> direction.equals(UP);
            case LEFT -> direction.equals(RIGHT);
            case RIGHT -> direction.equals(LEFT);
        };
    }
}
