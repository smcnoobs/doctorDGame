package doctord;

import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class SceneLevel extends doctord.Scene {
	private Player player;
	private Pillar[] pillars;
	private Item[] items;
	private float gravity = (float)9.8;
	private Scanner levelScanner;
	private Animation background;
	
	// For Testing
	float x = 0;
	
	private void collideActables() {
		for(Pillar p : pillars)
			player.collide(p);
		for(Item i : items)
			player.collide(i);
	}
	
	public SceneLevel(ControlHandler controls) {
		super(controls);
	}
	
	public void loadLevel(String filename) {
		
	}
	
	/*
	 * Only For Testing
	 */
	public void loadDebugLevel() {
		Animation playerAnimation = new Animation();
		background = new Animation();
		try {
			
			Image playerImage = new Image("/res/images/dehkhoda_jetpack.png");
			playerAnimation = new Animation(new Image[] {playerImage}, 1, false);
			Image backgroundImage = new Image("/res/images/jupiterbg.jpg");
			background = new Animation(new Image[] {backgroundImage}, 1, false);
		} catch(SlickException sex) {
			sex.printStackTrace();
		}
		player = new Player(playerAnimation, new Vector2f(50,50), controls, 3, (float)50.0);
	}
	
	@Override
	public void update() {
		player.update();
	}
	
	@Override
	public void render(Graphics g) {
		if(background != null)
			background.draw(x--,-500);
		if(player != null)
			player.render(g);
	}
}
