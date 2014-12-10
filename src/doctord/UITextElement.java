package doctord;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Vector2f;

public class UITextElement extends UIElement {
	private static java.awt.Font UIFont1;
    private static UnicodeFont uniFont;
    private String text;
    private Vector2f location;
	
    public UITextElement(String text, Vector2f location, Color color, int align) {
    	this.text = text;
    	this.location = location;
    	this.align = (align > 0) ? 1 : (align < 0) ? -1 : 0;
    	this.color = color;
    	if(UIFont1 == null && uniFont == null) {
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
    }
    
	@Override
	public void render(Graphics g) {
		g.setFont(uniFont);
		g.setColor(color);
		int delta = 0;
		float shift = 0;
		switch(align) {
		case 1:
			delta = 0;
			shift = 1;
			break;
		case 0:
			delta = 960;
			shift = -0.5f;
			break;
		case -1:
			delta = 1920;
			shift = -1;
			break;
		}
		
		g.drawString(text, delta + ((shift * location.getX()) * hscale), location.getY() * vscale);
	}

	@Override
	public void setAlpha(int a) {
		this.color = new Color(color.getRed(),color.getGreen(),color.getBlue(),a);
	}
}
