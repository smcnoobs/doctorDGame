package config;

<<<<<<< HEAD
=======
import java.io.FileNotFoundException;
>>>>>>> origin/master
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
<<<<<<< HEAD
	private boolean fullscreen = true, startMain = false;
	private Image splashImage;
	private Animation splashAnimation;
	private ArrayList<UIElement> mainElements;
	private ArrayList<UIElement> optionElements;
	private Input input;
	private Color color;
	private int clicked = -1;
=======
	private boolean startMain = false, startEditor = false, fs = false, mt = false;
	private Image splashImage;
	private Animation splashAnimation, gameScreens;
	private ArrayList<UIElement> mainElements, optionElements, creditElements, uiElements;
	private Input input;
	private GameConfig config;
	private int dispIndex = 0;
>>>>>>> origin/master

	public SplashGame(String title) {
		super(title);

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
<<<<<<< HEAD
		splashImage.draw(-5,-50,width/splashImage.getWidth());
		for(UIElement e : mainElements) {
			e.render(g);
		}
		splashAnimation.draw(20,220 - (10 * splashAnimation.getFrame()),
				splashAnimation.getCurrentFrame().getWidth() / 1.5f,
				splashAnimation.getCurrentFrame().getHeight() / 1.5f);
=======
		if(uiElements == mainElements) {
			splashImage.draw(-5,-50,width/splashImage.getWidth());
		}
		for(UIElement e : uiElements) {
			e.render(g);
		}
		if(uiElements == mainElements) {
			gameScreens.draw(0, 150, 400, 200, new Color(200,200,200,200));
			splashAnimation.draw(20,220 - (10 * splashAnimation.getFrame()),
				splashAnimation.getCurrentFrame().getWidth() / 1.5f,
				splashAnimation.getCurrentFrame().getHeight() / 1.5f);
		}
		
		if(uiElements == optionElements) {
			g.setColor(Color.white);
			g.drawString(displays[dispIndex].toString(),15,350);
		}
>>>>>>> origin/master
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		input = gc.getInput();
		UIElementBuilder.loadElements("./res/uielements/splash.xml");
		mainElements = UIElementBuilder.getElements();
<<<<<<< HEAD
		try {
			displays = Display.getAvailableDisplayModes();
=======
		UIElementBuilder.loadElements("./res/uielements/options.xml");
		optionElements = UIElementBuilder.getElements();
		UIElementBuilder.loadElements("./res/uielements/credits.xml");
		creditElements = UIElementBuilder.getElements();
		
		uiElements = mainElements;
		
		try {
			displays = Display.getAvailableDisplayModes();
			config = new GameConfig("./res/config.txt");
			fs = config.isFullScreen();
			mt = config.isMuted();
			
			for(int i = 0; i < displays.length; i++) {
				if(config.getDisplay().getWidth() == displays[i].getWidth()
						&& config.getDisplay().getHeight() == displays[i].getHeight())
					dispIndex = i;
			}
>>>>>>> origin/master
			splashImage = new Image("./res/images/splash/s21.png");
			splashAnimation = new Animation(new Image[] {
					new Image("./res/images/splash/drD1.png"),
					new Image("./res/images/splash/drD2.png"),
					new Image("./res/images/splash/drD3.png"),
			}, 150, true);
<<<<<<< HEAD
			splashAnimation.setPingPong(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
=======
			gameScreens = new Animation(new Image[] {
					new Image("./res/images/splash/jupiterbg.jpg"),
					new Image("./res/images/splash/Callisto.png"),
					new Image("./res/images/splash/Europa.png"),
					new Image("./res/images/splash/Ganymede.png"),
//					new Image("./res/images/splash/Volcano.png"),
			}, 3600, true);
			splashAnimation.setPingPong(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private DisplayMode getdisplay(float width, float height) throws LWJGLException {
		DisplayMode[] modes = Display.getAvailableDisplayModes();
		DisplayMode display = new DisplayMode(0,0);
		for (int i=0;i<modes.length;i++) {
			DisplayMode current = modes[i];
			if(current.getBitsPerPixel() == 32 && Math.abs(((float)current.getWidth()/(float)current.getHeight()-(width/height))) <= 0.1) {
				if(display.getWidth() < current.getWidth()) {
					dispIndex = i;
					display = current;
				}
			}
		}
		return display;
	}
>>>>>>> origin/master

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		Vector2f mouseLoc = new Vector2f(input.getMouseX(),input.getMouseY());
<<<<<<< HEAD
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
=======
		for(UIElement e : uiElements) {
			if(e instanceof UIShapeElement) {
				UIShapeElement uish = (UIShapeElement)e;
				if(uish.getId().equals("fullscreen") && config.isFullScreen()) 
					uish.setColor(new Color(217,30,24));
				else if(uish.getId().equals("fullscreen"))
					uish.setColor(new Color(218,223,225));
				if(uish.getId().equals("muted") && config.isMuted()) 
					uish.setColor(new Color(217,30,24));
				else if(uish.getId().equals("muted"))
					uish.setColor(new Color(218,223,225));
				if(uish.contains(mouseLoc) && uish.getId() != "") {
					if(input.isMouseButtonDown(0))
						uish.setHighlighted(true);
					else {
						if(uish.isHighlighted()) {
							switch(uish.getId()) {
							case "play":
								startMain = true;
								gc.exit();
								break;
							case "editor":
								startEditor = true;
								gc.exit();
							case "options":
								uiElements = optionElements;
								break;
							case "home":
								uiElements = mainElements;
								break;
							case "credits":
								uiElements = creditElements;
								break;
							case "resolution":
								dispIndex = (dispIndex + 1) % displays.length;
								config.setDisplay(displays[dispIndex]);
								break;
							case "fullscreen":
								fs = fs ? false : true;
								config.setFullScreen(fs);
								if(fs)
									uish.setColor(new Color(217,30,24));
								else
									uish.setColor(new Color(218,223,225));
								break;
							case "muted":
								mt = mt ? false : true;
								config.setMuted(mt);
								if(mt) 
									uish.setColor(new Color(217,30,24));
								else 
									uish.setColor(new Color(218,223,225));
								break;
							case "defaults":
								config = new GameConfig();
								for(int j = 0; j < displays.length; j++) {
									if(config.getDisplay().getWidth() == displays[j].getWidth()
											&& config.getDisplay().getHeight() == displays[j].getHeight())
										dispIndex = j;
								}
								fs = config.isFullScreen();
								mt = config.isMuted();
								break;
							}
						}
						uish.setHighlighted(false);
					}
						
					e.setAlpha(255);
				} 
				else if(uish.getId() != "") { 					// All options the mouse is not currently covering
					uish.setAlpha(200);
					uish.setHighlighted(false);
>>>>>>> origin/master
				}
			}
		}
	}
	
<<<<<<< HEAD
=======
	public void writeConfig() {
		try {
			config.printConfig("./res/config.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
>>>>>>> origin/master
	public static float getWidth() {
		return width;
	}
	
	public static float getHeight() {
		return height;
	}
	
	public boolean startMain() {
		return startMain;
	}
<<<<<<< HEAD
=======

	public boolean startEditor() {
		return startEditor;
	}
>>>>>>> origin/master
}
