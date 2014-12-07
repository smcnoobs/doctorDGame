package levelEditor;

public enum EditorButton {
	/*"Edit Pillars."
	"Position Player.",
	"Add Coin.",
	"Add PlayerShielder.",
	"Add PlayerRestorer.",
	"Add Projectile.",
	"Randomize Pillars."*/

	POSITION_PLAYER ("Position Player."),
	EDIT_PILLARS ("Edit Pillars."),
	RANDOMIZE_PILLARS ("Randomize Pillars."),
	ADD_COIN ("Add Coin."),
	PLAYER_SHIELDER ("Add PlayerShielder."),
	PLAYER_RESTORER ("Add PlayerRestorer."),
	PROJECTILE ("Add Projectile."),
	REMOVE_LAST_ITEM ("Remove Last Item."),
	WRITE_LEVEL ("Write Level."),
	LOAD_LEVEL ("Load Level.");
	
	
	public final String title;
	public static final int NUM_BUTTONS = 10;
	
	EditorButton(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public static EditorButton getByInt(int i) {
		switch(i) {
		case 0:
			return POSITION_PLAYER;
		case 1:
			return EDIT_PILLARS;
		case 2:
			return RANDOMIZE_PILLARS;
		case 3:
			return ADD_COIN;
		case 4:
			return PLAYER_SHIELDER;
		case 5:
			return PLAYER_RESTORER;
		case 6:
			return PROJECTILE;
		case 7:
			return REMOVE_LAST_ITEM;
		case 8:
			return WRITE_LEVEL;
		case 9:
			return LOAD_LEVEL;
		default:
			return null;
		}
	}
}
