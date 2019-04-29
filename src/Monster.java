import java.awt.image.*;
import java.awt.*;

/**
 * Represents a monster enemy
 */
public class Monster implements Entity {
	
	private static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("monster");
	
	/**
	 * Returns the unclipped texture of the enemy
	 * @param p the player that should see this enemy
	 * @return the unclipped texture of the enemy
	 */
	public Image getUnclippedTexture(Player p) {
		double dx = p.getPosX() - getX();
		double dy = p.getPosY() - getY();
		double r = Math.sqrt(dx*dx+dy*dy);
		if (r < 0.4) r = 0.4;
		return TEXTURE.getScaledInstance((int)(getWidth()/r), -1, Image.SCALE_FAST);
	}
	
	/**
	 * Gets the natural width of the texture
	 * @return the width of the texture
	 */
	public double getWidth() {
		return 400;
	}
	
	/**
	 * Gets the distance from the player to the enemy
	 * @param p the given player
	 * @return the distance from the player to the enemy
	 */
	public double getDistance(Player p) {
		double dx = p.getPosX() - getX();
		double dy = p.getPosY() - getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	/**
	 * Returns the x coordinate of the enemy
	 * @return the x coordinate of the enemy
	 */
	public double getX() {
		return 4.0;
	}
	
	/**
	 * Returns the y coordinate of the enemy
	 * @return the y coordinate of the enemy
	 */
	public double getY() {
		return 4.0;
	}
}