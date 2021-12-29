package UI.GameInfo;

import NameLater.GameStateM.GameStateM;
import NameLater.NodeRoleM;

import javax.swing.*;
import java.awt.*;

public class GameRulesPanel extends JPanel {
    private final Color BACKGROUND_COLOR = new Color(255, 250, 162, 89);
    private final Rectangle BOUNDS = new Rectangle(20, 240, 310, 60);
    private final JLabel rules = new JLabel("Правила");
    {
        rules.setBounds(0, 0, 60, 20);
        this.add(rules);
    }
    private final JLabel masterLabel = new JLabel("Ведущий");
    {
        masterLabel.setBounds(5, 20, 125, 20);
        this.add(masterLabel);
    }
    private final JLabel sizeLabel = new JLabel("Размер");
    {
        sizeLabel.setBounds(135, 20, 55, 20);
        this.add(sizeLabel);
    }
    private final JLabel foodLabel = new JLabel("Еда");
    {
        foodLabel.setBounds(190, 20, 50, 20);
        this.add(foodLabel);
    }
    private final JLabel playersLabel = new JLabel("Игроков");
    {
        playersLabel.setBounds(245, 20, 55, 20);
        this.add(playersLabel);
    }
    private final JLabel master = new JLabel();
    {
        master.setBounds(5, 40, 125, 20);
        this.add(master);
    }
    private final JLabel size = new JLabel();
    {
        size.setBounds(135, 40, 55, 20);
        this.add(size);
    }
    private final JLabel food = new JLabel();
    {
        food.setBounds(190, 40, 50, 20);
        this.add(food);
    }
    private final JLabel players = new JLabel();
    {
        players.setBounds(245, 40, 45, 20);
        this.add(players);
    }

    public GameRulesPanel(){
        this.setLayout(null);
        this.setBounds(BOUNDS);
        this.setBackground(BACKGROUND_COLOR);
    }

    public void updateState(GameStateM state){
        state.getPlayers().getPlayers().forEach(player -> {
            if(player.getRole() == NodeRoleM.MASTER)
                master.setText(player.getName());
        });
        size.setText(state.getConfig().getWidth() + "x" + state.getConfig().getHeight());
        food.setText(state.getConfig().getFoodStatic() + "+" + state.getConfig().getFoodPerPlayer() + "x");
        players.setText(state.getPlayers().getPlayers().size() + "");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 20, 309, 39);
        g.drawLine(130, 20, 130, 60);
        g.drawLine(188, 20, 188, 60);
        g.drawLine(243, 20, 243, 60);
        g.drawLine(0, 40, 310, 40);
    }
}
