package UI.GameArea;

import java.awt.*;

public class SnakeColors {
    public static Color getColor(int id){
        return switch (id%10){
            case 0 -> new Color(255, 0, 0);
            case 1 -> new Color(255, 127, 0);
            case 2 -> new Color(255, 255, 0);
            case 3 -> new Color(180, 255, 0);
            case 4 -> new Color(65, 128, 0);
            case 5 -> new Color(0, 255, 255);
            case 6 -> new Color(0, 0, 255);
            case 7 -> new Color(127, 0, 255);
            case 8 -> new Color(255, 0, 255);
            case 9 -> new Color(255, 127, 127);
            default -> new Color(255, 255, 255);
        };
    }

    public static Color getPlayerColor(){
        return new Color(255, 255, 255);
    }

    public static Color getHeadColor(){
        return new Color(0, 0, 0, 127);
    }

    public static Color getFoodColor() {
        return new Color(189, 28, 105);
    }
}
