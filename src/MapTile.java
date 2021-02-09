/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.awt.Color;

/**
 * Represents a tile in the map
 */
public enum MapTile {
    NOTHING,
    WALL_GREEN,
    WALL_RED,
    WALL_BLUE,
    WALL_YELLOW,
    WALL_ORANGE;
    
    private static final MapTile[] indices = MapTile.values();
    private static final Color[] colors = {
		Color.GREEN,
		Color.RED,
		Color.BLUE,
        Color.YELLOW,
        Color.ORANGE
    };
    
    /**
     * Returns the enum value with the given index
     * @param index the given index
     * @return the enum value with the given index   
     */
    public static MapTile fromInt(int index) {
        return indices[index];
    }
    
    /**
     * Returns the correct wall color to draw
     * @param mt the given map tile
     * @return the wall color
     */
    public static Color getColor(MapTile mt) {
		return colors[mt.ordinal()-1];
    }
}
