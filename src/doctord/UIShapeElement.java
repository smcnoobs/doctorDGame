package doctord;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;

public class UIShapeElement extends UIElement {
	private Shape shape;
	private String id;
	
	/*
	 * @param align
	 * negative 	-> right align
	 * positive 	-> left align
	 * zero 		-> centered
	 */
	public UIShapeElement(Shape shape, Color color, int align) { 
		this.shape = shape;
		this.color = color;
		this.align = (align > 0) ? 1 : (align < 0) ? -1 : 0;
	}
	
	public UIShapeElement(Shape shape, Color color, int align, float hscale, float vscale) {
		this(shape,color,align);
		this.setHScale(hscale);
		this.setVScale(vscale);
	}
	
	@Override
	public void render(Graphics g) {
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
		
		if(shape instanceof RoundedRectangle) {
			RoundedRectangle rr = (RoundedRectangle)shape;
			g.fillRoundRect(
					(delta + (shift*rr.getMinX())) * hscale,
					rr.getMinY() * vscale,
					rr.getWidth() * hscale,
					rr.getHeight() * vscale,
					(int) rr.getCornerRadius());
		} else if(shape instanceof Rectangle) {
			Rectangle r = (Rectangle)shape;
			g.fillRect(
					(delta + (shift*r.getMinX())) * hscale,
					r.getMinY() * vscale,
					r.getWidth() * hscale,
					r.getHeight() * vscale);
		}
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public boolean contains(Vector2f location) {
		return shape.contains(location.getX(), location.getY());
	}
	
	@Override
	public void setAlpha(int a) {
		this.color = new Color(color.getRed(),color.getGreen(),color.getBlue(),a);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
