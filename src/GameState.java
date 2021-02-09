/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

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
    private GameLoop loop;
    private MusicPlayer musicPlayer;
    
    /**
     * Constructs a new game state if necessary  
     */
    public GameState() {
        player = new Player();
        keyState = KeyState.getKeyState();
        fps = new FPS();
        musicPlayer = new MusicPlayer();
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
     * Can only be called after a game loop is set
     * @param lm the new level
     */
    public void setLevelMap(LevelMap lm) {
        if (loop == null) throw new IllegalStateException("No game loop registered");
        levelMap = lm;
        for (Entity e : levelMap.getEntities()) e.registerEntity(this);
    }
    
    /**
     * Registers an active game loop with the game state
     * Should only be called once
     * @param l the given game loop
     */
    public void registerGameLoop(GameLoop l) {
        if (loop != null)
            throw new IllegalStateException("Game loop already registered");
        else
            loop = l;
    }
    
    /**
     * Returns the registered game loop
     * @return the registered game loop
     */
    public GameLoop getGameLoop() {
        return loop;
    }
    
    /**
     * Get the FPS object
     * @return the FPS object
     */
    public FPS getFPS() {
        return fps;
    }
    
    /**
     * Returns the music player
     * @return the music player   
     */
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
