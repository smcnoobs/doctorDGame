package config;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import doctord.UIElement;
import doctord.UIElementBuilder;
import doctord.UIShapeElement;

public class SplashGame extends BasicGame {
	private static final float width = 400f, height = 600f;
	private DisplayMode[] displays;
	private boolean fullscreen = true, startMain = false;
	private Image splashImage;
	private Animation splashAnimation;
	private ArrayList<UIElement> mainElements;
	private ArrayList<UIElement> optionElements;
	private Input input;
	private Color color;
	private int clicked = -1;

	public SplashGame(String title) {
		super(title);

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		splashImage.draw(-5,-50,width/splashImage.getWidth());
		for(UIElement e : mainElements) {
			e.render(g);
		}
		splashAnimation.draw(20,220 - (10 * splashAnimation.getFrame()),
				splashAnimation.getCurrentFrame().getWidth() / 1.5f,
				splashAnimation.getCurrentFrame().getHeight() / 1.5f);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		input = gc.getInput();
		UIElementBuilder.loadElements("./res/uielements/splash.xml");
		mainElements = UIElementBuilder.getElements();
		try {
			displays = Display.getAvailableDisplayModes();
			splashImage = new Image("./res/images/splash/s21.png");
			splashAnimation = new Animation(new Image[] {
					new Image("./res/images/splash/drD1.png"),
					new Image("./res/images/splash/drD2.png"),
					new Image("./res/images/splash/drD3.png"),
			}, 150, true);
			splashAnimation.setPingPong(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		Vector2f mouseLoc = new Vector2f(input.getMouseX(),input.getMouseY());
		for(UIElement e : mainElements) {
			if(e instanceof UIShapeElement) {
				if(((UIShapeElement)e).contains(mouseLoc) && ((UIShapeElement)e).getId() != "") {
					if(input.isMouseButtonDown(0) ) {
						color = e.getColor();
						e.setColor(new Color(244,208,63));
						clicked = mainElements.indexOf(e);
						if(((UIShapeElement)e).getId() == "play")
							startMain = true;
					} else if(clicked != -1 && ((UIShapeElement)e).getId() != "") {	// Not currently clicking, but previously had clicked
						UIShapeElement uish = (UIShapeElement)(mainElements.get(clicked));
						uish.setColor(color);
						color = null;
						clicked = -1;
						if(uish.getId() == "play")
							gc.exit();
					} 
					
					e.setAlpha(255);
				} 
				else if(((UIShapeElement)e).getId() != "") { 					// All options the mouse is not currently covering
					if(clicked != -1 && mainElements.get(clicked) == e) {
						e.setColor(color);
						color = null;
						clicked = -1;
						if(((UIShapeElement)e).getId() == "play")
							startMain = false;
					}
						
					e.setAlpha(200);
				}
			}
		}
	}
	
	public static float getWidth() {
		return width;
	}
	
	public static float getHeight() {
		return height;
	}
	
	public boolean startMain() {
		return startMain;
	}
}
