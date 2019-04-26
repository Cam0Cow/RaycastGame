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
}