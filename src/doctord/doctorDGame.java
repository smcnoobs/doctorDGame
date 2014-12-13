package doctord;
import java.util.logging.Level;
import java.util.logging.Logger;

import levelEditor.GameLevelMaker;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
//import org.lwjgl.LWJGLException;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

public class doctorDGame extends BasicGame implements InputProviderListener {
	private static Scene[] scenes;
	private static String[] scenePaths;
	private InputProvider provider;
	private Command debugCommand, exitCommand, fpsCommand, pauseCommand, muteCommand, restartCommand;
	private boolean endGame = false, displayFPS = false;
	private static boolean spaceDown = false, shooting = false, muted = false;
	private boolean transitioning = false;
	private int shadowTimer = 0;
	private static int currentScene = 0;
	private static float vscale = 1.0f, hscale = 1.0f;
	private static DisplayMode display = new DisplayMode(0,0);
	
	// Private Control Functions
	
	private static DisplayMode getdisplay(float width, float height) throws LWJGLException {
		DisplayMode[] modes = Display.getAvailableDisplayModes();
		DisplayMode display = new DisplayMode(0,0);
		for (int i=0;i<modes.length;i++) {
			DisplayMode current = modes[i];
			if(current.getBitsPerPixel() == 32 && Math.abs(((float)current.getWidth()/(float)current.getHeight()-(width/height))) <= 0.1) {
				if(display.getWidth() < current.getWidth())
					display = current;
			}
		}
		return display;
	}
	
	private void loadScenes() {
		scenes = new Scene[10];  
		scenes = new Scene[] {
				new LevelScene(),			//	Easy
				new CinematicScene(),		//	Intro
				new CinematicScene(),		//	Opening
				new CinematicScene(),		//	Title
				new LevelScene(),			// 	Volcano
		};
		scenePaths = new String[] {
				"./res/levels/easy.xml",
				"./res/cinematics/intro.txt",
				"./res/cinematics/opening.txt",
				"./res/cinematics/title.txt",
				"./res/levels/testMakerLevel.xml",
		};
		
		// Load the first Scene
		scenes[currentScene].load(scenePaths[currentScene]);
	}
	
	// Necessary Slick2D Functions
	public doctorDGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setVSync(true);
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		gc.setMouseGrabbed(true);
		
		loadScenes();
		
		provider = new InputProvider(gc.getInput());
		provider.addListener((InputProviderListener) this);
		
		debugCommand = new BasicCommand("Debug Command");
		exitCommand = new BasicCommand("Close the Game.");
		fpsCommand = new BasicCommand("Display FPS.");
		pauseCommand = new BasicCommand("Pause the Level");
		muteCommand = new BasicCommand("Mute All Sounds");
		restartCommand = new BasicCommand("Restart The Level");
		
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), debugCommand);
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), exitCommand);
		provider.bindCommand(new KeyControl(Input.KEY_F1), fpsCommand);
		provider.bindCommand(new KeyControl(Input.KEY_P),pauseCommand);
		provider.bindCommand(new KeyControl(Input.KEY_M), muteCommand);
		provider.bindCommand(new KeyControl(Input.KEY_R), restartCommand);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.setShowFPS(displayFPS);
		if(muted)
			scenes[currentScene].silenceMusic();
		else
			scenes[currentScene].unSilenceMusic();
		if(endGame)
			gc.exit();
		
		if(scenes[currentScene].isFinished()) {
			if(!transitioning) {
				shadowTimer = 0;
				transitioning = true;
			} else {
				scenes[currentScene].silenceMusic();
				shadowTimer= shadowTimer + 5;
				if(shadowTimer >= 255) {
					transitioning = false;
					scenes[currentScene].stopMusic();
					currentScene++;
					scenes[currentScene].load(scenePaths[currentScene]);
				}
			}
		}
		scenes[currentScene].update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		scenes[currentScene].render(g);
		if(transitioning) {
			g.setColor(new Color(0,0,0,shadowTimer));
			g.fillRect(0,0,1920 * vscale,1080 * vscale);
		}
	}
	
	@Override
	public void controlPressed(Command com) {
		if(com == debugCommand)
			spaceDown = true;
		if(com == exitCommand)
			endGame = true;
		if(com == fpsCommand)
			displayFPS = (displayFPS) ? false : true;
		if(com == pauseCommand && scenes[currentScene] instanceof LevelScene && Player.getHealth() > 0)
			((LevelScene)scenes[currentScene]).pause();
		if(com == muteCommand   )
			muted = (muted) ? false: true;
		if(com == restartCommand && scenes[currentScene] instanceof LevelScene) {
			if(Player.getHealth() <= 0) {
				scenes[currentScene] = new LevelScene();
				((LevelScene)scenes[currentScene]).loadLevel(scenePaths[currentScene]);
				((LevelScene)scenes[currentScene]).pause();
			}
		}
	}

	@Override
	public void controlReleased(Command com) {
		if(com == debugCommand)
			spaceDown = false;
	}
	
	// Main Loop

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			config.SplashGame splash = new config.SplashGame("Splash Game");
			appgc = new AppGameContainer(splash);
			appgc.setDisplayMode((int)config.SplashGame.getWidth(),(int)config.SplashGame.getHeight(),false);
			appgc.setForceExit(false);
			appgc.start();
			
<<<<<<< HEAD
=======
			splash.writeConfig();
			
>>>>>>> origin/master
			if(splash.startMain()) {
				appgc = new AppGameContainer(new doctorDGame("Doctor D"));
				if(display.getWidth() == 0 && display.getHeight() == 0)
					display = getdisplay(16,9);
				if(display.getWidth() == 0 && display.getHeight() == 0)
					display = getdisplay(16,10);
				if(display.getWidth() == 0 && display.getHeight() == 0)
					display = getdisplay(4,3);
				if(display.getWidth() == 0 && display.getHeight() == 0) {
					display = new DisplayMode(640,360);
					vscale = (float)display.getHeight()/1080;
					hscale = (float)display.getWidth()/1920;
					appgc.setDisplayMode(display.getWidth(),display.getHeight(),false);
				} else {
					vscale = ((float)display.getHeight())/1080;
					hscale = ((float)display.getWidth())/1920;
					appgc.setDisplayMode(display.getWidth(), display.getHeight(), true);
				}
				appgc.start();
<<<<<<< HEAD
=======
			} else if(splash.startEditor()) {
				appgc = new AppGameContainer(new GameLevelMaker("Level Editor"));
				appgc.setDisplayMode(1366, 768, false);
				appgc.start();
>>>>>>> origin/master
			}
		} catch (SlickException ex) {
			ex.printStackTrace();
			Logger.getLogger(doctorDGame.class.getName()).log(Level.SEVERE, null, ex);
		} 
		catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	// Accessors
	
	public static boolean spaceBarIsDown() {
		return spaceDown;
	}
	
	public static boolean shooting() {
		return shooting;
	}
	
	public static boolean isMuted() {
		return muted;
	}
	
	public static Scene getCurrentScene() {
		return scenes[currentScene];
	}
	
	public static float getVScale() {
		return vscale;
	}
	
	public static float getHScale() {
		return hscale;
	}
	
	public static DisplayMode getDisplay() {
		return display;
	}
}