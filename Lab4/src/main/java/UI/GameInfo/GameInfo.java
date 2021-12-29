package UI.GameInfo;

import NameLater.GameStateM.GameStateM;
import Network.Client.ClientThread;
import UI.GameFinder.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GameInfo extends JPanel {
    private final Color BACKGROUND_COLOR = new Color(255, 162, 162, 255);
    private final Rectangle BOUNDS = new Rectangle(1000, 0, 400, 500);
    private final ScorePanel scorePanel = new ScorePanel();

    {
        this.add(scorePanel);
    }

    private final GameRulesPanel gameRulesPanel = new GameRulesPanel();

    {
        this.add(gameRulesPanel);
    }

    /*private final JButton leaveButton = new JButton("Выйти");

    {
        leaveButton.setBounds(20, 350, 150, 50);
        this.add(leaveButton);
    }*/

    private final JButton spectateButton = new JButton("Наблюдать");
    {
        spectateButton.setBounds(200, 350, 150, 50);
        this.add(spectateButton);
    }

    private final JButton startServerButton = new JButton("Новый сервер");
    {
        startServerButton.setBounds(20, 350, 150, 50);
        startServerButton.addActionListener(a -> {
            try {
                ClientThread.getInstance().startServer();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
        this.add(startServerButton);
    }

    public GameInfo(){
        this.setLayout(null);
        this.setBounds(BOUNDS);
        this.setBackground(BACKGROUND_COLOR);
    }

    public void updateState(GameStateM state){
        scorePanel.updateState(state);
        gameRulesPanel.updateState(state);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, 398, 499);
    }
}
