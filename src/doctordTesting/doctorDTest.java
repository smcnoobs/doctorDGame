package doctordTesting;

import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import doctord.doctorDGame;

public class doctorDTest extends BasicGame {

	public doctorDTest(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	/*
	 * THIS IS WHERE EVERYTHING HAPPENS!!
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		/*
		 * 	SOMEONE SHOULD SHOOT ME FOR WRITING A TEST LIKE THIS. UGH I LITERALLY CAN'T EVEN.
		 */
		System.out.println("\n\n\nTESTING BEGIN:");
		
		boolean LW_WORKS = LevelWriterTest.LevelWriterWorks(false);
		System.out.println("LevelWriter works: " + (LW_WORKS ? LW_WORKS : LevelWriterTest.LevelWriterWorks(true)));
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		arg0.exit();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *  SUCH BAD DEPENDENCY. THIS IS LITERALLY THE WORST TESTING IVE EVER DONE.
		 */
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new doctorDTest("TESTING. PLEASE CLOSE WHEN FINISHED."));
			appgc.setDisplayMode(400, 1, false);
	         //disabling log here...
			appgc.setVerbose(false);
	        Log.setLogSystem(new NullLogSystem());
	        Log.setVerbose(false);
	        appgc.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
