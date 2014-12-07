package doctord;

import org.newdawn.slick.Graphics;

public abstract class Scene {
	protected ControlHandler controls;
	protected boolean finished;
	
	public Scene() { }
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public boolean isFinished() {
		return finished;
	}
	
	public abstract void silenceMusic();
	
	public abstract void stopMusic();

	public abstract void playMusic();

	public abstract void unSilenceMusic();
	
	public abstract void load(String s);
}
