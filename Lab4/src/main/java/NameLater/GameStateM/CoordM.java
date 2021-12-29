package NameLater.GameStateM;

import Snakes.GameState;
import com.google.protobuf.InvalidProtocolBufferException;

public class CoordM {

    private int x;
    private int y;

    public CoordM(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GameState.Coord getMessage(){
        return GameState.Coord.newBuilder().setX(x).setY(y).build();
    }

    public byte[] getBinary(){
        return getMessage().toByteArray();
    }

    public static CoordM createFromMessage(GameState.Coord coord) {
        return new CoordM(coord.getX(), coord.getY());
    }

    public static CoordM createFromBinary(byte[] binary){
        CoordM result = null;
        try {
            GameState.Coord message = GameState.Coord.parseFrom(binary);
            result = createFromMessage(message);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
