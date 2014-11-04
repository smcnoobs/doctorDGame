package levelEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import doctord.*;

public class LevelWriter {
	private PrintWriter pw;
	private Item[] items;
	private Pillar[] pillars;
	private Player player;
	private float gravity;
	private int length;
	private int tab = 0;
	
	public static final String LEVEL = "LEVEL";
	public static final String LEVEL_GRAVITY = "GRAVITY";
	public static final String LEVEL_LENGTH = "LENGTH";
	public static final String ACTABLE = "Actable";
	public static final String TYPE = "type";
	
	public static final String URL = "URL";
	public static final String IMAGECOUNT = "IMAGECOUNT";
	public static final String PLAYER_HEALTH = "HEALTH";
	public static final String PLAYER_FUEL = "FUEL";
	
	public static final String PLAYER_SHIELDER_DURATION = "DURATION";
	public static final String PLAYER_WARPER_DURATION = "DURATION";
	
	public static final String PROJECTILE_DAMAGE = "DAMAGE";
	
	// General Level Writer Code
	public LevelWriter(String filename) throws FileNotFoundException {
		pw = new PrintWriter(filename);
	}
	
	public void loadAssets(Item[] items, Pillar[] pillars, Player player, float gravity, int length) {
		this.items = items;
		this.pillars = pillars;
		this.player = player;
		this.gravity = gravity;
		this.length = length;
	}
	
	public void writeLevel() {
		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println(basicOpenTag(LEVEL));
		
		pw.println(shortTag(LEVEL_GRAVITY, new String("") + gravity));
		pw.println(shortTag(LEVEL_LENGTH, new String("") + length));
		
		pw.println("");
		for(Item i : items) 
			writeActable(i);
		
		pw.println("");
		for(Pillar p : pillars)
			writeActable(p);
		
		pw.println("");
		writeActable(player);
		
		pw.println("");
		pw.println(basicCloseTag(LEVEL));
	}
	
	public void finishWriting() {
		pw.close();
	}
	
	
	// Actable Specific Code
	private void writeAnimation(Animation animation) {
		pw.println(openTag(animation));
		pw.println(shortTag(IMAGECOUNT,new String("" + animation.getFrameCount())));
		
		for(int i = 0; i < animation.getFrameCount(); i++) {
			writeImage(animation.getImage(i));
		}
		
		pw.println(closeTag(animation));
	}
	
	private void writeImage(Image image) {
		pw.println(openTag(image));
		
		pw.println(shortTag(URL,image.getResourceReference()));
		
		pw.println(closeTag(image));
	}
	
	private void writeLocation(Vector2f location) {
		pw.println(openTag(location));
		
		pw.println(shortTag("X",new String("" + location.getX())));
		pw.println(shortTag("Y",new String("" + location.getY())));
		
		pw.println(closeTag(location));
	}
	
	private void writeActable(Actable a) {
		pw.println(openTagWithType(ACTABLE,TYPE,a));
		
		if(a instanceof Player) {
			pw.println(shortTag(PLAYER_HEALTH, new String("") + Player.getHealth()));
			pw.println(shortTag(PLAYER_FUEL, new String("") + Player.getFuel()));
		}
		if(a instanceof PlayerShielder) {
			pw.println(shortTag(PLAYER_SHIELDER_DURATION, new String("") + ((PlayerShielder)a).getDuration()));
		}
		if(a instanceof PlayerWarper) {
			pw.println(shortTag(PLAYER_WARPER_DURATION, new String("") + ((PlayerWarper)a).getDuration()));
		}
		if(a instanceof PlayerRestorer) {
			pw.println(shortTag(PLAYER_HEALTH, new String("") + ((PlayerRestorer)a).getHealth()));
			pw.println(shortTag(PLAYER_FUEL, new String("") + ((PlayerRestorer)a).getFuel()));
		}
		if(a instanceof Projectile) {
			pw.println(shortTag(PROJECTILE_DAMAGE, new String("") + ((Projectile)a).getDamage()));
		}
		
		writeAnimation(a.getAnimation());
		writeLocation(a.getLocation());
		
		pw.println(basicCloseTag(ACTABLE));
	}
	
	// BASIC XML FUNCTIONS
	private String basicOpenTag(String s) {
		String t = new String("");
		for(int i =0; i < tab; i++) {
			t += "\t";
		}
		tab++;
		return t + "<" + s + ">";
	}
	
	private String basicCloseTag(String s) {
		String t = new String("");
		tab--;
		for(int i =0; i < tab; i++) {
			t += "\t";
		}
		return t + "</" + s + ">";
	}
	
	private String openTag(Object obj) {
		return basicOpenTag(obj.getClass().getSimpleName());
	}
	
	private String closeTag(Object obj) {
		return basicCloseTag(obj.getClass().getSimpleName());
	}
	
	private String shortTag(String tagName, String value) {
		String r = basicOpenTag(tagName) + value + "</" + tagName + ">";
		tab--;
		return r;
	}
	
	private String openTagWithType(String tagName, String identifier, Object obj) {
		String t = "";
		for(int i =0; i < tab; i++) {
			t += "\t";
		}
		tab++;
		
		return t + "<" + tagName + " " + identifier + "=\"" + obj.getClass().getSimpleName() + "\">";
	}
}
