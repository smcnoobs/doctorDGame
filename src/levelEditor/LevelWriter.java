package levelEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import doctord.Item;
import doctord.Player;
//import doctord.Pillar;

public class LevelWriter {
	PrintWriter pw;
	
	public LevelWriter(String filename) throws FileNotFoundException {
		pw = new PrintWriter(new File(filename));
	}
	
	public void finishWriting() {
		pw.close();
	}
	
	private void writeItem(Item a) {
		
	}
	
	public void writePlayer(Player p) {
		
	}
}
