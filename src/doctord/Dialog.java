package doctord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dialog {
	private Scanner sc;
	private Animation sprite;
	private String text = "";
	private String exposedText;
	private int i = 0;
	private boolean finishedWriting = false;
	
	public Dialog(String filename) {
		try {
			ArrayList<Image> images = new ArrayList<Image>();
			ArrayList<String> texts = new ArrayList<String>();
			sc = new Scanner(new File(filename));
			while(sc.hasNextLine()) {
				String temp = sc.nextLine();
				if(temp.indexOf("IMAGE:") == 0)
					images.add(new Image(temp.substring(6)));	// Load Image
				if(temp.indexOf("TEXT:") == 0)
					texts.add(temp.substring(5));				// Load Text
			}
			for(String s : texts)
				text += s + "\n";
			exposedText = "";
			Image[] arrImages =  images.toArray(new Image[images.size()]);
			sprite = new Animation(arrImages,100,true);
			sprite.setPingPong(false);
			sprite.setLooping(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public String getText() {
		return exposedText;
	}
	
	public Animation getAnimation() {
		return sprite;
	}
	
	public boolean finishedWriting() {
		return finishedWriting;
	}
	
	public void writeAll() {
		i = text.length();
		exposedText = text;
		finishedWriting = true;
	}
	
	public void update() {
		if(i < text.length()) {
			exposedText = text.substring(0,i++);
		} else  {
			finishedWriting = true;
		}
	}
	
	public boolean hasText() {
		return text.length() != 0;
	}
}
