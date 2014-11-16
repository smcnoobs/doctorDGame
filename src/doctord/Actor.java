package doctord;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Animation;
public class Actor implements Actable {
	protected Animation sprites;
	protected Vector2f location;	

	public Actor ( Animation sprites, Vector2f location)
	{
		this.sprites = sprites;
		this.location = location;
	}
	public void move(Vector2f delta) {
		this.location.set(this.location.getX() + delta.getX(), this.location.getY() + delta.getY());
	}
	public void moveTo(Vector2f location) {
		this.location.set(location.getX(), location.getY());
	}
	public void update() {
		// TODO Auto-generated method stub
	}
	public void render(Graphics g) {
		if(sprites != null && location != null) {
			sprites.draw(location.getX(), location.getY());
		}
	}
	public void render(Graphics g, float scale) {
		if(sprites != null && location != null) {
			sprites.getCurrentFrame().draw(location.getX(), location.getY(), scale);
		}
	}
	public void die(int time) {
		// TODO Auto-generated method stub
	}
	public boolean isDead() {
		return false;
	}
	public void collide(Actable a) {
		/*
		 * Collide Actables by comparing alpha masks of current images of the Actables
		 */
	}
	public Vector2f getLocation() {
		return location;
	}
	public Animation getAnimation() {
		return sprites;
	}
}