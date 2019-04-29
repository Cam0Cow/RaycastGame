import java.time.*;

public class EnemyAIEvent extends GameEvent {
	
	Entity ent;
	
	public EnemyAIEvent(Entity e) {
		ent = e;
	} 
	
	public void handle(GameState state, Duration dt) {
		Player p = state.getPlayer();
		ent.setX(p.getPosX() - 1);
		ent.setY(p.getPosY() - 1);
	}
}