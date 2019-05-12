import java.time.*;

/**
 * Represents a requested termination of an entity
 */
public class EntityCleanupEvent extends GameEvent {
	
	private Entity e;
	
	/**
	 * Constructs an entity cleanup event
	 * @param e the entity to cleanup
	 */
	public EntityCleanupEvent(Entity e) {
		this.e = e;
	}
	
	/**
	 * Triggered by the game loop and handles the request
	 * @param state the current game state
	 * @param dt the duration of the last frame
	 */
	public void handle(GameState state, Duration dt) {
		state.getLevelMap().getEntities().remove(e);
        e.kill();
	}
}