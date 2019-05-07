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
}