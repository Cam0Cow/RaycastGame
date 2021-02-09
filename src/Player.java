/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

/**
 * Represents the player
 */
public class Player {
    
    private double dirX, dirY;
    private double posX, posY;
    
    private int health;
    
    private double speed;
    
    private Weapon weapon;
    
    /**
     * Creates a new player with default values
     */
    public Player() {
        this(0,0,0,-1);
    }
    
    /**
     * Creates a new player with the given values
     * @param posX the x position
     * @param posY the y position
     * @param dirX the x direction
     * @param dirY the y direction
     */
    public Player(double posX, double posY, double dirX, double dirY) {
        this.posX = posX;
        this.posY = posY;
        this.dirX = dirX;
        this.dirY = dirY;
        
        speed = 1;
        
        double mag = Math.sqrt(dirX*dirX+dirY*dirY);
        dirX /= mag;
        dirY /= mag;
        
        weapon = new Handgun();
        health = 10;
    }
    
    /**
     * Returns the x position
     * @return the x position
     */
    public double getPosX() {
        return posX;
    }
    
    /**
     * Returns the y position
     * @return the y position
     */
    public double getPosY() {
        return posY;
    }
    
    /**
     * Returns the x direction
     * @return the x direction
     */
    public double getDirX() {
        return dirX;
    }
    
    /**
     * Returns the y direction
     * @return the y direction
     */
    public double getDirY() {
        return dirY;
    }
    
    /**
     * Set the x position
     * @param x the new x position
     */
    public void setPosX(double x) {
        posX = x;
    }
    
    /**
     * Set the y position
     * @param y the new y position
     */
    public void setPosY(double y) {
        posY = y;
    }
    
    /**
     * Set the x direction
     * @param x the new x direction
     */
    public void setDirX(double x) {
        dirX = x;
    }
    
    /**
     * Set the y direction
     * @param y the new y direction
     */
    public void setDirY(double y) {
        dirY = y;
    }
    
    /**
     * Returns the speed of the player
     * @return the speed of the player
     */
    public double getSpeed() {
        return speed;
    }
    
    /**
     * Returns the current weapon
     * @return the current weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }
    
    /**
     * Damages the player
     * @param damage the amount of health points to take away
     */
    public void damage(int damage) {
        health -= damage;
        AudioLibrary.getAudioLibrary().play("hurt");
    }
    
    /**
     * Checks whether the player is dead
     * @return whether the player is dead
     */
    public boolean isDead() {
        return health <= 0;
    }
    
    /**
     * Returns the player's health
     * @return the player's health
     */
    public int getHealth() {
        return health;
    }
}
