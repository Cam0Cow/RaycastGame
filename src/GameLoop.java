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
			while (!disp.isFrameDone());
            disp.resetFrameStatus();
			rend.render(state);
            disp.show(rend);
            System.out.println (dt.toMillis());
		}
	}
    
    private void handleKeys(Duration dt) {
        Iterator<Integer> keys = KeyState.getKeyState().iterator();
        Player p = state.getPlayer();
        while (keys.hasNext()) {
            switch (keys.next()) {
                case KeyEvent.VK_ESCAPE: System.exit(0); break;
                case KeyEvent.VK_W: {
	    			p.setPosX(p.getPosX()+p.getDirX()/10.0);
	    			p.setPosY(p.getPosY()+p.getDirY()/10.0);
	    			break;
    			}
	    		case KeyEvent.VK_S: {
	    			p.setPosX(p.getPosX()-p.getDirX()/10.0);
	    			p.setPosY(p.getPosY()-p.getDirY()/10.0);
	    			break;
	    		}
	    		case KeyEvent.VK_D: {
	    			p.setPosX(p.getPosX()-p.getDirY()/10.0);
	    			p.setPosY(p.getPosY()+p.getDirX()/10.0);
	    			break;
	    		}
	    		case KeyEvent.VK_A: {
	    			p.setPosX(p.getPosX()+p.getDirY()/10.0);
	    			p.setPosY(p.getPosY()-p.getDirX()/10.0);
	    			break;
	    		}
	    		case KeyEvent.VK_RIGHT: {
	    			double x = p.getDirX();
	    			double y = p.getDirY();
	    			double angle = Math.PI/50;
	    			p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
	    			p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
	    			break;
	    		}
	    		case KeyEvent.VK_LEFT: {
	    			double x = p.getDirX();
	    			double y = p.getDirY();
	    			double angle = -Math.PI/50;
	    			p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
	    			p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
	    			break;
	    		}
            }
        }
    }
    
    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}








