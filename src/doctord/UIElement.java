package doctord;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class UIElement {
	protected float vscale = 1.0f, hscale = 1.0f;
	public abstract void render(Graphics g);
	protected int align = 0;
	protected Color color;
	
	public void setHScale(float hscale) {
		this.hscale = hscale;
	}
	
	public void setVScale(float vscale) {
		this.vscale = vscale;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public abstract void setAlpha(int a);

	public Color getColor() {
		return color;
	}
}
