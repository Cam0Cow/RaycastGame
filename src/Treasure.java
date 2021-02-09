/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.awt.*;
import java.awt.image.*;

/**
 * Represents a collectable treasure
 */
public class Treasure implements Entity {
    
    private double x, y;
    public static final Image TEXTURE = TextureAtlas.getTextureAtlas().getTexture("treasure");
    
    /**
     * Constructs a new treasure
     * @param x the x position
     * @param y the y position
     */
    public Treasure(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Damages the treasure--which can't be done
     * @param damage unused
     */
    public void damage(int damage) {
        // The treasure can't be damaged
    }
    
    /**
     * Checks whether the treasure is dead--it's never dead
     */
    public boolean isDead() {
        return false;
    }
    
    /**
     * Registers the treasure with the game state
     * @param state the current game state  
     */
    public void registerEntity(GameState state) {
        state.getGameLoop().registerRepeatedEvent(new TreasureAIEvent(this), 30);
    }

    /**
     * Resizes the treasure texture to be the correct size on screen
     * @param viewer the viewing player
     * @return the treasure's texture
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
     * Returns the treasure's x position
     * @return the treasure's x position
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the treasure's y position
     * @return the treasure's y position
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the treasure's width
     * @return the treasure's width
     */
    public double getWidth() {
        return 400;
    }

    /**
     * Sets the treasure's x postion
     * @param x the new x position
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the treasure's y postion
     * @param y the new y position
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the distance between the player and the treasure
     * @param p the given player
     * @return the distance between the player and the treasure
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
