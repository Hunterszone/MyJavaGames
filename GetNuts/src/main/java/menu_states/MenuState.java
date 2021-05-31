package menu_states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuState {
	
	public static boolean isOn = true;
	
	public void render(Graphics g) {
		final Font titleFont = new Font("arial", Font.BOLD, 50);
		g.setFont(titleFont);
		g.setColor(Color.LIGHT_GRAY);
		
		final Font buttonFont = new Font("arial", Font.BOLD, 30);
		g.setFont(buttonFont);
		
		//Need to look into necessary assets/library so that all buttons can be
		//be defined here
	}
}