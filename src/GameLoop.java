import java.util.*;
import java.time.chrono.*;
import java.time.*;
import java.awt.event.*;

public class GameLoop {
	
	private GameState state;
	private Renderer rend;
	private PriorityQueue<GameEvent> queue;
	private Instant previousFrame;
	
	public GameLoop(GameState gs, Renderer r) {
		state = gs;
		rend = r;
		queue = new PriorityQueue<GameEvent>();
		previousFrame = Instant.now();
	}
	
	public void queueEvent(GameEvent ge) {
		queue.offer(ge);
	}
	
	public void loop() {
		boolean done = false;
		previousFrame = Instant.now().minus(Duration.ofMillis(1)); // not quite now
		while (!done) {
			Instant now = Instant.now();
			Duration dt = Duration.between(previousFrame, now);
			previousFrame = now;
			while (!queue.isEmpty()) {
				queue.poll().handle(state, dt);
			}
			rend.render(state);
		}
	}
}