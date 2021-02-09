/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.util.*;
import java.util.concurrent.*;
import java.awt.event.*;
import java.awt.*;

/**
 * A structure that holds all of the pressed keys
 */
public class KeyState implements KeyListener {

    private HashSet<Integer> keysDown;
    private ConcurrentLinkedQueue<KeyStateChangedEvent> queue;
    private static KeyState ref;
    
    /**
     * Constructs a new keyState
     */
    private KeyState() {
        queue = new ConcurrentLinkedQueue<KeyStateChangedEvent>();
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
     * Returns an iterator for the keys that are pressed
     * @return an iterator for the keys that are pressed 
     */
    public Iterator<Integer> iterator() {
        flushQueue();
        return keysDown.iterator();
    }
    
    /**
     * Check whether a key is down
     * @param key the key to check for
     * @return whether the key is pressed
     */
    public boolean isDown(Integer key) {
        flushQueue();
        return keysDown.contains(key);
    }
    
    /**
     * Triggered when a key is pressed
     * @param e the keyEvent
     */
    public void keyPressed(KeyEvent e) {
        queue.offer(new KeyStateChangedEvent(e.getKeyCode(), true));
    }
    
    /**
     * Triggered when a key is released
     * @param e the keyEvent
     */
    public void keyReleased(KeyEvent e) {
        queue.offer(new KeyStateChangedEvent(e.getKeyCode(), false));
    }
    
    /**
     * Flushes the internal queue, updating the hashSet of keys
     * to be as up-to-date as possible
     */
    private void flushQueue() {
        while (!queue.isEmpty()) {
            KeyStateChangedEvent ksce = queue.poll();
            if (ksce.isDown()) {
                keysDown.add(ksce.getKeyCode());
            } else {
                keysDown.remove(ksce.getKeyCode());
            }
        }
    }
    
    /**
     * Return the number of keys being held down
     */
    public int getNumKeysDown() {
        flushQueue();
        return keysDown.size();
    }
    
    /**
     * Removes all of the key presses that have been registered
     * with the keyState. Useful for context switches (i.e. between menus)
     */
    public void purge() {
        keysDown.clear();
        queue.clear();
    }
    
    /**
     * Triggered by the OS whena key is typed
     * @param e the key event
     */
    public void keyTyped(KeyEvent e) {}
    
    /**
     * Represents a state change of a key
     */
    private static class KeyStateChangedEvent {
        
        private int keyCode;
        private boolean isDown;
        
        /**
         * Constructs a new state change for a given key
         * @param k the key code
         * @param d whether the key is down 
         */
        public KeyStateChangedEvent(int k, boolean d) {
            keyCode = k;
            isDown = d;
        }
        
        /**
         * Returns the key code
         * @return the key code
         */
        public int getKeyCode() {
            return keyCode;
        }
        
        /**
         * Returns whether the key is down
         * @return whether the key is down
         */
        public boolean isDown() {
            return isDown;
        }
    }
}
