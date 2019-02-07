

/**
 * Represents the state of the game at any given time
 */
public class GameState {
    
    // This will contain the screen data, enemies, stats, etc.
    // It will be updated as new features are added
    
    private static GameState state;
    
    /**
     * Constructs a new game state if necessary  
     */
    private GameState() {
        // Initialization here
    }
    
    public static GameState getGameState() {
        if (state == null) state = new GameState();
        return state;
    }
}