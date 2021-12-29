package NameLater;

import NameLater.GameMessage.AnnouncementMessageM;
import NameLater.GameStateM.GameStateM;
import NameLater.GameStateM.SnakeM;
import Snakes.GameMessage;
import Snakes.GameState;

public class GameMessageM {

    public enum Type{
        PING, STEER, ACK, STATE, ANNOUNCEMENT, JOIN, ERROR, ROLE_CHANGE, TYPE_NOT_SET;

        public static Type getFromMessage(GameMessage.TypeCase type){
            return switch (type){
                case PING -> PING;
                case STEER -> STEER;
                case ACK -> ACK;
                case STATE -> STATE;
                case ANNOUNCEMENT -> ANNOUNCEMENT;
                case JOIN -> JOIN;
                case ERROR -> ERROR;
                case ROLE_CHANGE -> ROLE_CHANGE;
                case TYPE_NOT_SET -> TYPE_NOT_SET;
            };
        }
    }

    private Object value;
    private Type type;
    private long mesSeq;
    private int senderId;
    private int receiverId;

    public static GameMessageM getFromMessage(GameMessage message){
        GameMessageM result = new GameMessageM();
        result.type = Type.getFromMessage(message.getTypeCase());
        result.mesSeq = message.getMsgSeq();
        result.senderId = message.getSenderId();
        result.receiverId = message.getReceiverId();

        switch (result.type) {
            case PING -> result.value = message.getPing();
            case STATE -> result.value = message.getState();
            case ACK -> result.value = message.getAck();
            case STEER -> result.value = message.getSteer();
            case ANNOUNCEMENT -> result.value = message.getAnnouncement();
            case JOIN -> result.value = message.getJoin();
            case ERROR -> result.value = message.getError();
            case ROLE_CHANGE -> result.value = message.getRoleChange();
            case TYPE_NOT_SET -> result.value = null;
        }

        return result;
    }

    public AnnouncementMessageM getAnnouncementMsg(){
        if(type == Type.ANNOUNCEMENT){
            return AnnouncementMessageM.getFromMessage((GameMessage.AnnouncementMsg) value);
        }
        else
            return null;
    }

    public GameMessage getMessage(){
        var builder =  GameMessage.newBuilder();
        return builder.build();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getMesSeq() {
        return mesSeq;
    }

    public void setMesSeq(long mesSeq) {
        this.mesSeq = mesSeq;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverID(int receiverId) {
        this.receiverId = receiverId;
    }


}
