package NameLater;

import Snakes.NodeRole;

public enum NodeRoleM {
    NORMAL, MASTER, DEPUTY, VIEWER;

    public NodeRole getMessage(){
        return switch (this){
            case NORMAL -> NodeRole.NORMAL;
            case MASTER -> NodeRole.MASTER;
            case DEPUTY -> NodeRole.DEPUTY;
            case VIEWER -> NodeRole.VIEWER;
        };
    }

    public static NodeRoleM getFromMessage(NodeRole message){
        return switch (message){
            case NORMAL -> NORMAL;
            case MASTER -> MASTER;
            case DEPUTY -> DEPUTY;
            case VIEWER -> VIEWER;
        };
    }
}
