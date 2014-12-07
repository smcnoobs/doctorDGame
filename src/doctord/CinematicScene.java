package doctord;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class CinematicScene extends Scene {
	private ArrayList<Dialog> dialogs;
	private int currentDialog = 0, debounce = 0;
	private java.awt.Font UIFont1;
    private UnicodeFont uniFont;
    private Music music;
	
	@SuppressWarnings("unchecked")
	public CinematicScene() {
		super();
		dialogs = new ArrayList<Dialog>();
		
		try {
			UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
			            org.newdawn.slick.util.ResourceLoader.getResourceAsStream("./res/fonts/manaspc.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 	UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 24.f);
	        uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
	        uniFont.addAsciiGlyphs();
	        ColorEffect a = new ColorEffect();
	        a.setColor(new java.awt.Color(255,255,255));

	        uniFont.getEffects().add(a);
	        try {
				uniFont.loadGlyphs();
			} catch (SlickException e) {
				e.printStackTrace();
			}
	}
	
	public void load(String s) {
		loadCinematic(s);
	}
	
	public void loadCinematic(String filename) {
		try {
			Scanner sc = new Scanner(new File(filename));
			while(sc.hasNextLine()) {
				String temp = sc.nextLine();
				if(temp.indexOf("MUSIC:") == 0)
					music = new Music(temp.substring(6));
				else
					dialogs.add(new Dialog(temp));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		if(music != null && !doctorDGame.isMuted() && !music.playing())
			music.play();
		if(debounce == 0 && doctorDGame.spaceBarIsDown()) {
			if(dialogs.get(currentDialog).finishedWriting()) {
				if(currentDialog < dialogs.size() - 1) {
					currentDialog++;
					debounce = 30;
				} else
					finished = true;
			} else {
				dialogs.get(currentDialog).writeAll();
				debounce = 30; 
			}
		} else if(debounce > 0)
			debounce--;
		dialogs.get(currentDialog).update();
	}
	
	@Override
	public void render(Graphics g) {
		g.setFont(uniFont);
		Animation a = dialogs.get(currentDialog).getAnimation();
		a.draw(960 - (a.getCurrentFrame().getWidth() / 2),0);
		
		if(dialogs.get(currentDialog).hasText()) {
			g.setColor(Color.black);
			g.fillRect(0,720,1920,360);
			
			g.setColor(Color.white);
			g.drawString(dialogs.get(currentDialog).getText(),15,735);
		}
	}
	
	@Override
	public void playMusic() {
		if(music != null && music.playing())
			music.play();
	}
	
	@Override
	public void silenceMusic() {
		if(music != null)
			music.setVolume(music.getVolume() - 0.05f);
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
}
