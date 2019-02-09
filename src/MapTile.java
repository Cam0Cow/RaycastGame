
/**
 * Represents a tile in the map
 */
public enum MapTile {
    NOTHING,
    UNTEXTURED_WALL;
    
    private static final MapTile[] indices = MapTile.values();
    
    /**
     * Returns the enum value with the given index
     * @param index the given index
     * @return the enum value with the given index   
     */
    public static MapTile fromInt(int index) {
        return indices[index];
    }
}