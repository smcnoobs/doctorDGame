package levelEditor;

import java.awt.Frame;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.geom.Vector2f;

import doctord.*;

public class GameLevelMaker extends BasicGame implements InputProviderListener {
	private Pillar[] pillars;
	private ArrayList<Item> items;
	private Player player;
	private final int delta = 5;
	private boolean debounce = false;
	
	/*
	 * STATUS
	 * 0 	=>	Write Level
	 * 1	=>	Edit Pillars
	 * 2	=>	Position Player
	 * 3	=>	Add Coin
	 * 4	=>	Add PlayerShielder
	 * 5	=>	Add PlayerRestorer
	 * 6	=>	Add Projectile
	 * 7	=>	Randomize Pillars
	 */
	private int status = 1;
	
	private InputProvider provider;
	private Input input;
	
	public static final float P_HEIGHT = Pillar.PILLAR_HEIGHT / 2;
	public static final float P_WIDTH = Pillar.PILLAR_WIDTH / 2;
	
	public GameLevelMaker(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Draw Background
		drawBG(gc,g);
		
		// Draw Pillars
		drawPillars(gc,g);
		
		// Draw Items
		drawItems(gc, g);
		
		// Draw Player
		if(player != null && vectorIsInRect(player.getLocation(),0,0,1920,1080)) {
			g.setColor(new Color(231, 76, 60));
			g.fillOval(player.getLocation().getX()/2 + 5, player.getLocation().getY()/2 + 5, P_WIDTH - 5, P_HEIGHT - 5);
		}
			
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		
		// Initialize Pillars
		String temp = (String)JOptionPane.showInputDialog(
                new Frame(),
                "How long will the level be? -> This translates to the amount of pillars",
                "Level Maker - Number of Pillars",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
		int npill = Integer.parseInt(temp);
		
		Animation a = askForAnimation("Pillar");
		
		pillars = new Pillar[npill];
		for(int i = 0; i < npill; i++) {
			pillars[i] = new Pillar(a, new Vector2f(200 + i * Pillar.PILLAR_WIDTH, 0));
		}
		
		// Initialize Items
		items = new ArrayList<Item>();
		
		input = gc.getInput();
		provider = new InputProvider(input);
		provider.addListener((InputProviderListener) this);
		
		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		Vector2f mouseLoc = new Vector2f(input.getMouseX(),input.getMouseY());
		
		// Manage State of Game Builder.
		if(input.isMouseButtonDown(0)) {
			if(vectorIsInRect(mouseLoc,0,1080/2,100,50)) {
				moveGameElements(new Vector2f(delta, 0));
			}
			if(vectorIsInRect(mouseLoc,(1920/2)-100,1080/2,100,50)) {
				moveGameElements(new Vector2f(-delta, 0));
			}
			if(vectorIsInRect(mouseLoc,(1920/4)-25,1080/2 + 10,80,30)) {
				moveGameElements(new Vector2f(200 - pillars[0].getLocation().getX(), 0));
			}
			
			if(!debounce) {
				
				for(int i = 0; i < 8; i++) {
					if(vectorIsInRect(mouseLoc,1920/2 + 50, (60 * i) + 10, 300, 50)) {
						status = i;
						handleStatusChange();
						debounce = true;
					}
				}
				
				if(vectorIsInRect(mouseLoc,0,0,1920/2,1080/2)) {
					handleGameClick(mouseLoc);
					debounce = true;
				}
			}
		}
		
		if(debounce && !input.isMouseButtonDown(0)) {
			debounce = false; // Wait until you release the mouse
		}
	}
	
	private Animation askForAnimation(String type) throws SlickException {
		String temp = (String)JOptionPane.showInputDialog(
                new Frame(),
                "What is the qualified name of the Image for the " + type + "?",
                "Level Maker - " + type + " Image",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "./res/images/dehkhoda_jetpack.png");
		return new Animation(new Image[] {new Image(temp)}, 1, false);
	}
	
	private void handleGameClick(Vector2f click) {
		try {
			String temp;
			switch(status) {
			case 1: // Edit Pillars
				hideBlock(click);
				break;
			case 2: // Position Player
				positionPlayer(new Vector2f(click.getX() * 2, click.getY() * 2));
				break;
			case 3: // Add Coin
				items.add(new Coin(askForAnimation("Coin"), new Vector2f(click.getX() * 2, click.getY() * 2)));
				break;
			case 4: // Add PlayerShielder
				temp = (String)JOptionPane.showInputDialog(
		                new Frame(),
		                "How long does the shield last? -> Must be an int value.",
		                "Level Maker - PlayerShielder Duration",
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                null,
		                "100");
				int dur = Integer.parseInt(temp);
				items.add(new PlayerShielder(dur,askForAnimation("PlayerShielder"), new Vector2f(click.getX() * 2, click.getY() * 2)));
				break;
			case 5: // Add PlayerRestorer
				temp = (String)JOptionPane.showInputDialog(
		                new Frame(),
		                "How much does the Restorer Heal? -> Must be an int value.",
		                "Level Maker - PlayerRestorer Health",
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                null,
		                "1");
				int health = Integer.parseInt(temp);
				temp = (String)JOptionPane.showInputDialog(
		                new Frame(),
		                "How much fuel does the Restorer give? -> Must be a float value.",
		                "Level Maker - PlayerRestorer Fuel",
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                null,
		                "100");
				int fuel = Integer.parseInt(temp);
				items.add(new PlayerRestorer(health,fuel,askForAnimation("PlayerRestorer"), new Vector2f(click.getX() * 2, click.getY() * 2)));
				break;
			case 6: // Add Projectile
				temp = (String)JOptionPane.showInputDialog(
		                new Frame(),
		                "How much damage does the Projectile do? -> Must be an int value.",
		                "Level Maker - Projectile Damage",
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                null,
		                "100");
				int dmg = Integer.parseInt(temp);
				items.add(new Projectile(dmg,askForAnimation("PlayerShielder"), new Vector2f(click.getX() * 2, click.getY() * 2)));
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleStatusChange() {
		try {
			switch(status) {
			case 0:
				write();
				break;
			case 7:
				randomizePillars();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Input Functions
	
	@Override
	public void controlPressed(Command arg0) {}
	@Override
	public void controlReleased(Command arg0) {}
	@Override
	public void mousePressed(int button, int x, int y) {}
	
	public static void main(String args[]) {
		// Initialize Game and its Container
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new GameLevelMaker("Level Editor"));
			appgc.setDisplayMode(1366, 768, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(doctorDGame.class.getName()).log(Level.SEVERE, null, ex);
		} 
	}
	
	// Layout Functions
	
	private void drawBG(GameContainer gc, Graphics g) {
		// Background
		g.setColor(new Color(44, 62, 80));
		g.fillRect(0,0,1366,768);
		
		// Game Screen - Scale is 1/2
		g.setColor(new Color(236, 240, 241));
		g.fillRect(0,0,1920/2,1080/2);
		
		// Next / Prev / Home Pillar Screen Buttons
		g.setColor(new Color(200,50,50));
		g.fillRect(0,1080/2,100,50);
		g.fillRect(1920/2 - 100,1080/2,100,50);
		g.fillRect((1920/4)-25,1080/2 + 10,80,30);
		
		g.setColor(Color.black);
		g.drawString("<<",40,1080/2 + 15);
		g.drawString(">>",1920/2 - 60,1080/2 + 15);
		g.drawString("Home.",(1920/4) -15,1080/2 + 20);
		
		// RIGHT SIDE BUTTONS
		String[] s = new String[] {"Write Level.", 
				"Edit Pillars.",
				"Position Player.",
				"Add Coin.",
				"Add PlayerShielder.",
				"Add PlayerRestorer.",
				"Add Projectile.",
				"Randomize Pillars."};
		for(int i = 0; i < 8; i++) {
			if(status == i) 
				g.setColor(new Color(243, 156, 18));
			else
				g.setColor(new Color(100,100,255));
			g.fillRect(1920/2 + 50, (60 * i) + 10, 300, 50);
			g.setColor(Color.white);
			g.drawString(s[i],1920/2 + 60,(60 * i) + 20);
		}
	}
	
	private void drawItems(GameContainer gc, Graphics g) {
		g.setColor(Color.green);
		for(Item i : items) {
			if(vectorIsInRect(i.getLocation(),0,0,1920,1080))
				g.fillOval(i.getLocation().getX()/2 + 5,i.getLocation().getY()/2 + 5, P_HEIGHT - 5, P_WIDTH - 5); 
		}
	}
	
	private void drawPillars(GameContainer gc, Graphics g) {
		g.setColor(new Color(52, 152, 219));
		for(int i = 0; i < pillars.length; i ++) {
			if(vectorIsInRect(pillars[i].getBlocks()[0].getLocation(),0,0,1920,1080)) {
				for(PillarBlock pb : pillars[i].getBlocks()) {
					if(!pb.isHidden()) {
						g.fillRect(pb.getLocation().getX()/2 + 5,pb.getLocation().getY()/2 + 5,P_HEIGHT - 5,P_WIDTH - 5);
					}
				}
			}
		}
	}
	
	private void moveGameElements(Vector2f d) {
		movePillars(d);
		moveItems(d);
		if(player != null)
			player.move(d);
	}
	
	private void movePillars(Vector2f d) {
		for(Pillar p : pillars) 
			p.move(d);
	}
	
	private void moveItems(Vector2f d) {
		for(Item i : items)
			i.move(d);
	}
	
	private boolean vectorIsInRect(Vector2f in, int x, int y, int width, int height) {
		return (in.getX() >= x
				&& in.getX() <= x + width
				&& in.getY() >= y
				&& in.getY() <= y + height
				);
	}
	
	private boolean vectorIsInRect(Vector2f in, float x, float y, float width, float height) {
		return (in.getX() >= x
				&& in.getX() <= x + width
				&& in.getY() >= y
				&& in.getY() <= y + height
				);
	}
	
	private void positionPlayer(Vector2f clicked) throws SlickException {
		// If there is no player, make one.
		if(player == null) {
			// Get Player's Animation
			Animation a = askForAnimation("Player");
			
			// Get Player's Health
			String temp = (String)JOptionPane.showInputDialog(
	                new Frame(),
	                "How much health does the player have? -> Must be an integer value.",
	                "Level Maker - Player Health",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                "");
			int health = Integer.parseInt(temp);
			
			// Get Player's Fuel
			temp = (String)JOptionPane.showInputDialog(
	                new Frame(),
	                "How much fuel does the player have? -> Must be a float value.",
	                "Level Maker - Player Fuel",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                "");
			float fuel = Float.parseFloat(temp);
			
			// Make Player at clicked location
			player = new Player(a,clicked,new ControlHandler(),health,fuel);
		} else {
			player.moveTo(clicked);
		}
	}
	
	private void hideBlock(Vector2f clicked) {
		// First, put mouse where it should be
		Vector2f loc = new Vector2f(clicked.getX() * 2, clicked.getY() * 2);
		
		// Then, check against all of the blocks
		for(Pillar p : pillars) {
			for(PillarBlock pb : p.getBlocks()) {
				if(vectorIsInRect(loc,pb.getLocation().getX(),pb.getLocation().getY(),Pillar.PILLAR_WIDTH,Pillar.PILLAR_WIDTH)) {
					if(pb.isHidden()) 
						pb.show();
					else
						pb.hide();
				}
			}
		}
	}
	

	// Create Floors and ceilings, with a random chance of blocks in the middle
	private void randomizePillars() {
		int floor = 1;
		int ceil = 1;
		Random rand = new Random();
		for(Pillar p : pillars) {
			// Random Chance of showing any block
			for(PillarBlock pb : p.getBlocks()) {
				int i = rand.nextInt(100);
				if(i < 97)
					pb.hide();
				else
					pb.show();
			}
			int i = rand.nextInt(100);
			if(i < 10 && ceil > 1)
				ceil--;
			else if(i > 90 && ceil < 10)
				ceil++;
			
			i = rand.nextInt(100);
			if(i < 10 && floor > 1)
				floor--;
			else if(i > 90 && floor < 10)
				floor++;
			
			for(int j = 0; j < floor; j++)
				p.getBlocks()[Pillar.PILLAR_COUNT - j - 1].show();
			
			for(int j = 0; j < ceil; j++)
				p.getBlocks()[j].show();
		}
	}
	
	private void write() throws FileNotFoundException {
		String level_path = (String)JOptionPane.showInputDialog(
                new Frame(),
                "Please Enter the location of the Level File to be written to.",
                "Level Maker - Level Filepath",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "./res/levels/");
		LevelWriter writer = new LevelWriter(level_path);
		
		Item[] arrItems = new Item[items.size()];
		arrItems = items.toArray(arrItems);
		writer.loadAssets(arrItems,pillars,player,0,0);
		writer.writeLevel();
		writer.finishWriting();
	}
}
