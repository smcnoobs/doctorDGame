package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class pillarBlock extends Actor {
	private boolean hidden = false;
	
	public pillarBlock(Animation sprites, Vector2f location) {
		super(sprites, location);
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void hide() {
		hidden = true;
	}
	public void show() {
		hidden = false;
	}
}
