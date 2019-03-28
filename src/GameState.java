

/**
 * Represents the state of the game at any given time
 */
public class GameState {
    
    // This will contain the screen data, enemies, stats, etc.
    // It will be updated as new features are added
    
    
    private Player player;
    private LevelMap levelMap;
    private KeyState keyState;
    
    /**
     * Constructs a new game state if necessary  
     */
    public GameState() {
        player = new Player();
        keyState = KeyState.getKeyState();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player p) {
        player = p;
    }
    
    public LevelMap getLevelMap() {
        return levelMap;
    }
    
    public void setLevelMap(LevelMap lm) {
        levelMap = lm;
    }
}