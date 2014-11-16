package levelEditor;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import doctord.Pillar;
import doctord.doctorDGame;

public class PillarMaker extends BasicGame {
	private String sprites_filepath;
	private Pillar[] pillars;
	private boolean finished;

	public PillarMaker(String title, int length) {
		super(title);
		pillars = new Pillar[length];
	}
	//private Pillar[] pillars;

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for(Pillar p : pillars)
			p.render(g, 0.5f);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		finished = false;
		sprites_filepath = (String)JOptionPane.showInputDialog(
                new Frame(),
                "Please Enter the location of the Animation Files for Pillars",
                "Pillar Maker - Sprites Filepath",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "./res/images/");
		
		Image[] images = new Image[1];
		images[0] = new Image(sprites_filepath);
		Animation pa = new Animation(images,1,false);
		
		for(int i = 0; i < pillars.length; i++) 
			pillars[i] = new Pillar(pa, new Vector2f(i * Pillar.PILLAR_WIDTH, 0));
		
		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		finished = true;
		gc.exit();
	}
	
	public void run() throws SlickException {
		AppGameContainer appgc;
		appgc = new AppGameContainer(this);
		appgc.setDisplayMode(1600, 900, false);
		appgc.start();
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public Pillar[] getPillars() {
		return pillars;
	}
}
