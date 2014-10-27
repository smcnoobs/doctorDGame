package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class PlayerWarper extends Item {

	private int duration;
	
	public PlayerWarper(int dur, Animation sprites, Vector2f location ){
		super(sprites, location);
		this.duration= dur;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
}
