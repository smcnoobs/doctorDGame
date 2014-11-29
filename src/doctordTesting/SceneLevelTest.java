package doctordTesting;

import doctord.*;

public class SceneLevelTest {
	public static final String LEVEL_FILE_PATH = 	"./res/levels/level1.xml";
	public static final String SPRITE_PATH = 		"./res/images/dehkhoda_jetpack.png";
	
	public static boolean constructorWorks() {
		LevelScene sl = new LevelScene();
		return sl != null;
	}
	
	public static boolean loadLevelWorks() {
		LevelScene sl = new LevelScene();
		return sl.loadLevel(LEVEL_FILE_PATH);
//		return true;
	}
	
	public static boolean SceneLevelWorks(boolean verbose) {
		boolean[] sl = new boolean[] {
				SceneLevelTest.constructorWorks(),
				SceneLevelTest.loadLevelWorks(),};
		
		if(verbose) {
			System.out.println("SceneLevel works: constructorWorks: " 	+ sl[0]);
			System.out.println("SceneLevel works: loadLevelWorks(): " 	+ sl[1]);
		}
		
		boolean b = true;
		for(boolean test : sl) {
			b = b && test;
		}
		
		return b;
	}
}
