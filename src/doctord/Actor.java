package doctord;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Animation;
public class Actor implements Actable {
	protected Animation sprites;
	protected Vector2f location;
	protected boolean isdead = false;

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
		isdead = true;
	}
	public boolean isDead() {
		return isdead;
	}
	public boolean collide(Actable a) {
		return isCollidingByRect(a);
	}
	
	private boolean isCollidingByRect(Actable a) {
		float rect1x = location.getX();
        float rect1y = location.getY();
        float rect1w = (float)sprites.getCurrentFrame().getWidth();
        float rect1h = (float)sprites.getCurrentFrame().getHeight();
 
        float rect2x = a.getLocation().getX();
        float rect2y = a.getLocation().getY();
        float rect2w = (float)a.getAnimation().getCurrentFrame().getWidth();
        float rect2h = (float)a.getAnimation().getCurrentFrame().getHeight();
 
        return (rect1x + rect1w >= rect2x &&
                rect1y + rect1h >= rect2y &&
                rect1x <= rect2x + rect2w &&
                rect1y <= rect2y + rect2h);
	}
	
	public Vector2f getLocation() {
		return location;
	}
	public Animation getAnimation() {
		return sprites;
	}
}