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
//	private Vector2f location;
	private float fuel=0;
	private int health = 0;
	private final boolean debug = false;
	java.awt.Font UIFont1;
    UnicodeFont uniFont;
	
	public static final Color darkGrey = new Color(40, 40 , 40, 200),
			darkerGrey = new Color(0, 0, 0, 100),
			fuelGreen = new Color(46,204,113),
			darkGreen = new Color(30,130,63),
			healthRed = new Color(239,72,54),
			darkRed   = new Color(150,40,27),
			dullYellow = new Color(244,208,63);
			
	private Shape[] shapes = new Shape[] {
			new Rectangle(0,0,1920,54),			// Top Bar 				darkGrey		0
			new Rectangle(0,1080-54,1920,54),	// Bottom Bar			darkGrey		1
			
			new Rectangle(5,5,210,40),			// Fuel Background		darkerGrey		2
			new Rectangle(10,10,200,30),		// Fuel Trim			darkGreen		3
			new Rectangle(10,10,200,30),		// Player Fuel			fuelGreen		4 *
			
			new Rectangle(1705,5,210,40),		// Health Background	darkerGrey		5
			new Rectangle(1710,10,200,30),		// Health Trim			darkRed			6
			new Rectangle(1710,10,200,30),		// Player Health		healthRed		7 *
			
			new Rectangle(1500,5,200,40),		// Collected Coins		darkerGrey		8
	};
	
	private Color[] colors = new Color[] {
			darkGrey,		// Top Bar
			darkGrey,		// Bottom Bar
			darkerGrey,		// Fuel Background
			darkGreen,		// Fuel Trim
			fuelGreen,		// Player Fuel
			darkerGrey,		// Health Background
			darkRed,		// Health Trim
			healthRed,		// Player Health
			darkerGrey,		// Collected Coins
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
	
//	private void setFontSize(float fontSize) {
//	 	UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, fontSize);
//        uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
//        uniFont.addAsciiGlyphs();
//        ColorEffect a = new ColorEffect();
//        a.setColor(new java.awt.Color(255,255,255));
//
//        uniFont.getEffects().add(a);
//        try {
//			uniFont.loadGlyphs();
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void update() {
		fuel = Player.getFuel();
		health = Player.getHealth();
		
		((Rectangle) shapes[4]).setWidth(fuel);				// Fuel Width
		((Rectangle) shapes[7]).setWidth(health * 40);		// Health Width
	}
	
	public void render(Graphics g) {
		g.setFont(uniFont);
		for(int i = 0; i < shapes.length; i++) {
			g.setColor(colors[i]);
			if(shapes[i] instanceof Rectangle) {
				Rectangle r = (Rectangle)shapes[i];
				g.fillRect(r.getMinX(),r.getMinY(),r.getWidth(),r.getHeight());
			} else {
				fillShape(g,shapes[i]);
			}
		}
		
		g.setColor(darkGrey);
		g.drawString("FUEL",15,15);
		g.drawString("HEALTH",1715,15);
		
		g.setColor(dullYellow);
		g.drawString("COINS: " + Coin.getCollected(), 1510, 15);
		
		
		g.setColor(Color.white);
		g.drawString(LevelScene.getLevelName(),960 - (uniFont.getWidth(LevelScene.getLevelName())/2),1080-54+15);
		
		if(debug) {
			g.setColor(darkGrey);
			g.fillRect(1580,800,330,175);
			g.setColor(Color.white);
			g.drawString("Fuel: " + Player.getFuel(), 1600,820);
			g.drawString("Health: " + Player.getHealth(), 1600,850);
			if(Player.getCurrentEffect() == PlayerEffect.NEUTRAL)
				g.drawString("CURRENTLY NEUTRAL", 1600,880);
			if(Player.getCurrentEffect() == PlayerEffect.SHIELDED)
				g.drawString("CURRENTLY SHIELDED", 1600,880);
			if(Player.getCurrentEffect() == PlayerEffect.AUTOPILOT)
				g.drawString("CURRENTLY AUTOPILOT", 1600,880);
			g.drawString("Coins Collected: " + Coin.getCollected(),1600,910);
		}
	}
	
	private void fillShape(Graphics g, Shape s) {
		g.translate(s.getLocation().getX(),s.getLocation().getY());
		g.fill(s);
		g.translate(-(s.getLocation().getX()),-(s.getLocation().getY()));
	}
}
