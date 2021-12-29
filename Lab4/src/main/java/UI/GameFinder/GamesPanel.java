package UI.GameFinder;

import NameLater.GameConfigM;
import NameLater.GameMessage.AnnouncementMessageM;
import NameLater.GamePlayerM;
import NameLater.NodeRoleM;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.stream.Stream;


public class GamesPanel extends JPanel{
    private final Color BACKGROUND_COLOR = new Color(200, 255, 190, 250);
    private final Rectangle BOUNDS = new Rectangle(1000, 500, 400, 500);
    private List<GamePanel> games = null;
    private final JLabel masterLabel = new JLabel("Ведущий");
    {
        masterLabel.setBounds(10, 10, 100, 20);
        masterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(masterLabel);
    }
    private final JLabel numOfPlayersLabel = new JLabel("#");
    {
        numOfPlayersLabel.setBounds(110, 10, 20, 20);
        numOfPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(numOfPlayersLabel);
    }
    private final JLabel sizeLabel = new JLabel("Размер");
    {
        sizeLabel.setBounds(140, 10, 50, 20);
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(sizeLabel);
    }
    private final JLabel foodLabel = new JLabel("Еда");
    {
        foodLabel.setBounds(200, 10, 50, 20);
        foodLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(foodLabel);
    }

    public GamesPanel(){
        this.setLayout(null);
        this.setBounds(BOUNDS);
        this.setBackground(BACKGROUND_COLOR);
        games = new ArrayList<>();
    }

    public void updateGames(AnnouncementMessageM[] messages){
        if (games != null) {
            games.forEach(this::remove);
        }
        games = new ArrayList<>();
        for(int i = 0; i < messages.length; i++)
        {
            List<GamePlayerM> players = messages[i].getGamePlayers().getPlayers();
            GamePlayerM[] master = players.stream().filter(player -> player.getRole().equals(NodeRoleM.MASTER)).toList().toArray(new GamePlayerM[0]);
            GameConfigM config = messages[i].getGameConfig();
            boolean canJoin = messages[i].isCanJoin();
            if(master.length > 0)
                games.add(new GamePanel(
                    master[0].getName(), players.size(), config.getWidth(), config.getHeight()
                    , config.getFoodStatic(), config.getFoodPerPlayer(), canJoin));
            else
                games.add(new GamePanel(
                        "", players.size(), config.getWidth(), config.getHeight()
                        , config.getFoodStatic(), config.getFoodPerPlayer(), canJoin));
            games.get(i).setLocation(0, 40 + 60 * i);
            games.get(i).setJoinAction(messages[i]);
            this.add(games.get(i));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, 398, 498);
        g.drawLine(0, 40, 400, 40);
        g.drawLine(105, 0, 105, 40);
        g.drawLine(135, 0, 135, 40);
        g.drawLine(195, 0, 195, 40);
        g.drawLine(250, 0, 250, 40);
    }
}