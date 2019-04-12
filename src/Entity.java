import java.awt.image.*;
import java.awt.*;

/**
 * Describes a viewable entity in the map
 */
public interface Entity {
	
	/**
	 * Returns the unclipped texture of the entity, scaled correctly
	 * @param viewer the player viewing the entity
	 * @return the unclipped texture of the entity, scaled correctly
	 */
	public Image getUnclippedTexture(Player viewer);
	
	/**
	 * Returns the x coordinate of the entity
	 * @return the x coordinate of the entity
	 */
	public double getX();
	
	/**
	 * Returns the y coordinate of the entity
	 * @return the y coordinate of the entity
	 */
	public double getY();
}