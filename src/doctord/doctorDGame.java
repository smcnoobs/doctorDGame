package doctord;
import java.util.logging.Level;

import java.util.logging.Logger;

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
	private InputProvider provider;
	private Command debugCommand, exitCommand, fpsCommand, pauseCommand;
	private boolean endGame = false, displayFPS = false;
	private static boolean spaceDown = false, shooting = false;
	private boolean transitioning = false;
	private int shadowTimer = 0;
	private static int currentScene = 0;
	
	// Private Control Functions
	
	private void loadScenes() {
		scenes = new Scene[4];
		scenes[0] = new CinematicScene();
		((CinematicScene)scenes[0]).loadCinematic("./res/cinematics/cinematicTest.txt");
		scenes[1] = new LevelScene();
		((LevelScene)scenes[1]).loadLevel("./res/levels/testMakerLevel.xml");
		scenes[2] = new CinematicScene();
		((CinematicScene)scenes[2]).loadCinematic("./res/cinematics/cinematicTest.txt");
		scenes[3] = new LevelScene();
		((LevelScene)scenes[3]).loadLevel("./res/levels/testMakerLevel.xml");
	}
	
	// Necessary Slick2D Functions
	public doctorDGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		gc.setMouseGrabbed(true);
		
		loadScenes();
		
		provider = new InputProvider(gc.getInput());
		provider.addListener((InputProviderListener) this);
		
		debugCommand = new BasicCommand("Debug Command");
		exitCommand = new BasicCommand("Close the Game.");
		fpsCommand = new BasicCommand("Display FPS.");
		pauseCommand = new BasicCommand("Puase the Level");
		
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), debugCommand);
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), exitCommand);
		provider.bindCommand(new KeyControl(Input.KEY_F1), fpsCommand);
		provider.bindCommand(new KeyControl(Input.KEY_P),pauseCommand);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.setShowFPS(displayFPS);
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
			g.fillRect(0,0,1920,1080);
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
		if(com == pauseCommand && scenes[currentScene] instanceof LevelScene)
			((LevelScene)scenes[currentScene]).pause();
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
			appgc = new AppGameContainer(new doctorDGame("Doctor D"));
			appgc.setDisplayMode(1920, 1080, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(doctorDGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	// Accessors
	
	public static boolean spaceBarIsDown() {
		return spaceDown;
	}
	
	public static boolean shooting() {
		return shooting;
	}
	
	public static Scene getCurrentScene() {
		return scenes[currentScene];
	}
}