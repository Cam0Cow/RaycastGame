import java.util.*;

/**
 * Represents an FPS counter
 */
public class FPS {
	
	public Queue<Long> frames;
	public long total;
	
	private static final int QUEUE_SIZE = 60;
	
	/**
	 * Constructs a new FPS counter
	 */
	public FPS() {
		frames = new LinkedList<Long>();
		total = 0;
	}
	
	/**
	 * Gets the FPS
	 * @return the FPS
	 */
	public int getFPS() {
		return (int) (total / QUEUE_SIZE);
	}
	
	/**
	 * Adds a new frame for consideration
	 * @param dt the new frame time
	 */
	public void addFrame(long dt) {
		total += dt;
		frames.offer(dt);
		if (frames.size() > QUEUE_SIZE) total -= frames.poll();
	}
}