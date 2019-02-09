import java.io.*;
import java.util.Scanner;

/**
 * Represents a 2D map (i.e. one level only)
 */
public class LevelMap {
    
    private MapTile[][] map;
    private String name;
    private int width;
    private int height;
    
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
            map = new MapTile[height][width];
            for (int i=0; i<height; i++) {
                for (int j=0; j<width; j++) {
                    map[i][j] = MapTile.fromInt(sc.nextInt());
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
}