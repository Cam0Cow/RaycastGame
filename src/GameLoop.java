import java.util.*;
import java.time.chrono.*;
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
	private Map<GameEvent, Integer> registeredEvents;
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
		registeredEvents = new HashMap<GameEvent, Integer>();
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
	 * Called when the game is in progress
	 * Runs everything basically
	 */
	public void loop() {
		boolean done = false;
		previousFrame = Instant.now().minus(Duration.ofMillis(1)); // not quite now
		while (!done) {
			Instant now = Instant.now();
			Duration dt = Duration.between(previousFrame, now);
			previousFrame = now;
			state.getFPS().addFrame(dt.toMillis());
            handleKeys(dt);
            handleMouse();
            handleRepeatedEvents();
			while (!queue.isEmpty()) {
				queue.poll().handle(state, dt);
			}
			while (!disp.isFrameDone());
            disp.resetFrameStatus();
			rend.render(state);
            disp.show(rend);
            frameNumber++;
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
                case KeyEvent.VK_ESCAPE: System.exit(0); break;
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
     * Handles camera rotation due to mouse movements
     */
    public void handleMouse() {
        Player p = state.getPlayer();
        double x = p.getDirX();
        double y = p.getDirY();
    	double angle = mouseState.getDeltaAngle();
        p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
        p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
    }
    
    /**
     * Checks whether repeated events should be requeued this frame
     */
    public void handleRepeatedEvents() {
    	for (GameEvent ge : registeredEvents.keySet()) {
    		if (frameNumber % registeredEvents.get(ge) == 0) {
    			queueEvent(ge);
    		}
    	}
    }
    
    /**
     * Delays for a given amount of ms
     * @param ms the given amount of ms
     */
    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}








