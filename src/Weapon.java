/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.awt.*;
import java.awt.image.*;

/**
 * Represents a weapon the player can hold
 */
public interface Weapon {
	
	/**
	 * Returns the weapon's texture
	 * @return the weapon's texture
	 */
	public Image getTexture();
	
	/**
	 * Fires the gun
	 * @param loop the game loop, for timing purposes
	 */
	public void fire(GameLoop loop);
	
	/**
	 * End the weapon's cooldown period
	 */
	public void endCooldown();
	
	/**
	 * Returns how much damage the weapon should have a certain distance away
	 * @param distance the distance to the entity
	 * @return the effective damage
	 */
	public int getDamageAtRange(double distance);
}