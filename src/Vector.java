
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
    
    /**
      * Returns the negated vector
      * @return the negated vector   
      */
    public Vector negate() {
        return new Vector(-x,-y);
    }
    
    /**
      * Returns the sum of this vector and another
      * @param v the other vector
      * @return the sum of this vector and another
      */
    public Vector add(Vector v) {
        return new Vector(x+v.x, y+v.y);
    }
    
    /**
      * Returns the square of the magnitude of the vector
      * @return the square of the magnitude of the vector
      */
    public double magnitude2() {
        return x*x + y*y;
    }
    
    /**
      * Returns the magnitude of the vector
      * @return the magnitude of the vector
      */
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    /**
      * Returns the argument of the vector in radians
      * @return the argument of the vector in radians
      */
    public double argument() {
        return Math.atan2(y,x);
    }
    
    /**
      * Creates a new unit vector from a given angle
      * @param angle the angle of the new vector
      * @return the unit vector
      */
    public static Vector fromAngle(double angle) {
        return new Vector(Math.cos(angle), Math.sin(angle));
    }
    
    /**
      * Scales a vector by a given scalar factor
      * @param s the scalar factor
      * @return the scaled vector
      */
    public Vector scale(double s) {
        return new Vector(x*s, y*s);
    }
}