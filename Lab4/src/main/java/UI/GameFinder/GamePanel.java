package UI.GameFinder;

import NameLater.GameMessage.AnnouncementMessageM;
import Network.Client.ClientThread;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final Color BACKGROUNDCOLOR = new Color(0, 56, 255, 94);
    private final Rectangle BOUNDS = new Rectangle(400, 60);

    private final JLabel masterLabel = new JLabel();
    {
        masterLabel.setBounds(10, 10, 100, 20);
        masterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(masterLabel);
    }
    private final JLabel numOfPlayersLabel = new JLabel();
    {
        numOfPlayersLabel.setBounds(110, 10, 20, 20);
        numOfPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(numOfPlayersLabel);
    }
    private final JLabel sizeLabel = new JLabel();
    {
        sizeLabel.setBounds(140, 10, 50, 20);
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(sizeLabel);
    }
    private final JLabel foodLabel = new JLabel();
    {
        foodLabel.setBounds(200, 10, 50, 20);
        foodLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(foodLabel);
    }
    private final JButton enterButton = new JButton("Войти");
    {
        enterButton.setBounds(260, 5, 110, 20);
        enterButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.add(enterButton);
    }
    private final JButton viewButton = new JButton("Наблюдать");
    {
        viewButton.setBounds(260, 30, 110, 20);
        viewButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.add(viewButton);
    }

    public GamePanel(String master, int playersAmount, int width, int height,
                    int foodStatic, float foodPerPlayer, boolean canJoin){
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(BACKGROUNDCOLOR);
        this.setBounds(BOUNDS);
        masterLabel.setText(master);
        numOfPlayersLabel.setText(String.valueOf(playersAmount));
        sizeLabel.setText(width + "x" + height);
        foodLabel.setText(foodStatic + "+" + foodPerPlayer + "x");
        if(!canJoin){
            enterButton.setVisible(false);
        }
    }

    public void setJoinAction(AnnouncementMessageM announcement){
        enterButton.addActionListener((e) -> ClientThread.getInstance().joinServer(announcement, false));
        viewButton.addActionListener((e) -> ClientThread.getInstance().joinServer(announcement, true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.drawLine(0, 59, 400, 59);
        g.drawLine(105, 0, 105, 60);
        g.drawLine(135, 0, 135, 60);
        g.drawLine(195, 0, 195, 60);
        g.drawLine(250, 0, 250, 60);
    }
}
