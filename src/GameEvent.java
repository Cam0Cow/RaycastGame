/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.time.*;

/**
 * Represents an event occuring during gameplay
 */
public abstract class GameEvent implements Comparable<GameEvent> {
    
    // subclasses can modify this, higher values are higher priority
    protected static int priority = 0;
    
    /**
     * Handles a GameEvent during a given state
     * @param state the current GameState
     * @param dt the time between this frame and the last
     */
    public abstract void handle(GameState state, Duration dt);
    
    /**
     * Compares GameEvents based on priority
     * @param ge the other gameEvent
     */
    public final int compareTo(GameEvent ge) {
        return ge.priority - priority;
    }
}
