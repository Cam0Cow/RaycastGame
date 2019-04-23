import java.awt.image.*;
import java.awt.*;

public class Monster implements Entity {
	
	private static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("monster");
	
	public Image getUnclippedTexture(Player p) {
		double dx = p.getPosX() - getX();
		double dy = p.getPosY() - getY();
		double r = Math.sqrt(dx*dx+dy*dy);
		if (r < 0.1) r = 0.4;
		return TEXTURE.getScaledInstance((int)(getWidth()/r), -1, Image.SCALE_FAST);
	}
	
	public double getWidth() {
		return 200;
	}
	
	public double getDistance(Player p) {
		double dx = p.getPosX() - getX();
		double dy = p.getPosY() - getY();
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public double getX() {
		return 4.0;
	}
	
	public double getY() {
		return 4.0;
	}
}