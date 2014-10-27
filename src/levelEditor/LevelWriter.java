package levelEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import doctord.Item;
import doctord.Player;
//import doctord.Pillar;

public class LevelWriter {
	private PrintWriter pw;
	public static final String URL = "URL";
	public static final String IMAGECOUNT = "IMAGECOUNT";
	public static final String HEALTH = "HEALTH";
	public static final String FUEL = "FUEL";
	
	
	// General Level Writer Code
	public LevelWriter(String filename) throws FileNotFoundException {
		pw = new PrintWriter(new File(filename));
	}
	
	public void writeLevel() {
		/*
		 * Pseudo Code: 
		 * 	Write Generic Level Info
		 * 	Write Player Info
		 * 	Write Pillars
		 * 	Write Items
		 */
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
		
		pw.println(openTag(location));
	}
	
	// Item Specific Code
	private void writeItem(Item item) {
		pw.println(openTag(item));
		
		writeAnimation(item.getAnimation());
		writeLocation(item.getLocation());
		
		pw.println(closeTag(item));
	}
	
	public void writePlayer(Player p) {
		pw.println(openTag(p));
		
		pw.println(shortTag(HEALTH, new String("") + Player.getHealth()));
		pw.println(shortTag(FUEL, new String("") + Player.getFuel()));
		
		writeAnimation(p.getAnimation());
		writeLocation(p.getLocation());
		
		pw.println(closeTag(p));
	}
	
	// BASIC XML FUNCTIONS
	private String basicOpenTag(String s) {
		return "<" + s + ">";
	}
	
	private String basicCloseTag(String s) {
		return "</" + s + ">";
	}
	
	private String openTag(Object obj) {
		return basicOpenTag(obj.getClass().getSimpleName());
	}
	
	private String closeTag(Object obj) {
		return "</" + obj.getClass().getSimpleName() + ">";
	}
	
	private String shortTag(String tagName, String value) {
		return basicOpenTag(tagName)
				+ 	value + "\n" +
				basicCloseTag(tagName);
	}
}
