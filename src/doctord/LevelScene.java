package doctord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LevelScene extends doctord.Scene {
	// Actables
	private Player player;
	private ArrayList<Pillar> pillars;
	private ArrayList<Item> items;
	
	// Level Constants
	private float gravity = (float)9.8;
	private Music music;
	private Animation background;
	private boolean level_did_load;
	private static boolean paused = false;
	private StatDisplay HUD;
	private String levelName, filename;
	private static String lname;
	
	
	// For Testing
	float distancePassed = 0;
	
	private void collideActables() {
		for(int i = 0; i < pillars.size(); i++) {
			if(pillars.get(i).isDead())
				pillars.remove(i);
			else
				player.collide(pillars.get(i));
		}
		for(Item i : items) {
			if(i.isDead())
				i = null;
			else
				player.collide(i);
		}
	}
	
	public void reloadLevel() {
		// Clean Level
		player = null;
		pillars.clear();
		items.clear();
		// load level
		loadLevel(this.filename);
	}
	
	public LevelScene() {		
		pillars = new ArrayList<Pillar>();
		items = new ArrayList<Item>();
		HUD = new StatDisplay();
	}
	
	/*
	 * @Param filename
	 * returns true if the level loads properly, false otherwise
	 */
	public boolean loadLevel(String filename) {
		this.filename = filename;
		level_did_load = false;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = fact.newDocumentBuilder();
			InputStream is = new FileInputStream(filename);
			Document doc = build.parse(is);
			
			System.out.println("Loading... Document Root: " + doc.getDocumentElement().getNodeName());
			
			levelName = getValueByKey(doc.getDocumentElement(),"NAME");
			lname = levelName;
			gravity = Float.parseFloat(getValueByKey(doc.getDocumentElement(),"GRAVITY"));
//			length = Integer.parseInt(getValueByKey(doc.getDocumentElement(),"LENGTH"));
			background = loadAnimation((Element)doc.getDocumentElement().getElementsByTagName("Animation").item(0));
			music = new Music(getValueByKey(doc.getDocumentElement(),"MUSIC"));
			
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
		
		return new Player(a.getAnimation(),a.getLocation(), h, f);
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
		if(!paused && Player.getHealth() > 0)
			updateNormally();
		else 
			updatePaused();
	}
	
	private void updateNormally() {
		if(music != null && !doctorDGame.isMuted() && !music.playing())
			music.play();
		
		distancePassed = distancePassed - PillarBlock.BLOCK_SPEED / Pillar.PILLAR_WIDTH;
		if(!levelName.equals(lname))
			lname = levelName;
		
		for(Pillar p : pillars)
			p.update();
		for(Item i : items)
			i.update();
		player.update();
		HUD.update();
		
		collideActables();
		
		if(pillars.isEmpty() && !paused) {
			finished = true;
		}
	}
	
	private void updatePaused() {
		if(!paused)
			paused = true;
	}
	
	@Override
	public void playMusic() {
		if(music != null && !music.playing()) 
			music.play();
	}
	
	@Override
	public void silenceMusic() {
		if(music != null) {
			music.setVolume(music.getVolume() - 0.1f);
		}
	}
	
	@Override
	public void unSilenceMusic() {
		if(music != null)
			music.setVolume(1.0f);
	}
	
	@Override
	public void stopMusic() {
		if(music != null)
			music.stop();
	}
	
	@Override
	public void render(Graphics g) {
		if(background != null)
			background.draw(distancePassed,0);

		renderPillars(g);
		renderItems(g);
		player.render(g);
		
		if(paused) {
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0,0,1920,1080);
			HUD.renderPauseScreen(g);
			if(music != null)
				music.setVolume(0.5f);
		} else if(music != null)
			music.setVolume(1.0f);
		HUD.render(g);
	}
	
	private void renderPillars(Graphics g) {
		boolean hasDrawn = false;
		for(Pillar p : pillars) {
			if(isOnScreen(p.getLocation())) {
				if(!p.isDead()) {
					p.render(g);
					hasDrawn = true;
				}
			}
			else {
				if(!hasDrawn)
					p.die(0);	// No Longer on Screen -> Release from memory
				else
					break;
			}
		}
	}
	
	private void renderItems(Graphics g) {
		for(Item i : items) {
			if(isOnScreen(i.getLocation())) {
				if(!i.isDead()) {
					i.render(g);
				}
			}
		}
	}
	
	private boolean isOnScreen(Vector2f loc) {
		return loc.getX() >= -200
				&& loc.getX() <= 2120
				&& loc.getY() >= 0
				&& loc.getY() <= 1080;
	}
	
	public float getGravity() {
		return gravity;
	}
	
	public static String getLevelName() {
		return lname;
	}
	
	public void pause() {
		if(!paused)
			paused = true;
		else
			paused = false;
	}
	
	public static boolean isPaused() {
		return paused;
	}
}
