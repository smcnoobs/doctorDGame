//Testing the GitHub uploading.

package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public interface Actable {
	
	/*
	 * Moves the Actable relative to its current location
	 * @param delta;
	 */
	public void move(Vector2f delta);
	
	/*
	 * Moves the Actable to a specific location
	 * @param location;
	 */
	public void moveTo(Vector2f location);
	
	/*
	 * Updates the Actable based on any changes 
	 * that have happened, logic goes here
	 */
	public void update();
	
	/*
	 * Draws the Actable to the Graphics object g
	 * @param g;
	 */
	public void render(Graphics g);
	
	/*
	 * Prepare the Actable to be removed from the game
	 * @param time;
	 */
	public void die(int time);
	
	/*
	 * Flags the Actable to be removed from the game
	 */
	public boolean isDead();
	
	/*
	 * Handle a potential collision with another Actable
	 * @param a;
	 */
	public boolean collide(Actable a);
	
	/*
	 * Returns the current location of the Actable as a Vector2f
	 */
	public Vector2f getLocation();
	
	/*
	 * Returns the Animation of the Actable
	 */
	public Animation getAnimation();
}
