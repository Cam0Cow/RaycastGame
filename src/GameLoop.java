/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.util.*;
import java.time.*;
import java.awt.event.*;

/**
 * Represents a game loop
 */
public class GameLoop {
	
	private GameState state;
	private MouseState mouseState;
	private Renderer rend;
	private Display disp;
	private PriorityQueue<GameEvent> queue;
	private ConcurrentMap<GameEvent, Integer> registeredEvents;
	private ConcurrentMap<GameEvent, Long> futureEvents;
	private Instant previousFrame;
	private long frameNumber;
	
	/**
	 * Constructs a new game loop with the given parameters
	 * @param gs the current game state
	 * @param r the frame renderer
	 * @param d the display to draw to
	 */
	public GameLoop(GameState gs, Renderer r, Display d) {
		state = gs;
		state.registerGameLoop(this);
		rend = r;
		disp = d;
		mouseState = new MouseState(d.getSize());
		d.setMouseState(mouseState);
		queue = new PriorityQueue<GameEvent>();
		registeredEvents = new ConcurrentMap<GameEvent, Integer>();
		futureEvents = new ConcurrentMap<GameEvent, Long>();
		frameNumber = 0;
		previousFrame = Instant.now();
	}
	
	/**
	 * Queues a game event for later processing
	 * @param ge the given game event
	 */
	public void queueEvent(GameEvent ge) {
		queue.offer(ge);
	}
	
	/**
	 * Register a game event that should be triggered every certain number of frames
	 * @param ge the game event
	 * @param frameDelay the number of frames between triggerings
	 */
	public void registerRepeatedEvent(GameEvent ge, int frameDelay) {
		registeredEvents.put(ge, frameDelay);
	}
    
    /**
     * Removes a previously registered game event from occuring again
     * @param ge the game event  
     */
    public void unregisterRepeatedEvent(GameEvent ge) {
        registeredEvents.remove(ge);
    }
	
	/**
	 * Registers a game event that should be triggered only once, in a certain number of frames
	 * @param ge the game event
	 * @param frameDelay the number of frames to wait before triggering
	 */
	public void registerFutureEvent(GameEvent ge, int frameDelay) {
		futureEvents.put(ge, frameNumber+frameDelay);
	}
	
	/**
	 * Called when the game is in progress
	 * Runs everything basically
	 */
	public void loop() {
		boolean done = false;
		previousFrame = Instant.now().minus(Duration.ofMillis(1)); // not quite now
		
		while (!done) {
			if (!rend.isPaused()) {
				Instant now = Instant.now();
				Duration dt = Duration.between(previousFrame, now);
				previousFrame = now;
				state.getFPS().addFrame(dt.toMillis());
				handleKeys(dt);
				handleMouse();
				handleFutureEvents();
				handleRepeatedEvents();
				while (!queue.isEmpty()) {
					// System.out.println (queue.peek());
					queue.poll().handle(state, dt);
				}
				frameNumber++;
				if (state.getPlayer().isDead()) rend.setGameOver();
			}
			while (!disp.isFrameDone());
            disp.resetFrameStatus();
			done = rend.render(state);
            disp.show(rend);
		}
	}
    
    /**
     * Handles the key presses from the last frame
     * @param dt the last frame time
     */
    private void handleKeys(Duration dt) {
		int ms = (int) dt.toMillis();
        Iterator<Integer> keys = KeyState.getKeyState().iterator();
        Player p = state.getPlayer();
        MapTile[][] map = state.getLevelMap().getMap();
        while (keys.hasNext()) {
            switch (keys.next()) {
                case KeyEvent.VK_ESCAPE: {
					rend.toggleMenu();
					break;
                }
                case KeyEvent.VK_W: {
					double xNew = p.getPosX()+p.getDirX()*ms/1000.0;
					double yNew = p.getPosY()+p.getDirY()*ms/1000.0;
					if (map[(int)p.getPosY()][(int)xNew] != MapTile.NOTHING) xNew = p.getPosX();
					if (map[(int)yNew][(int)p.getPosX()] != MapTile.NOTHING) yNew = p.getPosY();
					p.setPosX(xNew);
					p.setPosY(yNew);
					break;
				}
				case KeyEvent.VK_S: {
					double xNew = p.getPosX()-p.getDirX()*ms/1000.0;
					double yNew = p.getPosY()-p.getDirY()*ms/1000.0;
					if (map[(int)p.getPosY()][(int)xNew] != MapTile.NOTHING) xNew = p.getPosX();
					if (map[(int)yNew][(int)p.getPosX()] != MapTile.NOTHING) yNew = p.getPosY();
					p.setPosX(xNew);
					p.setPosY(yNew);
					break;
				}
				case KeyEvent.VK_D: {
					double xNew = p.getPosX()-p.getDirY()*ms/1000.0;
					double yNew = p.getPosY()+p.getDirX()*ms/1000.0;
					if (map[(int)p.getPosY()][(int)xNew] != MapTile.NOTHING) xNew = p.getPosX();
					if (map[(int)yNew][(int)p.getPosX()] != MapTile.NOTHING) yNew = p.getPosY();
					p.setPosX(xNew);
					p.setPosY(yNew);
					break;
				}
				case KeyEvent.VK_A: {
					double xNew = p.getPosX()+p.getDirY()*ms/1000.0;
					double yNew = p.getPosY()-p.getDirX()*ms/1000.0;
					if (map[(int)p.getPosY()][(int)xNew] != MapTile.NOTHING) xNew = p.getPosX();
					if (map[(int)yNew][(int)p.getPosX()] != MapTile.NOTHING) yNew = p.getPosY();
					p.setPosX(xNew);
					p.setPosY(yNew);
					break;
				}
				case KeyEvent.VK_RIGHT: {
					double x = p.getDirX();
					double y = p.getDirY();
					double angle = Math.PI * ms / 2000;
					p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
					p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
					break;
				}
				case KeyEvent.VK_LEFT: {
					double x = p.getDirX();
					double y = p.getDirY();
					double angle = -Math.PI * ms / 2000;
					p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
					p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
					break;
				}
            }
        }
    }
    
    /**
     * Handles camera rotation due to mouse movements and gun shots
     */
    private void handleMouse() {
        Player p = state.getPlayer();
        
        if (mouseState.hasFired()) p.getWeapon().fire(this);
        
        double x = p.getDirX();
        double y = p.getDirY();
		double angle = mouseState.getDeltaAngle();
        p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
        p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
    }
    
    /**
     * Checks whether repeated events should be requeued this frame
     */
    private void handleRepeatedEvents() {
		for (GameEvent ge : registeredEvents.keySet()) {
			if (frameNumber % registeredEvents.get(ge) == 0) {
				queueEvent(ge);
			}
		}
    }
    
    /**
     * Checks whether future events should be queued this frame
     */
    private void handleFutureEvents() {
		for (GameEvent ge : futureEvents.keySet()) {
			if (frameNumber == futureEvents.get(ge)) {
				queueEvent(ge);
				futureEvents.remove(ge);
			}
		}
    }
    
    /**
     * Gets the distance to the wall in front
     * @return the distance to the wall in front
     */
    public double getWallDistance() {
		return rend.getWallDistance();
    }
    
    /**
     * Returns the Game Loop's Mouse State
     * @return the Game Loop's Mouse State
     */
    public MouseState getMouseState() {
		return mouseState;
    }
    
    /**
     * Called by the treasure when the level is completed   
     */
    public void levelCompleted() {
        rend.setVictory(true);
        rend.setGameOver();
    }
    
    /**
     * Delays for a given amount of ms
     * @param ms the given amount of ms
     */
    public static void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}
