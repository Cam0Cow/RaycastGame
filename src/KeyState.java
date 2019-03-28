import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class KeyState implements KeyListener {
    
    private HashMap<Integer, Boolean> keysDown;
    private static KeyState ref;
    
    private KeyState() {
        keysDown = new HashMap<Integer, Boolean>();
        ref = this;
    }
    
    public static KeyState getKeyState() {
        if (ref == null) ref = new KeyState();
        return ref;
    }
    
    public boolean isDown(Integer key) {
        return keysDown.getOrDefault(key, false);
    }
    
    public void keyPressed(KeyEvent e) {
        keysDown.put(e.getKeyCode(), true);
    }
    
    public void keyReleased(KeyEvent e) {
        keysDown.put(e.getKeyCode(), false);
    }
    
    public void keyTyped(KeyEvent e) {}
}