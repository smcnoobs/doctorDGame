package doctord;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImagePool {
	private static ImagePool single;
	private static TreeMap<String, Image> images = new TreeMap<String,Image>();
	
	private ImagePool() {}
	
	public static ImagePool getInstance() {
		if(single == null)
			single = new ImagePool();
		
		return single;
	}
	
	private static Image addImage(String s) {
		try {
			Image i = new Image(s);
			images.put(s, i);
			return i;
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image getImage(String s) {
		if(images.containsKey(s)) {
			return images.get(s);
		} else {
			return addImage(s);
		}
	}
	
	public static void clear() {
		images.clear();
	}
}
