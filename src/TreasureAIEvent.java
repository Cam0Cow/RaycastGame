/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.time.*;

/**
 * Represents the treasure's AI
 */
public class TreasureAIEvent extends GameEvent {
    
    private Treasure t;
    
    /**
     * Constructs a new treasure AI event
     * @param t the treasure
     */
    public TreasureAIEvent(Treasure t) {
        this.t = t;
    }
    
    /**
     * Checks if the player is around
     * @param state the current game state
     * @param dt the frame time
     */
    public void handle(GameState state, Duration dt) {
        Player p = state.getPlayer();
        if (t.getDistance(p) < 0.5) {
            state.getGameLoop().levelCompleted();
        }
    }
}