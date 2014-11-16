package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Pillar extends Actor {
	public static final int 	PILLAR_COUNT = 20;
	public static final float 	PILLAR_HEIGHT = (float)50.0;
	public static final float 	PILLAR_WIDTH = (float)50.0;
	
	private PillarBlock[] blocks = new PillarBlock[PILLAR_COUNT];
	
	public Pillar(Animation sprites, Vector2f location) {
		super(sprites, location);
		for(int i = 0; i < PILLAR_COUNT; i++) {
			Vector2f loc = new Vector2f(location.getX(), location.getY() + i * (PILLAR_HEIGHT + 4));
			blocks[i] = new PillarBlock(sprites,loc);
		}
	}
	
	public Pillar(Animation sprites, Vector2f location, String hide) {
		this(sprites,location);
		for(int i = 0; i < PILLAR_COUNT; i++) {
			if(hide.charAt(i) == 'h')
				blocks[i].hide();
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
	public void render(Graphics g, float f) {
		for(PillarBlock pb : blocks) 
			pb.render(g,f);
	}
	
	@Override
	public void update() {
		this.move(new Vector2f(new Vector2f(- PillarBlock.BLOCK_SPEED, 0)));
	}
	
	public PillarBlock[] getBlocks() {
		return blocks;
	}
	
	@Override
	public void move(Vector2f delta) {
		super.move(delta);
		for(PillarBlock pb : blocks)
			pb.move(delta);
	}
}
