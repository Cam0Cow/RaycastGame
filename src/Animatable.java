/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

/**
 * Represents an object that can be animated on the screen
 */
public interface Animatable {
    
    /**
     * Called when the next frame should be played
     */
    public void nextFrame(GameState state);
}
