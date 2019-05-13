/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.time.*;
import java.util.*;

/**
 * Represents damage delt to the player
 */
public class DamagePlayerEvent extends GameEvent {
	
    private Entity e;
    
    /**
     * Constructs a new damage player event
     * @param e the entity that damages the player   
     */
    public DamagePlayerEvent(Entity e) {
        this.e = e;
    }
    
    /**
     * Handles the event when it is triggered
     * @param state the current game state
     * @param dt the frame time
     */
	public void handle(GameState state, Duration dt) {
		Player p = state.getPlayer();
	    if (e.getDistance(p) < 1.1) p.damage(1);
	}
}