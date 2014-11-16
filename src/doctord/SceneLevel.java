package doctord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SceneLevel extends doctord.Scene {
	// Actables
	private Player player;
	private ArrayList<Pillar> pillars;
	private ArrayList<Item> items;
	
	// Level Constants
	private float gravity = (float)9.8;
	private int length;
	private Animation background;
	private boolean level_did_load;
	
	// For Testing
	float x = 0;
	
	private void collideActables() {
		for(Pillar p : pillars)
			player.collide(p);
		for(Item i : items)
			player.collide(i);
	}
	
	public SceneLevel(ControlHandler controls) {
		super(controls);
		
		pillars = new ArrayList<Pillar>();
		items = new ArrayList<Item>();
		level_did_load = false;
	}
	
	/*
	 * @Param filename
	 * returns true if the level loads properly, false otherwise
	 */
	public boolean loadLevel(String filename) {
		level_did_load = false;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = fact.newDocumentBuilder();
			InputStream is = new FileInputStream(filename);
			Document doc = build.parse(is);
			
			System.out.println("Loading... Document Root: " + doc.getDocumentElement().getNodeName());
			
			gravity = Float.parseFloat(getValueByKey(doc.getDocumentElement(),"GRAVITY"));
			length = Integer.parseInt(getValueByKey(doc.getDocumentElement(),"LENGTH"));
			background = loadAnimation((Element)doc.getDocumentElement().getElementsByTagName("Animation").item(0));
			
			NodeList actables = doc.getElementsByTagName("Actable");
			
			for (int temp = 0; temp < actables.getLength(); temp++) {
				Node n = actables.item(temp);
		 
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					switch (((Element) n).getAttribute("type")) {
						case "Coin":
							items.add(loadCoin(((Element) n)));
							break;
						case "PlayerShielder":
							items.add(loadPlayerShielder((Element) n));
							break;
						case "PlayerRestorer":
							items.add(loadPlayerRestorer((Element) n));
							break;
						case "PlayerWarper":
							items.add(loadPlayerWarper((Element) n));
						case "Projectile":
							items.add(loadProjectile((Element) n));
							break;
						case "Pillar":
							pillars.add(loadPillar((Element) n));
							break;
						case "Player":
							player = loadPlayer((Element) n);
							break;
						default:
							throw new Exception("Unable to Load Actable of type: " + ((Element) n).getAttribute("type"));
					}
				} else {
					System.out.println("Skipped that shit yo.");
				}
			}

			level_did_load = true;
		} catch (ParserConfigurationException e) {
			level_did_load = false;
			e.printStackTrace();
		} catch (SAXException e) {
			level_did_load = false;
			e.printStackTrace();
		} catch (IOException e) {
			level_did_load = false;
			e.printStackTrace();
		} catch (Exception e) {
			level_did_load = false;
			e.printStackTrace();
		}
		return level_did_load;
	}
	
	private Actable loadActable(Element e) throws SlickException {
		Element aEl = (Element)e.getElementsByTagName("Animation").item(0);
		Animation a = loadAnimation(aEl);
		Element vEl = (Element)e.getElementsByTagName("Vector2f").item(0);
		Vector2f l = loadVector2f(vEl);
		return new Actor(a,l);
	}
	
	private Player loadPlayer(Element e) throws SlickException {
		Actable a = loadActable(e);
		int h = Integer.parseInt(getValueByKey(e,"HEALTH"));
		float f = Float.parseFloat(getValueByKey(e,"FUEL"));
		
		return new Player(a.getAnimation(),a.getLocation(), controls, h, f);
	}
	
	private Pillar loadPillar(Element e) throws SlickException {
		Actable a = loadActable(e);
		String h = getValueByKey(e,"HIDDENBLOCKS");
		return new Pillar(a.getAnimation(), a.getLocation(),h);
	}

	private Coin loadCoin(Element e) throws SlickException {
		Actable a = loadActable(e);
		return new Coin(a.getAnimation(),a.getLocation());
	}
	
	private PlayerShielder loadPlayerShielder(Element e) throws SlickException {
		int d = Integer.parseInt(getValueByKey(e, "DURATION"));
		Actable a = loadActable(e);
		return new PlayerShielder(d, a.getAnimation(), a.getLocation());
	}
	
	private PlayerRestorer loadPlayerRestorer(Element e) throws SlickException {
		int h = Integer.parseInt(getValueByKey(e, "HEALTH"));
		float f = Float.parseFloat(getValueByKey(e, "FUEL"));
		Actable a = loadActable(e);
		return new PlayerRestorer(h,f,a.getAnimation(),a.getLocation());
	}
	
	private PlayerWarper loadPlayerWarper(Element e) throws SlickException {
		int d = Integer.parseInt(getValueByKey(e, "DURATION"));
		Actable a = loadActable(e);
		return new PlayerWarper(d, a.getAnimation(), a.getLocation());
	}
	
	private Projectile loadProjectile(Element e) throws SlickException {
		int d = Integer.parseInt(getValueByKey(e, "DAMAGE"));
		Actable a = loadActable(e);
		return new Projectile(d, a.getAnimation(), a.getLocation());
	}
	
	private Animation loadAnimation(Element e) throws SlickException {
		Image[] images;
		int imgCount = Integer.parseInt(getValueByKey(e,"IMAGECOUNT"));
		images = new Image[imgCount];
		
		NodeList imageList = e.getElementsByTagName("Image");
		for(int i = 0; i < imageList.getLength(); i++) {
			Element iEl = (Element)imageList.item(i);
			images[i] = loadImage(iEl);
		}
		
		return new Animation(images,1,false);
	}
	
	private Image loadImage(Element e) throws SlickException {
		String filepath = e.getElementsByTagName("URL").item(0).getTextContent();
		return new Image(filepath);
	}
	
	private Vector2f loadVector2f(Element e) {
		String floatString = e.getElementsByTagName("X").item(0).getTextContent();
		float x =  Float.parseFloat(floatString);
		floatString = e.getElementsByTagName("Y").item(0).getTextContent();
		float y = Float.parseFloat(floatString);
		return new Vector2f(x,y);
	}
	
	private String getValueByKey(Element e, String key) {
		return e.getElementsByTagName(key).item(0).getTextContent();
	}
	
	@Override
	public void update() {
		for(Pillar p : pillars)
			p.update();
		for(Item i : items)
			i.update();
		player.update();
		
		collideActables();
	}
	
	@Override
	public void render(Graphics g) {
		if(background != null)
			background.draw(x--,-500);
		
		for(Pillar p : pillars) {
			if(isOnScreen(p.getLocation()))
				p.render(g);
		}

		for(Item i : items) 
			if(isOnScreen(i.getLocation()))
				i.render(g);

		player.render(g);
	}
	
	private boolean isOnScreen(Vector2f loc) {
		return loc.getX() >= -200
				&& loc.getX() <= 2120
				&& loc.getY() >= 0
				&& loc.getY() <= 1080;
	}
}
