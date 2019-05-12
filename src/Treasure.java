import java.awt.*;
import java.awt.image.*;

public class Treasure implements Entity {
	
	private double x, y;
	public static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("treasure");
	
	public Treasure(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void damage(int damage) {
		// The treasure can't be damaged
	}
	
	public boolean isDead() {
		return false;
	}
	
	public void registerEntity(GameState state) {
		// TODO
	}

	/**
	 * Method getUnclippedTexture
	 *
	 *
	 * @param viewer
	 *
	 * @return
	 *
	 */
	public BufferedImage getUnclippedTexture(Player viewer) {
		double r = getDistance(viewer);
		double w = getWidth()/r;
        double h = w * (TEXTURE.getHeight(null)/(double)TEXTURE.getWidth(null));
		BufferedImage img = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(TEXTURE, 0, 0, (int)w, (int)h, null);
        g.dispose();
        return img;
	}

	/**
	 * Method getX
	 *
	 *
	 * @return
	 *
	 */
	public double getX() {
		return x;
	}

	/**
	 * Method getY
	 *
	 *
	 * @return
	 *
	 */
	public double getY() {
		return y;
	}

	/**
	 * Method getWidth
	 *
	 *
	 * @return
	 *
	 */
	public double getWidth() {
		return 400;
	}

	/**
	 * Method setX
	 *
	 *
	 * @param x
	 *
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Method setY
	 *
	 *
	 * @param y
	 *
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Method getDistance
	 *
	 *
	 * @param p
	 *
	 * @return
	 *
	 */
	public double getDistance(Player p) {
		double dx = p.getPosX() - x;
		double dy = p.getPosY() - y;
		return Math.sqrt(dx*dx+dy*dy);
	}
    
    /**
     * Called when the treasure has been "killed"
     * Shouldn't happen
     */
    public void kill() {}
	
	
}