package doctord;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;

public class Pillar extends Actor {
	public static final int 	PILLAR_COUNT = 20;
	public static final float 	PILLAR_HEIGHT = (float)50.0;
	
	private pillarBlock[] blocks = new pillarBlock[PILLAR_COUNT];
	
	public Pillar(Animation sprites, Vector2f location) {
		super(sprites, location);
		
	}
	
	public void hideBlock(int index) {
		if(index < blocks.length) 
			blocks[index].hide();
	}
	public void showBlock(int index) {
		if(index < blocks.length) 
			blocks[index].show();
	}
}
