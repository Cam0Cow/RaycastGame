import java.time.*;

public class WeaponCooldownEvent extends GameEvent {
	
	private Weapon weapon;
	
	public WeaponCooldownEvent(Weapon w) {
		weapon = w;
	}
	
	public void handle(GameState state, Duration dt) {
		weapon.endCooldown();
	}
}