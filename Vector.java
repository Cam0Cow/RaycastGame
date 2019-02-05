
/**
 * Represents an immuatable vector and associated operations
*/
public class Vector {
    
    private double x;
    private double y;
    
    /**
     * Constructs a new vector with specified x and y components
     * @param x the x component
     * @param y the y component
    */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector negate() {
        return new Vector(-x,-y);
    }
    
    public Vector add(Vector v) {
        return new Vector(x+v.x, y+v.y);
    }
    
    public double magnitude2() {
        return x*x + y*y;
    }
    
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    public double argument() {
        return Math.atan2(y,x);
    }
    
    public static Vector fromAngle(double angle) {
        return new Vector(Math.cos(angle), Math.sin(angle));
    }
    
    public Vector scale(double s) {
        return new Vector(x*s, y*s);
    }
}