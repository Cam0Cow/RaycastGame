/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.awt.image.*;
import java.awt.*;

/**
 * Describes a viewable entity in the map
 */
public interface Entity {
    
    /**
     * Returns the unclipped texture of the entity, scaled correctly
     * @param viewer the player viewing the entity
     * @return the unclipped texture of the entity, scaled correctly
     */
    public BufferedImage getUnclippedTexture(Player viewer);
    
    /**
     * Returns the x coordinate of the entity
     * @return the x coordinate of the entity
     */
    public double getX();
    
    /**
     * Returns the y coordinate of the entity
     * @return the y coordinate of the entity
     */
    public double getY();
    
    /**
     * Returns the width, in pixels, of the entity, if
     * it's at a distance of 1 away from the player
     * @return the width, in pixels, of the entity
     */
    public double getWidth();
    
    /**
     * Sets the Entity's x position
     * @param x the new x position
     */
    public void setX(double x);
    
    /**
     * Sets the Entity's y position
     * @param y the new y position
     */
    public void setY(double y);
    
    /**
     * Returns the distance from the player
     * @return the distance from the player
     */
    public double getDistance(Player p);
    
    /**
     * Register an entity with the game state
     * This is done so that AI can be set up
     */
    public void registerEntity(GameState gs);
    
    /**
     * Check whether the entity is dead
     * @return whether the entity is dead
     */
    public boolean isDead();
    
    /**
     * Hurt the entity a certain number of health points
     * @param damage how much to hurt the monster
     */
    public void damage(int damage);
    
    /**
     * Called when the entity has been killed
     * Used to give it a chance to unregister repeated events 
     */
    public void kill();
}
