package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class PlayerRestorer extends Item {

	private int health;
	private float fuel;
	
	
	public PlayerRestorer( int h, float f, Animation a, Vector2f v) 
	{
		super(a,v);
		this.health = h;
		this.fuel = f;
	}
	
	public int getHealth() {
		return health;
	}
	
	public float getFuel() {
		return fuel;
	}
}
