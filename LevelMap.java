import java.io.*;

/**
 * Represents a 2D map (i.e. one level only)
 */
public class LevelMap {
    
    private MapTile[][] map;
    private int width;
    private int height;
    
    /**
     * Loads a map from disk
     * @param filename the file to load from   
     */
    public LevelMap(String filename) {
        System.out.println("Loading map from " + filename);
    }
}