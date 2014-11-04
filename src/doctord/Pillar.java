package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Pillar extends Actor {
	public static final int 	PILLAR_COUNT = 20;
	public static final float 	PILLAR_HEIGHT = (float)50.0;
	
	private PillarBlock[] blocks = new PillarBlock[PILLAR_COUNT];
	
	public Pillar(Animation sprites, Vector2f location) {
		super(sprites, location);
		for(int i = 0; i < PILLAR_COUNT; i++) {
			Vector2f loc = new Vector2f(location.getX(), location.getY() + i * 50);
			blocks[i] = new PillarBlock(sprites,loc);
		}
	}
	
	public void hideBlock(int index) {
		if(index < blocks.length) 
			blocks[index].hide();
	}
	public void showBlock(int index) {
		if(index < blocks.length) 
			blocks[index].show();
	}
	
	@Override
	public void render(Graphics g) {
		for(PillarBlock pb : blocks) 
			pb.render(g);
	}
	
	@Override
	public void update() {
		for(PillarBlock pb : blocks)
			pb.update();
	}
	
	public PillarBlock[] getBlocks() {
		return blocks;
	}
}
