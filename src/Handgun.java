/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

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
    private boolean inCooldownMode;
    
    /**
     * Constructs a new handgun
     */
    public Handgun() {
        fired = false;
        inCooldownMode = false;
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
        if (!inCooldownMode) {
            fired = true;
            inCooldownMode = true;
            loop.queueEvent(new BulletEvent());
            loop.registerFutureEvent(new AnimationUpdateEvent(this), 5);
            loop.registerFutureEvent(new WeaponCooldownEvent(this), 20);
            AudioLibrary.getAudioLibrary().play("handgun");
        }
    }
    
    /**
     * Called when the animation should display its next frame
     * When called, the gun resets back to not shooting
     */
    public void nextFrame(GameState state) {
        fired = false;
    }
    
    /**
     * Called when the handgun's cooldown period is over
     */
    public void endCooldown() {
        inCooldownMode = false;
    }
    
    /**
     * Returns how much damage the weapon should have a certain distance away
     * @param distance the distance to the entity
     * @return the effective damage
     */
    public int getDamageAtRange(double distance) {
        return distance > 9 ? 0 : 1;
    }
}
