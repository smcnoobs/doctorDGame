package doctord;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
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
	private Scene[] scenes;
	private InputProvider provider;
	private ControlHandler controls;
	private Command debugCommand, exitCommand, fpsCommand;
	private boolean endGame = false, displayFPS = false;
	
	// Private Control Functions
	
	private void loadScenes() {
		scenes = new Scene[1];
		scenes[0] = new SceneLevel(controls);
		((SceneLevel)scenes[0]).loadDebugLevel();
	}
	
	private static DisplayMode getMaxDisplay(float width, float height) throws LWJGLException {
		DisplayMode[] modes = Display.getAvailableDisplayModes();
		DisplayMode maxDisplay = new DisplayMode(0,0);
		for (int i=0;i<modes.length;i++) {
			DisplayMode current = modes[i];
			if(current.getBitsPerPixel() == 32 && Math.abs(((float)current.getWidth()/(float)current.getHeight()-(width/height))) <= 0.1) {
				if(maxDisplay.getWidth() < current.getWidth()) 
					maxDisplay = current;
			}
		}
		return maxDisplay;
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
		
		controls = new ControlHandler();
		loadScenes();
		
		provider = new InputProvider(gc.getInput());
		provider.addListener((InputProviderListener) this);
		
		debugCommand = new BasicCommand("Debug Command");
		exitCommand = new BasicCommand("Close the Game.");
		fpsCommand = new BasicCommand("Display FPS.");
		
		
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), debugCommand);
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), exitCommand);
		provider.bindCommand(new KeyControl(Input.KEY_F1), fpsCommand);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.setShowFPS(displayFPS);
		if(endGame)
			gc.exit();
		
		for(Scene s : scenes) 
			s.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Howdy!", 50, 50);
		for(Scene s : scenes)
			s.render(g);
	}
	
	@Override
	public void controlPressed(Command com) {
		if(com == debugCommand)
			controls.respondPressed(com);
		if(com == exitCommand)
			endGame = true;
		if(com == fpsCommand)
			displayFPS = (displayFPS) ? false : true;
	}

	@Override
	public void controlReleased(Command com) {
		if(com == debugCommand)
			controls.respondReleased(com);
	}
	
	// Main Loop

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new doctorDGame("Doctor D"));
			DisplayMode maxDisplay = getMaxDisplay(16,9);
			appgc.setDisplayMode(maxDisplay.getWidth(), maxDisplay.getHeight(), true);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(doctorDGame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (LWJGLException ex) {
			Logger.getLogger(doctorDGame.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}