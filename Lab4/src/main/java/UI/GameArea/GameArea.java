package UI.GameArea;

import NameLater.GameStateM.GameStateM;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    public static final Rectangle AREA = new Rectangle(1000, 1000);
    private final Color BACKGROUND_COLOR = new Color(0, 0, 0);
    private Field field = null;

    public GameArea(){
        this.setLayout(null);
        this.setBounds(AREA);
        this.setBackground(BACKGROUND_COLOR);
    }

    public void startGame(int width, int height){
        field = new Field(width, height);
        this.add(field);
    }

    public void updateState(GameStateM state){
        field.updateState(state);
    }

    public void setPlayerId(int id){
        field.setPlayerId(id);
    }

    public void stopGame(){
        this.remove(field);
        field = null;
    }
}
