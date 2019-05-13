import java.util.*;

/**
 * Represents an FPS counter
 */
public class FPS {
	
	private Queue<Long> frames;
	private long total;
	
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
		return (int) (1000.0 * QUEUE_SIZE / total);
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