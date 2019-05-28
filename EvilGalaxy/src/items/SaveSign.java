package items;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import frames.GameMenu;
import game_engine.SpritePattern;

public class SaveSign extends SpritePattern implements KeyListener {

	private static final long serialVersionUID = 1L;
	public static SaveSign saveSign;

	public SaveSign(int x, int y) {
		super(x, y);
	}

	private void initSave() {

		loadImage("images/saved.png");
		getImageDimensions();
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if (((key == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.ALT_MASK) != 0)) || 
				((key == KeyEvent.VK_X) && ((e.getModifiers() & KeyEvent.ALT_MASK) != 0) && 
						GameMenu.autosave.isSelected() == false)) {
			saveSign.setVisible(true);
            initSave();
        }else {
        	saveSign.setVisible(false);
        }
		
	}
}