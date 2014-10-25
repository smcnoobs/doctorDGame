package doctord;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Vector2f;

public class ControlHandler
{
	  private static final float UP = (float)(-15.0);
	  private static final float DOWN = (float)5.0;
	  private float gravity;
	  private Vector2f direction = new Vector2f(0,DOWN);
	  private Vector2f velocity = new Vector2f(0,0);
	  
	  public void updateDirection()
	  {
		  
	  }
	  
	  public void setGravity(float gravity) {
		  this.gravity = gravity;
	  }
	  
	  public float getGravity() {
		  return gravity;
	  }
	  
	  /*
	   * Process: 	(1) calculate new velocity based on current Acceleration.
	   * 			(2) calculate the new Location based on the current velocity
	   */
	  public Vector2f getNewLocation() {
		  velocity.set(0,newVelocity(direction.getY()));
		  return velocity;
	  }
	  
	  private float newVelocity(float v) {
		  // v_f = v_0 + at -> t = 1
		  
		  return v - (v * gravity);
	  } 
	  
	  
	  public void respondPressed(Command arg) {
		  direction.set(0,UP);
	  }
	  
	  public void respondReleased(Command arg) {
		  direction.set(0,DOWN);
	  }
	  
	  public Vector2f getDirection() {
		  return direction;
	  }
}
