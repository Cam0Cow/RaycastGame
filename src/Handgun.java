import java.awt.*;
import java.awt.image.*;

public class Handgun implements Weapon {
	
	public static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("handgun");
	public static final Image GUN   = Renderer.toBuf(TEXTURE).getSubimage(0 ,0,51,84).getScaledInstance(153,-1, Image.SCALE_FAST);
	public static final Image FIRED = Renderer.toBuf(TEXTURE).getSubimage(52,0,51,92).getScaledInstance(153,-1, Image.SCALE_FAST);
	
	private boolean fired;
	
	public Handgun() {
		fired = false;
	}
	
	public Image getTexture() {
		return fired ? FIRED : GUN;
	}
	
	public void fire(GameLoop loop) {
		fired = true;
	}
}