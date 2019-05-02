import java.time.*;
import java.util.*;

public class BulletEvent extends GameEvent {
	
	public void handle(GameState state, Duration dt) {
		HashSet<Entity> entities = state.getLevelMap().getEntities();
		Player p = state.getPlayer();
		double dirX = p.getDirX();
		double dirY = p.getDirY();
		double x = p.getPosX();
		double y = p.getPosY();
		double mag = Math.sqrt(dirX*dirX+dirY*dirY);
		for (Entity e : entities) {
			double ex = e.getX() - x;
			double ey = e.getY() - y;
			double dot = (dirX*ex+dirY*ey)/(mag*Math.sqrt(ex*ex+ey*ey));
			if (dot > 0.98) {
				e.damage(1);
				if (e.isDead()) state.getGameLoop().queueEvent(new EntityCleanupEvent(e));
			}
		}
	}
}