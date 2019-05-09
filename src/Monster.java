import java.awt.image.*;
import java.awt.*;

/**
 * Represents a monster enemy
 */
public class Monster implements Entity, Animatable {
	
	private static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("monster");
	private static final Image HURT    = TextureAtlas.getTextureAtlas().getTexture("hurt");
	
	private double x, y;
	private int health;
	private boolean isHurt;
	
	private GameState game; // for future use
	
	/**
	 * Constructs a new Monster
	 */
	public Monster(double x, double y) {
		this.x = x;
		this.y = y;
		health = 5;
		isHurt = false;
	}
	
	/**
	 * Returns the unclipped texture of the enemy
	 * @param p the player that should see this enemy
	 * @return the unclipped texture of the enemy
	 */
	public BufferedImage getUnclippedTexture(Player p) {
		double dx = p.getPosX() - getX();
		double dy = p.getPosY() - getY();
		double r = Math.sqrt(dx*dx+dy*dy);
		if (r < 0.4) r = 0.4;
		Image tex = isHurt ? HURT : TEXTURE;
        double w = getWidth()/r;
        double h = w * (tex.getHeight(null)/(double)tex.getWidth(null));
		BufferedImage img = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(tex, 0, 0, (int)w, (int)h, null);
        g.dispose();
        return img;
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
		game = state;
	}
	
	/**
	 * Checks whether the monster is dead
	 * @return whether the monster is dead
	 */
	public boolean isDead() {
		return health <= 0;
	}
	
	/**
	 * Reduce the monster's health by a certain amount
	 * @param damage the amount to damage the monster by
	 */
	public void damage(int damage) {
		health -= damage;
		isHurt = true;
		game.getGameLoop().registerFutureEvent(new AnimationUpdateEvent(this), 8);
	}
	
	/**
	 * Triggers the next frame of animation
	 * @param state the current game state
	 */
	public void nextFrame(GameState state) {
		isHurt = false;
	}
}