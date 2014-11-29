package doctord;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Vector2f;


@SuppressWarnings("rawtypes")
public class Item extends Actor implements Comparable {
	
	//@Override
	public void update() {
//		location.set(location.getX() - PillarBlock.BLOCK_SPEED, location.getY());
		super.move(new Vector2f(-PillarBlock.BLOCK_SPEED, 0));
	}
	
	/*
	 * @see Actable#collide(Actable)
	 * @Override
	 */
	public boolean collide(Actable a) {
		boolean c = super.collide(a);
		if(c && a instanceof Player) 
			die(0); // Figure out timing later
		return c;
	}
	
	public Item(Animation sprites, Vector2f location) {
		super(sprites,location);
	}
	
	public int compareTo(Object obj) {
		if(obj instanceof Item) {
			return (super.location.getX() < ((Item)obj).getLocation().getX()) ? -1 : 
				(super.location.getX() > ((Item)obj).getLocation().getX()) ? 1 : 0; 
		}
		return -1;
	}
}
