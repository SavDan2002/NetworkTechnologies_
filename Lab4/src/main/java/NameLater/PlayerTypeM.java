package NameLater;

import Snakes.PlayerType;

public enum PlayerTypeM {
    HUMAN, ROBOT;

    public PlayerType getMessage(){
        return switch (this){
            case HUMAN -> PlayerType.HUMAN;
            case ROBOT -> PlayerType.ROBOT;
        };
    }

    public static PlayerTypeM getFromMessage(PlayerType message){
        return switch (message){
            case HUMAN -> HUMAN;
            case ROBOT -> ROBOT;
        };
    }
}
