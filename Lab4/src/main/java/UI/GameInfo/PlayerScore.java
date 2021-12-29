package UI.GameInfo;

import javax.swing.*;
import java.awt.*;

public class PlayerScore extends JPanel {
    private final Color BACKGROUND_COLOR = new Color(255, 0, 0, 34);
    private final Rectangle BOUNDS = new Rectangle(260, 30);
    private final JLabel place = new JLabel();
    {
        place.setBounds(5, 5, 20, 20);
        this.add(place);
    }
    private final JLabel name = new JLabel();
    {
        name.setBounds(30, 5, 100, 20);
        this.add(name);
    }
    private final JLabel score = new JLabel();
    {
        score.setBounds(140, 5, 50, 20);
        this.add(score);
    }
    private final JLabel status = new JLabel();
    {
        status.setBounds(200, 5, 60, 20);
        this.add(status);
    }

    public PlayerScore(int place, String name, int score, int id){
        this.setLayout(null);
        this.setBounds(BOUNDS);
        this.setBackground(BACKGROUND_COLOR);
        this.place.setText(String.valueOf(place));
        this.name.setText(name);
        this.score.setText(String.valueOf(score));
        this.status.setText(String.valueOf(id));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, 259, 29);
    }
}
