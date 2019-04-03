import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class KeyState implements KeyListener {

    private HashSet<Integer> keysDown;
    private static KeyState ref;
	
	/**
	 * Constructs a new keyState
	 */
    private KeyState() {
        keysDown = new HashSet<Integer>();
        ref = this;
    }
	
	/**
	 * Returns the singleton keyState
	 * @return the singleton keyState
	 */
    public static KeyState getKeyState() {
        if (ref == null) ref = new KeyState();
        return ref;
    }
	
	/**
	 * Check whether a key is down
	 * @param key the key to check for
	 * @return whether the key is pressed
	 */
    public boolean isDown(Integer key) {
        return keysDown.contains(key);
    }
	
	/**
	 * Triggered when a key is pressed
	 * @param e the keyEvent
	 */
    public void keyPressed(KeyEvent e) {
        keysDown.add(e.getKeyCode());
    }
	
	/**
	 * Triggered when a key is released
	 * @param e the keyEvent
	 */
    public void keyReleased(KeyEvent e) {
        keysDown.remove(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e) {}
}
