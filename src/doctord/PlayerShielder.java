package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;


public class PlayerShielder extends Item {
	private int duration = 0;
	
	public PlayerShielder(int d, Animation s, Vector2f location) {
		super(s, location);
		this.duration =  d;
	}
}
