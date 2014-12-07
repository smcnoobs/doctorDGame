package doctordTesting;

import java.io.FileNotFoundException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import levelEditor.*;
import doctord.*;

public class LevelWriterTest {
	public static final String LEVEL_FILE_PATH = 	"./res/levels/level1.xml";
	public static final String SPRITE_PATH = 		"./res/images/dehkhoda_jetpack.png";
	
	/*
	 * precondition: there exists a file at "./res/levels/level1.txt" that can be modified
	 */
	public static boolean constructorWorks() {
		LevelWriter lw;
		try {
			lw = new LevelWriter(LEVEL_FILE_PATH);
			lw.finishWriting();
		} catch (FileNotFoundException e) {
			return false;
		}
		return lw != null;
	}
	
	public static boolean loadAssetsWorks() {
		// Item[] items, Pillar[] pillars, Player player, float gravity, int length
		try {			
			Vector2f location = new Vector2f(0,0);
			Image image = new Image(SPRITE_PATH);
			Animation sprites = new Animation(new Image[] {image}, 1, false);
			
			Item[] items = new Item[] {new Coin(sprites,location)};
			Pillar[] pillars = new Pillar[] {new Pillar(sprites, location)};
			Player player = new Player(sprites, location, 0, (float)0);
			
			LevelWriter lw = new LevelWriter(LEVEL_FILE_PATH);
			lw.loadAssets(items, pillars, player, sprites, (float)0, 0, "LEVEL NAME","");
			lw.finishWriting();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean writeLevelWorks() {
		// Item[] items, Pillar[] pillars, Player player, float gravity, int length
		try {			
			Vector2f location = new Vector2f(0,0);
			Image image = new Image(SPRITE_PATH);
			Animation sprites = new Animation(new Image[] {image}, 1, false);
			
			Item[] items = new Item[] {new Coin(sprites,location)};
			Pillar[] pillars = new Pillar[] {new Pillar(sprites, location)};
			Player player = new Player(sprites, location, 0, (float)0);
			
			LevelWriter lw = new LevelWriter(LEVEL_FILE_PATH);
			lw.loadAssets(items, pillars, player, sprites, (float)0, 0, "LEVEL NAME","");
			lw.writeLevel();
			lw.finishWriting();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean LevelWriterWorks(boolean verbose) {
		boolean[] lw = new boolean[] {
				LevelWriterTest.constructorWorks(),
				LevelWriterTest.loadAssetsWorks(),
				LevelWriterTest.writeLevelWorks(),};
		
		if(verbose) {
			System.out.println("LevelWriter works: constructorWorks: " 	+ lw[0]);
			System.out.println("LevelWriter works: loadAssetsWorks: " 	+ lw[1]);
			System.out.println("LevelWriter works: writeLevelWorks: " 	+ lw[2]);
		}
		
		boolean b = true;
		for(boolean test : lw) {
			b = b && test;
		}
		
		return b;
	}
}
