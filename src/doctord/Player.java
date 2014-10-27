package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;


public class Player extends Actor {
	private static int health;
	private static float fuel;
	private int effectDuration;
	private PlayerEffect currentEffect = PlayerEffect.NEUTRAL;
	private ControlHandler controls;

	public Player(Animation spirtes, Vector2f location, ControlHandler input, int health, float fuel) {
		super(spirtes, location);
		Player.health = health;
		Player.fuel = fuel;
		this.controls = input;
	}
	
	public void setControls(ControlHandler input) {
		this.controls = input;
	}

	public void shield(int time) {
		currentEffect = PlayerEffect.SHIELDED;
		effectDuration = time;
	}
	
	public static int getHealth() {
		return Player.health;
	}
	
	public static float getFuel() {
		return Player.fuel;
	}
	
	@Override
	public void update() {
		// Logic for player's motion!
		Vector2f loc = controls.getNewLocation();
		super.location.set(loc.getX() + super.location.getX(),
				loc.getY() + super.location.getY());
	}
}
