package UI;

import NameLater.DirectionM;
import Network.Client.ClientThread;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListen implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(c == 'w') ClientThread.getInstance().getSteer(DirectionM.UP);
        else if(c == 'd') ClientThread.getInstance().getSteer(DirectionM.RIGHT);
        else if(c == 's') ClientThread.getInstance().getSteer(DirectionM.DOWN);
        else if(c == 'a') ClientThread.getInstance().getSteer( DirectionM.LEFT);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
