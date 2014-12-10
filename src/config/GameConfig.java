package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.lwjgl.opengl.DisplayMode;

public class GameConfig {
	private boolean fullscreen, muted;
	private int width = 0, height = 0;
	private DisplayMode display;
	
	public GameConfig() {
		fullscreen = true;
		muted = false;
		display = new DisplayMode(1920,1080);
	}
	
	public GameConfig(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			if(s.indexOf("FULLSCREEN:") != -1) {
				s = s.substring(11);
				if(s.indexOf("true") != -1)
					fullscreen = true;
				else
					fullscreen = false;
			}
			if(s.indexOf("MUTED:") != -1) {
				s = s.substring(6);
				if(s.indexOf("true") != -1)
					muted = true;
				else
					muted = false;
			}
			if(s.indexOf("WIDTH:") != -1) {
				s = s.substring(6);
				width = Integer.parseInt(s);
			}
			if(s.indexOf("HEIGHT:") != -1) {
				s = s.substring(7);
				height = Integer.parseInt(s);
			}
		}
		sc.close();
		if(width != 0 && height != 0)
			display = new DisplayMode(width, height);
	}
	
	public void printConfig(String filename) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(filename));
		pw.println("FULLSCREEN:" + fullscreen);
		pw.println("MUTED:" + muted);
		pw.println("WIDTH:" + display.getWidth());
		pw.println("HEIGHT:" + display.getHeight());
		pw.close();
	}
	
	public void setFullScreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public boolean isFullScreen() {
		return fullscreen;
	}
	
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	
	public boolean isMuted() {
		return muted;
	}
	
	public void setDisplay(int x, int y) {
		display = new DisplayMode(x,y);
	}
	
	public void setDisplay(DisplayMode display) {
		this.display = display;
	}
	
	public DisplayMode getDisplay() {
		return display;
	}
}