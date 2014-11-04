package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class Item implements Actable {
	private Vector2f location;
	private Animation sprites;
	private boolean isDead=false;
	
	//@Override
	public void move(Vector2f delta) {
		this.location.set(location.getX() + delta.getX(),location.getY() + delta.getY());
	}
	
	//@Override
	public void moveTo(Vector2f location) {
		this.location = location;
	}
	
	//@Override
	public void update() {
		location.set(location.getX() - PillarBlock.BLOCK_SPEED, location.getY());
	}
	
	//@Override
	public void render(Graphics g){
		if(sprites != null && location != null) {
			sprites.draw(location.getX(), location.getY());
		}
	}
	
	//@Override
	public void die(int time) {
		isDead = true;
	}
	
	//@Override
	public boolean isDead() {
		return this.isDead;
	}
	
	/*
	 * precondition: Actable a is already verified to be 'colliding' with this object
	 * @see Actable#collide(Actable)
	 * @Override
	 */
	public void collide(Actable a) {
		if(a instanceof Player) 
			die(0); // Figure out timing later
	}
	
	//@Override
	public Vector2f getLocation() {
		return this.location;
	}
	
	public Item(Animation sprites, Vector2f location) {
		this.sprites = sprites;
		this.location = location;
	}

	//@Override
	public Animation getAnimation() {
		return sprites;
	}
}
