package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class PillarBlock extends Actor {
	private boolean hidden = false;
	public static final float BLOCK_SPEED = (float)5;
	
	public PillarBlock(Animation sprites, Vector2f location) {
		super(sprites, location);
//		System.out.println("New PillarBlock --- Animation Null? " + (sprites == null) + " Location null? " + (location == null));
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
		super.render(g);
		g.drawString("Block", location.getX(), location.getY());
	}
	
	@Override
	public void update() {
		location.set(location.getX() - BLOCK_SPEED, location.getY());
	}
}
