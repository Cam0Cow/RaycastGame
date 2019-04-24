

/**
 * Represents the state of the game at any given time
 */
public class GameState {
    
    // This will contain the screen data, enemies, stats, etc.
    // It will be updated as new features are added
    
    
    private Player player;
    private LevelMap levelMap;
    private KeyState keyState;
    private FPS fps;
    
    /**
     * Constructs a new game state if necessary  
     */
    public GameState() {
        player = new Player();
        keyState = KeyState.getKeyState();
        fps = new FPS();
    }
    
    /**
     * Returns the current player
     * @return the current player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Sets the current player
     * @param p the new player
     */
    public void setPlayer(Player p) {
        player = p;
    }
    
    /**
     * Returns the current level
     * @return the current level
     */
    public LevelMap getLevelMap() {
        return levelMap;
    }
    
    /**
     * Set the current level
     * @param lm the new level
     */
    public void setLevelMap(LevelMap lm) {
        levelMap = lm;
    }
    
    /**
     * Get the FPS object
     * @return the FPS object
     */
    public FPS getFPS() {
    	return fps;
    }
}