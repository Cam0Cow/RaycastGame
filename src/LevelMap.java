import java.io.*;
import java.util.*;

/**
 * Represents a 2D map (i.e. one level only)
 */
public class LevelMap {
    
    private MapTile[][] map;
    private HashSet<Entity> entities;
    private String name;
    private int width;
    private int height;
    private int locX;
    private int locY;
    
    /**
     * Loads a map from disk
     * @param filename the file to load from   
     */
    public LevelMap(String filename) {
        try {
            Scanner sc = new Scanner(new FileReader(filename));
            
            name = sc.nextLine();
            width = sc.nextInt();
            height = sc.nextInt();
            locX = sc.nextInt();
            locY = sc.nextInt();
            sc.nextLine();
            entities = new HashSet<Entity>();
            entities.add(new Monster());
            map = new MapTile[height][width];
            for (int i=0; i<height; i++) {
            	String line = sc.nextLine();
                for (int j=0; j<width; j++) {
                    map[i][j] = MapTile.fromInt(translate(line.charAt(j)));
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the name of the map
     * @return the name of the map  
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the width of the map
     * @return the width of the map  
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the name of the map
     * @return the width of the map  
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the map array
     * @return the map array   
     */
    public MapTile[][] getMap() {
        return map;
    }
    
    /**
     * Returns the set of all entities
     * @return the set of all entities
     */
    public HashSet<Entity> getEntities() {
    	return entities;
    }
    
    /**
     * Translates a map character to a tile index
     * @param c the map character
     * @return the tile index
     */
    private int translate(char c) {
    	if (Character.isDigit(c)) {
    		return c - '0';
    	} else if (Character.isLowerCase(c)) {
    		return c - 'a' + 10;
    	} else if (Character.isUpperCase(c)) {
    		return c - 'A' + 36;
    	} else {
    		return 0;
    	}
    }
    
    /**
     * Returns the starting x coordinate
     * @return the starting y coordinate
     */
    public int getLocX() {
    	return locX;
    }
    
    /**
     * Returns the starting y coordinate
     * @return the starting y coordinate
     */
    public int getLocY() {
    	return locY;
    }
}