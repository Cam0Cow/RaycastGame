import java.awt.image.*;
import java.awt.*;

public class Monster implements Entity {
	
	private static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("monster");
	
	public Image getUnclippedTexture(Player p) {
		return TEXTURE;
	}
	
	public double getX() {
		return 62.0;
	}
	
	public double getY() {
		return 22.0;
	}
}