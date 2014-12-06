package doctord;

import org.newdawn.slick.Graphics;

public abstract class Scene {
	protected ControlHandler controls;
	protected boolean finished;
	
	public Scene() {
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void silenceMusic() {
		
	}
	
	public void stopMusic() {
		
	}

	public void playMusic() {
		
	}

	public void unSilenceMusic() {
		
	}
}
