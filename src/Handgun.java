import java.awt.*;
import java.awt.image.*;

public class Handgun implements Weapon {
	
	public static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("handgun");
	public static final Image gun = Renderer.toBuf(TEXTURE).getSubimage(0,0,51,84).getScaledInstance(153,-1, Image.SCALE_FAST);
	
	public Image getTexture() {
		return gun;
	}
}