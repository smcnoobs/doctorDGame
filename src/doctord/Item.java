package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class Item implements Actable {
	private Vector2f location;
	private Animation sprites;
	private boolean isDead=false;
	
	public void move(Vector2f delta) {
		this.location.set(location.getX() + delta.getX(),location.getY() + delta.getY());
	}
	
	public void moveTo(Vector2f location) {
		this.location = location;
	}
	
	
	public void update() {
		// Done
	}
	
	
	public void render(Graphics g){
		// g.draw(Animation) ?
		// Figure out later
	}
	
	
	public void die(int time) {
		isDead = true;
	}
	
	
	public boolean isDead() {
		return this.isDead;
	}
	
	/*
	 * precondition: Actable a is already verified to be 'colliding' with this object
	 * @see Actable#collide(Actable)
	 */
	public void collide(Actable a) {
		if(a instanceof Player) 
			die(0); // Figure out timing later
	}
	
	
	public Vector2f getLocation() {
		return this.location;
	}
	
	public Item(Animation sprites, Vector2f location) {
		this.sprites = sprites;
		this.location = location;
	}
}
