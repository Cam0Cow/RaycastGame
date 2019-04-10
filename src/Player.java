

public class Player {
    
    private double dirX, dirY;
    private double posX, posY;
    
    private double speed;
    
    public Player() {
        this(0,0,0,-1);
    }
    
    public Player(double posX, double posY, double dirX, double dirY) {
        this.posX = posX;
        this.posY = posY;
        this.dirX = dirX;
        this.dirY = dirY;
        
        speed = 1;
        
        double mag = Math.sqrt(dirX*dirX+dirY*dirY);
        dirX /= mag;
        dirY /= mag;
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
    
    public void setPosX(double x) {
    	posX = x;
    }
    
    public void setPosY(double y) {
    	posY = y;
    }
    
    public void setDirX(double x) {
    	dirX = x;
    }
    
    public void setDirY(double y) {
    	dirY = y;
    }
    
    public double getSpeed() {
        return speed;
    }
}