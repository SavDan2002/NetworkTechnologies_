package UI.GameArea;

import NameLater.DirectionM;
import NameLater.GameStateM.CoordM;
import NameLater.GameStateM.GameStateM;
import NameLater.GameStateM.SnakeM;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static UI.GameArea.GameArea.AREA;

public class Field extends JPanel {
    private int cagesize;
    private int width = 0;
    private int height = 0;
    private Rectangle fieldRect;
    private final Color FIELD_OUTLINE_COLOR = new Color(255, 255, 255);
    private final Color FIELD_BACKGROUND_COLOR = new Color(19, 42, 16);
    private GameStateM lastGameState = null;
    private int playerId;
    SnakeM test;

    public Field(int width, int height){
        cagesize = Integer.min(999/width, 999/height);
        this.width = width;
        this.height = height;
        fieldRect = new Rectangle((int) AREA.getWidth() / 2 - width * cagesize / 2
                , (int)AREA.getHeight() / 2 - height * cagesize / 2
                , width* cagesize + 1,
                height* cagesize + 1);
        this.setLayout(null);
        this.setBounds(fieldRect);
        this.setBackground(FIELD_BACKGROUND_COLOR);
        List<CoordM> coords = new ArrayList<>();
        coords.add(0, new CoordM(2, 1));
        coords.add(1, new CoordM(0, 4));
        coords.add(2, new CoordM(-5, 0));
        coords.add(3, new CoordM(0, -7));
        coords.add(4, new CoordM(13, 0));
        coords.add(5, new CoordM(0, 8));
        test = SnakeM.getBuilder()
                .setState(SnakeM.StateM.ALIVE)
                .setDirection(DirectionM.UP)
                .setPlayerId(5)
                .setPoints(coords)
                .build();
        //drawSnake(test, null);
    }

    public void updateState(GameStateM state){
        if (lastGameState != null) {
            synchronized (lastGameState){
                lastGameState = state;
            }
        }
        else
            lastGameState = state;
        this.updateUI();
    }

    public void setPlayerId(int id){
        this.playerId = id;
    }

    private void drawCage(int x, int y, Color color, Graphics g){
        g.setColor(color);
        g.fillRect(x*cagesize, y*cagesize, cagesize, cagesize);
    }

    private void drawHorizontalRect(int x1, int y1, int x2, int y2, Color color, Graphics g){
        int x = x1 < x2 ? cagesize * x1 : cagesize * x2;
        int y = y1 * cagesize + 1;
        int width = x1 < x2 ? cagesize * (x2 - x1 + 1) : cagesize * (x1 - x2 + 1);
        g.setColor(color);
        g.fillRect(x + 1, y, width - 2, cagesize - 2);
    }

    private void drawVerticalRect(int x1, int y1, int x2, int y2, Color color, Graphics g){
        int x = x1 * cagesize + 1;
        int y = y1 < y2 ? cagesize * y1 : cagesize * y2;
        int height = y1 < y2 ? cagesize * (y2 - y1 + 1) : cagesize * (y1 - y2 + 1);
        g.setColor(color);
        g.fillRect(x, y + 1, cagesize - 2, height - 1);
    }

    private void drawSnakePart(int x, int y, int offX, int offY, Color color, Graphics g){
        if(offX != 0){
            if(x + offX < 0){
                drawHorizontalRect(x, y, 0, y, color, g);
                drawHorizontalRect(width - 1, y, width + x + offX + 1, y, color, g);
            }
            else if(x + offX >= width){
                drawHorizontalRect(x, y, width - 1, y, color, g);
                drawHorizontalRect(0, y, x - width + offX, y, color, g);
            }
            else{ // x = 5, offX = 2 => (5, y) (6, y) (7, y) x+offX = 7
                drawHorizontalRect(x, y, x + offX, y, color, g);
            }
        }
        else{
            if(y + offY < 0){
                drawVerticalRect(x, y, x, 0, color, g);
                drawVerticalRect(x, height - 1, x, height + y + offY + 1, color, g);
            }
            else if(y + offY >= height){
                drawVerticalRect(x, y, x, height - 1, color, g);
                drawVerticalRect(x, 0, x, y - height + offY, color, g);
            }
            else{
                drawVerticalRect(x, y, x, y+ offY, color, g);
            }
        }
    }

    private void drawSnake(SnakeM snake, Graphics g){
        int curX = snake.getPoints().get(0).getX(), curY = snake.getPoints().get(0).getY();
        Color color;
        if(snake.getPlayerId() != playerId)
            color = SnakeColors.getColor(snake.getPlayerId());
        else
            color = SnakeColors.getPlayerColor();
        for(int i = 1; i < snake.getPoints().size(); i++){
            int offX = snake.getPoints().get(i).getX();
            int offY = snake.getPoints().get(i).getY();
            drawSnakePart(curX, curY, offX, offY, color, g);
            curX += offX;
            curY += offY;
            if(curX < 0)
                curX = width + curX;
            else if(curX >= width)
                curX = curX - width;
            if(curY < 0)
                curY = height + curY;
            else if(curY >= height)
                curY = curY- height;
        }
        drawCage(snake.getPoints().get(0).getX(), snake.getPoints().get(0).getY(), SnakeColors.getHeadColor(), g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(FIELD_BACKGROUND_COLOR);
        g.fillRect(0, 0
                ,(int)fieldRect.getWidth() - 1, (int)fieldRect.getHeight() - 1);
        g.setColor(FIELD_OUTLINE_COLOR);
        g.drawRect(0, 0
                ,(int)fieldRect.getWidth() - 1, (int)fieldRect.getHeight() - 1);
        if (lastGameState != null) {
            synchronized (lastGameState) {
                if (lastGameState != null) {
                    lastGameState.getSnakes().forEach(snake -> drawSnake(snake, g));
                    lastGameState.getFoods().forEach(food -> drawCage(food.getX(), food.getY(), SnakeColors.getFoodColor(), g));
                }
            }
        }
    }
}
