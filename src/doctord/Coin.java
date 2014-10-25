package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;


public class Coin extends Item {
	private static int collected=0;
	
	public static int getCollected() {
		return collected;
	}
	
	public Coin(Animation sprites, Vector2f location) {
		super(sprites,location);
	}
	
	@Override
	public void die(int time) {
		super.die(time);
		collected++;
	}
}
