package doctord;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.*;

public class StatDisplay {
	private float fuel=0;
	private int health = 0;
	private final boolean debug = false;
	private final String pauseMsg = "GAME PAUSED";
	private final String playerMsg = "YOU DIED - PRESS R TO RESTART";
	private Vector2f debugLocation = new Vector2f(500,500);
	private java.awt.Font UIFont1;
    private UnicodeFont uniFont;
	
	public static final Color darkGrey = new Color(40, 40 , 40, 200),
			darkerGrey = new Color(0, 0, 0, 100),
			fuelGreen = new Color(46,204,113),
			darkGreen = new Color(30,130,63),
			healthRed = new Color(239,72,54),
			darkRed   = new Color(150,40,27),
			dullYellow = new Color(244,208,63),
			transparentBlack = new Color(0,0,0,200);
	
	private UIShapeElement[] elements = new UIShapeElement[] {
			new UIShapeElement(new Rectangle(0,0,1920,54), darkGrey, 1),				// Top Bar 				darkGrey		0
			new UIShapeElement(new Rectangle(0,1080-54,1920,54), darkGrey, 1),		// Bottom Bar			darkGrey		1
			new UIShapeElement(new Rectangle(5,5,210,40), darkerGrey, 1),			// Fuel Background		darkerGrey		2
			new UIShapeElement(new Rectangle(10,10,200,30), darkGreen, 1),			// Fuel Trim			darkGreen		3
			new UIShapeElement(new Rectangle(10,10,200,30), fuelGreen, 1),			// Player Fuel			fuelGreen		4 *
			
			new UIShapeElement(new Rectangle(215,5,210,40), darkerGrey, -1),			// Health Background	darkerGrey		5
			new UIShapeElement(new Rectangle(210,10,200,30), darkRed, -1),			// Health Trim			darkRed			6
			new UIShapeElement(new Rectangle(210,10,200,30), healthRed, -1),			// Player Health		healthRed		7 *
			new UIShapeElement(new Rectangle(420,5,200,40), darkerGrey, -1),			// Collected Coins		darkerGrey		8
	};
	
	private UIShapeElement[] pausedElements = new UIShapeElement[] {
			new UIShapeElement(new RoundedRectangle(500,540 - 30,500,40,10), transparentBlack, 0),
	};
	
	@SuppressWarnings("unchecked")
	public StatDisplay() {
		try {
			UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
			            org.newdawn.slick.util.ResourceLoader.getResourceAsStream("./res/fonts/manaspc.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 	UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 24.f * doctorDGame.getVScale());
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
	
	public void update() {
		fuel = Player.getFuel();
		health = Player.getHealth();
			
		((Rectangle) elements[4].getShape()).setWidth(fuel);			// Fuel Width
		((Rectangle) elements[7].getShape()).setWidth(health * 40);		// Health Width
	}
	
	public void render(Graphics g) {
		float vscale = doctorDGame.getVScale(), hscale = doctorDGame.getHScale();
		float width = doctorDGame.getDisplay().getWidth();
		g.setFont(uniFont);
		
		for(UIShapeElement uiel : elements) {
			uiel.setHScale(doctorDGame.getHScale());
			uiel.setVScale(doctorDGame.getVScale());
			uiel.render(g);
		}
		
		g.setColor(darkGrey);
		g.drawString("FUEL",15 * hscale,15 * vscale);
		g.drawString("HEALTH",width - (205* hscale),15 * vscale);
		
		g.setColor(dullYellow);
		g.drawString("COINS: " + Coin.getCollected(), width - (410 * hscale), 15 * vscale);
		
		
		g.setColor(Color.white);
		g.drawString(LevelScene.getLevelName(),((width/2) - (uniFont.getWidth(LevelScene.getLevelName())/2)),(1080-54+15) * vscale);
		
		if(debug) {
			g.setColor(darkGrey);
			g.fillRect(debugLocation.getX() - 20,debugLocation.getY() - 20,330,175);
			g.setColor(Color.white);
			g.drawString("Fuel: " + Player.getFuel(), debugLocation.getX(),debugLocation.getY());
			g.drawString("Health: " + Player.getHealth(), debugLocation.getX(),debugLocation.getY() + 30);
			if(Player.getCurrentEffect() == PlayerEffect.NEUTRAL)
				g.drawString("CURRENTLY NEUTRAL", debugLocation.getX(),debugLocation.getY() + 60);
			if(Player.getCurrentEffect() == PlayerEffect.SHIELDED)
				g.drawString("CURRENTLY SHIELDED", debugLocation.getX(),debugLocation.getY() + 60);
			if(Player.getCurrentEffect() == PlayerEffect.AUTOPILOT)
				g.drawString("CURRENTLY AUTOPILOT", debugLocation.getX(),debugLocation.getY() + 60);
			g.drawString("Coins Collected: " + Coin.getCollected(),debugLocation.getX(),debugLocation.getY() + 90);
			g.drawString("MUTED: " + doctorDGame.isMuted(), debugLocation.getX(),debugLocation.getY() + 120);
		}
	}
	
	public void renderPauseScreen(Graphics g) {
		g.setFont(uniFont);
		float scale = doctorDGame.getVScale(),hscale = doctorDGame.getHScale();
//		for(int i = 0; i < pausedShapes.length; i++) {
//			g.setColor(pausedColors[i]);
//			if(pausedShapes[i] instanceof RoundedRectangle) {
//				RoundedRectangle r = (RoundedRectangle)pausedShapes[i];
//				g.fillRoundRect(r.getMinX() * hscale,
//						r.getMinY() * scale,
//						r.getWidth() * hscale,
//						r.getHeight() * scale,(int) r.getCornerRadius());
//			} else if(pausedShapes[i] instanceof Rectangle) {	
//				Rectangle r = (Rectangle)shapes[i];
//				g.fillRect(r.getMinX() * hscale,
//						r.getMinY() * scale,
//						r.getWidth() * hscale,
//						r.getHeight() * scale);
//			} else {
//				fillShape(g,shapes[i]);
//			}
//		}
		
		if(Player.getHealth() > 0) {
			g.setColor(Color.white);
			g.drawString(pauseMsg, (960 * hscale) - (uniFont.getWidth(pauseMsg) / 2), (540 * scale) - uniFont.getHeight(pauseMsg));
		} else {
			g.setColor(Color.red);
			g.drawString(playerMsg, (960 * hscale) - (uniFont.getWidth(playerMsg) / 2), (540 * scale) - uniFont.getHeight(playerMsg));
		}
	}
	
	private void fillShape(Graphics g, Shape s) {
		g.translate(s.getLocation().getX() * doctorDGame.getVScale(),s.getLocation().getY() * doctorDGame.getVScale());
		g.fill(s);
		g.translate(-(s.getLocation().getX() * doctorDGame.getVScale()),-(s.getLocation().getY() * doctorDGame.getVScale()));
	}
}
