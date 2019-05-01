import java.awt.*;
import java.awt.image.*;

/**
 * Represents a handgun the player can hold and shoot
 */
public class Handgun implements Weapon, Animatable {
	
	public static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("handgun");
	public static final Image GUN   = Renderer.toBuf(TEXTURE).getSubimage(0 ,0,51,84).getScaledInstance(153,-1, Image.SCALE_FAST);
	public static final Image FIRED = Renderer.toBuf(TEXTURE).getSubimage(52,0,51,92).getScaledInstance(153,-1, Image.SCALE_FAST);
	
	private boolean fired;
	
	/**
	 * Constructs a new handgun
	 */
	public Handgun() {
		fired = false;
	}
	
	/**
	 * Returns the gun's texture
	 * @return the gun's texture
	 */
	public Image getTexture() {
		return fired ? FIRED : GUN;
	}
	
	/**
	 * Fire the gun and queue its animation
	 * @param loop the game loop
	 */
	public void fire(GameLoop loop) {
		fired = true;
		loop.registerFutureEvent(new AnimationUpdateEvent(this), 5);
	}
	
	/**
	 * Called when the animation should display its next frame
	 * When called, the gun resets back to not shooting
	 */
	public void nextFrame() {
		fired = false;
	}
}