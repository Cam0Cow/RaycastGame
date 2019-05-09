import java.time.*;
import java.util.*;

public class DamagePlayerEvent extends GameEvent {
	
	public void handle(GameState state, Duration dt) {
		Player p = state.getPlayer();
		Set<Entity> entities = state.getLevelMap().getEntities();
		for (Entity e : entities) {
			if (e.getDistance(p) < 1.5) p.damage(1);
		}
	}
}