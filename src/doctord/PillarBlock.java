package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class PillarBlock extends Actor {
	private boolean hidden = false;
	public static final float BLOCK_SPEED = (float)10;
	
	public PillarBlock(Animation sprites, Vector2f location) {
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
	
	@Override
	public void render(Graphics g) {
		if(!isHidden())
			super.render(g);
	}
	
	@Override
	public void render(Graphics g, float f) {
		if(!isHidden())
			super.render(g,f);
	}
	
	@Override
	public void update() {
		move(new Vector2f(- BLOCK_SPEED, 0));
	}
}
