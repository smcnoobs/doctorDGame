package doctord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LevelScene extends doctord.Scene {
	// Actables
	private Player player;
	private ArrayList<Pillar> pillars;
	private ArrayList<Item> items;
	
	// Level Constants
	private float gravity = (float)9.8;
	private Music music;
	private Animation background;
	private boolean level_did_load;
	private static boolean paused = false;
	private StatDisplay HUD;
	private String levelName, filename;
	private static String lname;
	
	
	// For Testing
	float distancePassed = 0;
	
	private void collideActables() {
		for(int i = 0; i < pillars.size(); i++) {
			if(pillars.get(i).isDead())
				pillars.remove(i);
			else
				player.collide(pillars.get(i));
		}
		for(Item i : items) {
			if(i.isDead())
				i = null;
			else
				player.collide(i);
		}
	}
	
	public boolean loadLevel(String filename) {
		LevelLoader l = new LevelLoader(filename);
		level_did_load = l.load();
		player = l.getPlayer();
		pillars = l.getPillars();
		items = l.getItems();
		gravity = l.getGravity();
		background = l.getBackground();
		music = l.getMusic();
		levelName = l.getLevelName();
		lname = l.getLevelName();
		return level_did_load;
	}
	
	public void reloadLevel() {
		// Clean Level
		player = null;
		pillars.clear();
		items.clear();
		// load level
		loadLevel(this.filename);
	}
	
	public LevelScene() {		
		pillars = new ArrayList<Pillar>();
		items = new ArrayList<Item>();
		HUD = new StatDisplay();
	}
	
	//@Override
	public void load(String s) {
		filename = s;
		loadLevel(s);
	}
	
	
	
	@Override
	public void update() {
		if(!paused && Player.getHealth() > 0)
			updateNormally();
		else 
			updatePaused();
	}
	
	private void updateNormally() {
		if(music != null && !doctorDGame.isMuted() && !music.playing())
			music.play();
		
		distancePassed = distancePassed - PillarBlock.BLOCK_SPEED / Pillar.PILLAR_WIDTH;
		if(!levelName.equals(lname))
			lname = levelName;
		
		for(Pillar p : pillars)
			p.update();
		for(Item i : items)
			i.update();
		player.update();
		HUD.update();
		
		collideActables();
		
		if(pillars.isEmpty() && !paused) {
			finished = true;
		}
	}
	
	private void updatePaused() {
		if(!paused)
			paused = true;
	}
	
	@Override
	public void playMusic() {
		if(music != null && !music.playing()) 
			music.play();
	}
	
	@Override
	public void silenceMusic() {
		if(music != null) {
			music.setVolume(music.getVolume() - 0.1f);
		}
	}
	
	@Override
	public void unSilenceMusic() {
		if(music != null)
			music.setVolume(1.0f);
	}
	
	@Override
	public void stopMusic() {
		if(music != null)
			music.stop();
	}
	
	@Override
	public void render(Graphics g) {
		if(background != null)
			background.draw(distancePassed,0);

		renderPillars(g);
		renderItems(g);
		player.render(g);
		
		if(paused) {
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0,0,1920,1080);
			HUD.renderPauseScreen(g);
			if(music != null)
				music.setVolume(0.5f);
		} else if(music != null)
			music.setVolume(1.0f);
		HUD.render(g);
	}
	
	private void renderPillars(Graphics g) {
		boolean hasDrawn = false;
		for(Pillar p : pillars) {
			if(isOnScreen(p.getLocation())) {
				if(!p.isDead()) {
					p.render(g);
					hasDrawn = true;
				}
			}
			else {
				if(!hasDrawn)
					p.die(0);	// No Longer on Screen -> Release from memory
				else
					break;
			}
		}
	}
	
	private void renderItems(Graphics g) {
		for(Item i : items) {
			if(isOnScreen(i.getLocation())) {
				if(!i.isDead()) {
					i.render(g);
				}
			}
		}
	}
	
	private boolean isOnScreen(Vector2f loc) {
		return loc.getX() >= -200
				&& loc.getX() <= 2120
				&& loc.getY() >= 0
				&& loc.getY() <= 1080;
	}
	
	public float getGravity() {
		return gravity;
	}
	
	public static String getLevelName() {
		return lname;
	}
	
	public void pause() {
		if(!paused)
			paused = true;
		else
			paused = false;
	}
	
	public static boolean isPaused() {
		return paused;
	}
}
