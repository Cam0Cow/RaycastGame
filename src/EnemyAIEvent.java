import java.time.*;

/**
 * Represents an update to the enemy AI
 * Chases the player, but is really dumb
 */
public class EnemyAIEvent extends GameEvent {
	
	Entity ent;
	
	/**
	 * Constructs a new enemy AI for a given entity
	 */
	public EnemyAIEvent(Entity e) {
		ent = e;
	} 
	
	/**
	 * Handles the enemy AI being triggered by the game
	 * @param state the current game state
	 * @param dt the elapsed time from last frame
	 */
	public void handle(GameState state, Duration dt) {
		Player p = state.getPlayer();
		MapTile[][] map = state.getLevelMap().getMap();
		double dx = p.getPosX() - ent.getX();
		double dy = p.getPosY() - ent.getY();
		double mag = Math.sqrt(dx*dx+dy*dy);
		dx /= mag;
		dy /= mag;
		
		if (mag > 1) {
			double x = ent.getX();
			double y = ent.getY();
			double xn = x + dx / 50;
			double yn = y + dy / 50;
			if (map[(int)y][(int)xn] == MapTile.NOTHING) ent.setX(xn);
			if (map[(int)yn][(int)x] == MapTile.NOTHING) ent.setY(yn);
		}
	}
}