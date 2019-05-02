import java.time.*;

/**
 * Represents a request update for an animation on the
 * main game loop
 */
public class AnimationUpdateEvent extends GameEvent {
	
	private Animatable obj;
	
	/**
	 * Constructs a new animation update event from a given animatable
	 * @param a the given Animatable
	 */
	public AnimationUpdateEvent(Animatable a) {
		obj = a;
	}
	
	/*
	 * Called when the game loop receives the animation update event
	 * @param state the current game state
	 * @param dt the last frame time
	 */
	public void handle(GameState state, Duration dt) {
		obj.nextFrame(state);
	}
}