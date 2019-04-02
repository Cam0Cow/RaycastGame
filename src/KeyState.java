import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class KeyState implements KeyListener {

    private HashSet<Integer> keysDown;
    private static KeyState ref;

    private KeyState() {
        keysDown = new HashSet<Integer>();
        ref = this;
    }

    public static KeyState getKeyState() {
        if (ref == null) ref = new KeyState();
        return ref;
    }

    public boolean isDown(Integer key) {
        return keysDown.contains(key);
    }

    public void keyPressed(KeyEvent e) {
        keysDown.add(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        keysDown.remove(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e) {}
}
