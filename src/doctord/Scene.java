package doctord;

import org.newdawn.slick.Graphics;

public class Scene {
	protected ControlHandler controls;
	private boolean finished;
	
	public Scene(ControlHandler controls) {
		this.controls = controls;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	public boolean isFinished() {
		return finished;
	}
}
