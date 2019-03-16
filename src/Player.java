

public class Player {
    
    private double dirX, dirY;
    private double posX, posY;
    
    public Player() {
        this(0,0,0,-1);
    }
    
    public Player(double posX, double posY, double dirX, double dirY) {
        this.posX = posX;
        this.posY = posY;
        this.dirX = dirX;
        this.dirY = dirY;
    }
    
    public double getPosX() {
        return posX;
    }
    
    public double getPosY() {
        return posY;
    }
    
    public double getDirX() {
        return dirX;
    }
    
    public double getDirY() {
        return dirY;
    }
}