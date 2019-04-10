import java.util.*;
import java.time.chrono.*;
import java.time.*;
import java.awt.event.*;

public class GameLoop {
	
	private GameState state;
	private Renderer rend;
    private Display disp;
	private PriorityQueue<GameEvent> queue;
	private Instant previousFrame;
	
	public GameLoop(GameState gs, Renderer r, Display d) {
		state = gs;
		rend = r;
        disp = d;
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
            handleKeys(dt);
			while (!queue.isEmpty()) {
				queue.poll().handle(state, dt);
			}
			rend.render(state);
            disp.show(rend);
            while (!disp.isFrameDone());
            disp.resetFrameStatus();
		}
	}
    
    private void handleKeys(Duration dt) {
        Iterator<Integer> keys = KeyState.getKeyState().iterator();
        while (keys.hasNext()) {
            switch (keys.next()) {
                case KeyEvent.VK_ESCAPE: System.exit(0); break;
            }
        }
    }
    
    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}








