import java.awt.image.*;
import java.awt.*;

/**
 * Represents a monster enemy
 */
public class Monster implements Entity {
	
	private static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("monster");
	
	private double x, y;
	
	/**
	 * Constructs a new Monster
	 */
	public Monster() {
		x = 4.0;
		y = 4.0;
	}
	
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
		return x;
	}
	
	/**
	 * Returns the y coordinate of the enemy
	 * @return the y coordinate of the enemy
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Sets the monster's x position
	 * @param x the new x position
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets the monster's y position
	 * @param y the new y position
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Register the entity with the game state
	 * @param state the given game state
	 */
	public void registerEntity(GameState state) {
		state.getGameLoop().registerRepeatedEvent(new EnemyAIEvent(this), 1);
	}
}