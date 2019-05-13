/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.time.*;

/**
 * Represents a weapon cooldown
 */
public class WeaponCooldownEvent extends GameEvent {
	
	private Weapon weapon;
	
    /**
     * Constructs a new weapon cooldown event for a given weapon
     * @param w the given weapon
     */
	public WeaponCooldownEvent(Weapon w) {
		weapon = w;
	}
	
    /**
     * Ends the weapon cooldown
     * @param state the current game state
     * @param dt the frame time
     */
	public void handle(GameState state, Duration dt) {
		weapon.endCooldown();
	}
}