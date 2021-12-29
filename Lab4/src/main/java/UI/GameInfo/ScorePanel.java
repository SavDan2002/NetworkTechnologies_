package UI.GameInfo;

import NameLater.GamePlayerM;
import NameLater.GameStateM.GameStateM;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScorePanel extends JPanel {
    private final Color BACKGROUND_COLOR = new Color(255, 250, 162, 89);
    private final Rectangle BOUNDS = new Rectangle(20, 20, 260, 200);
    private final JLabel rating = new JLabel("Рейтинг");
    {
        rating.setBounds(0, 0, 60, 20);
        this.add(rating);
    }
    private List<PlayerScore> scores;

    public ScorePanel(){
        this.setLayout(null);
        this.setBounds(BOUNDS);
        this.setBackground(BACKGROUND_COLOR);
    }

    public void updateState(GameStateM state){
        if (scores != null) {
            scores.forEach(this::remove);
        }
        scores = new ArrayList<>();
        List<GamePlayerM> players = new ArrayList<>(state.getPlayers().getPlayers());
        players.sort(Comparator.comparingInt(GamePlayerM::getScore).reversed());
        for (int i = 0; i < players.size(); i++) {
            scores.add(new PlayerScore(i + 1, players.get(i).getName(), players.get(i).getScore(), players.get(i).getId()));
            scores.get(i).setLocation(0, 20 + 30 * i);
            this.add(scores.get(i));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 20, 259, 179);
    }
}
