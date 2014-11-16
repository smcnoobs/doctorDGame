package levelEditor;

import java.awt.Frame;

import javax.swing.JOptionPane;

import doctord.Pillar;

public class LevelMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pillar[] pillars = new Pillar[5];
		try {
			String level_path = (String)JOptionPane.showInputDialog(
	                new Frame(),
	                "Please Enter the location of the Level File to be written to.",
	                "Level Maker - Level Filepath",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                "./res/levels/");
			System.out.println("Here we are");
			LevelWriter writer = new LevelWriter(level_path);
			PillarMaker p = new PillarMaker("Making Pillars",20);
			p.run();
			boolean b = true;
			while(b) {
				if(p.isFinished()) {
					pillars = p.getPillars();
					b = false;
				}
			}
			
			System.out.println("Finished making pillars");
			
			writer.loadAssets(null,pillars,null,0,0);
			writer.writeLevel();
			writer.finishWriting();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
