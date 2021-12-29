package UI;

import NameLater.GameMessage.AnnouncementMessageM;
import NameLater.GameStateM.GameStateM;
import UI.GameArea.GameArea;
import UI.GameFinder.GamesPanel;
import UI.GameInfo.GameInfo;

import javax.swing.*;

public class UI {
    private static UI ui;
    private JFrame frame;
    private GamesPanel gamesPanel;
    GameInfo gameInfo = new GameInfo();
    GameArea gameArea = new GameArea();
    KeyboardListen kl = new KeyboardListen();

    public static UI getInstance(){
        if (ui == null) {
            ui = new UI();
        }
        return ui;
    }
    private UI(){
        frame = new JFrame("Test");
        frame.setSize(1400, 1039);
        gamesPanel = new GamesPanel();
        gameArea.startGame(40, 40);
        frame.add(gameArea);
        frame.add(gameInfo);
        frame.add(gamesPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(kl);
    }

    public void updateGame(GameStateM gameState){
        gameArea.updateState(gameState);
        gameInfo.updateState(gameState);
        frame.repaint();
    }

    public void setId(int id){
        gameArea.setPlayerId(id);
    }

    public void updateAnnouncements(AnnouncementMessageM[] announcements){
        gamesPanel.updateGames(announcements);
        frame.repaint();
    }
}
