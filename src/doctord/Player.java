package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class Player extends Actor {
	private Vector2f inertia;
	private final float weight = 20, maxSpeed = 15;
	private static final int MAX_HEALTH = 5;
	private static final float MAX_FUEL = 200;
	private static int health;
	private static float fuel;
	private int effectDuration;
	private static PlayerEffect currentEffect = PlayerEffect.NEUTRAL;
//	private ControlHandler controls;

	public Player(Animation spirtes, Vector2f location, int health, float fuel) {
		super(spirtes, location);
		Player.health = health;
		Player.fuel = fuel;
		
		inertia = new Vector2f(0,0);
	}
	
	public void setControls(ControlHandler input) {
//		this.controls = input;
	}

	public void shield(int time) {
		currentEffect = PlayerEffect.SHIELDED;
		effectDuration = time;
	}
	
	public static int getHealth() {
		return health;
	}
	
	public static float getFuel() {
		return fuel;
	}
	
	@Override
	public void update() {
		if(health > MAX_HEALTH)
			health = MAX_HEALTH;
		if(fuel > MAX_FUEL)
			fuel = MAX_FUEL;
		inertia.set( // Fall down for the current level's gravity
				inertia.getX(),
				inertia.getY() + 
				(((LevelScene)doctorDGame.getCurrentScene()).getGravity() * weight / 120)
				);
		
		if(fuel > 0 && doctorDGame.spaceBarIsDown()) {
				fuel = fuel - 0.01f * ((LevelScene)doctorDGame.getCurrentScene()).getGravity();
			inertia.set(
					inertia.getX(),
					inertia.getY() - 5
					);
		}
		
		if(inertia.getY() > maxSpeed)
			inertia.set(inertia.getX(), maxSpeed);
		if(inertia.getY() < -maxSpeed / 2)
			inertia.set(inertia.getX(), -maxSpeed / 2);
		
		if(currentEffect != PlayerEffect.NEUTRAL) {
			if (effectDuration > 0)
				effectDuration--;
			else
				currentEffect = PlayerEffect.NEUTRAL;
		}
		
		if(location.getY() + inertia.getY() > 0 && location.getY() + inertia.getY() < 1000)
			move(inertia);
	}
	
	@Override
	public void render(Graphics g) {
		if(currentEffect != PlayerEffect.SHIELDED)
			super.render(g);
		else {
			if(sprites != null && location != null) {
				sprites.draw(location.getX() * doctorDGame.getScale(), location.getY() * doctorDGame.getScale(),
						sprites.getCurrentFrame().getWidth()  * doctorDGame.getScale(), sprites.getCurrentFrame().getHeight()  * doctorDGame.getScale(), new Color(150,40,27,200));
			}
		}
	}
	
	@Override
	public boolean collide(Actable a) {
		if(a instanceof Item && super.collide(a)) {
			if(a instanceof PlayerRestorer) {
				health += ((PlayerRestorer)a).getHealth();
				fuel += ((PlayerRestorer)a).getFuel();
			}
			if(a instanceof Projectile)
				health -= ((Projectile)a).getDamage();
			a.die(0);
			return true;
		}
		if(currentEffect != PlayerEffect.SHIELDED) {
			// For a Pillar, check against all of the blocks!
			if(a instanceof Pillar) {
				for(PillarBlock pb : ((Pillar)a).getBlocks()) {
					if(super.collide(pb) && !pb.isHidden()) {
						if(health > 0)
							health--;
						currentEffect = PlayerEffect.SHIELDED;
						effectDuration = Pillar.WAIT_TIME;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static PlayerEffect getCurrentEffect() {
		return currentEffect;
	}
}
