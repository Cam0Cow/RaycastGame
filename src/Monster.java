import java.awt.image.*;
import java.awt.*;

public class Monster implements Entity {
	
	private static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("monster");
	
	public Image getUnclippedTexture(Player p) {
		double dx = p.getPosX() - getX();
		double dy = p.getPosY() - getY();
		double r = Math.sqrt(dx*dx+dy*dy);
		if (r < 0.1) r = 0.4;
		return TEXTURE.getScaledInstance((int)(200/r), -1, Image.SCALE_FAST);
	}
	
	public double getX() {
		return 62.0;
	}
	
	public double getY() {
		return 22.0;
	}
}