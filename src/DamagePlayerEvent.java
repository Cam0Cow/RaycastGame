import java.time.*;
import java.util.*;

public class DamagePlayerEvent extends GameEvent {
	
    private Entity e;
    
    public DamagePlayerEvent(Entity e) {
        this.e = e;
    }
    
	public void handle(GameState state, Duration dt) {
		Player p = state.getPlayer();
	    if (e.getDistance(p) < 1.1) p.damage(1);
	}
}