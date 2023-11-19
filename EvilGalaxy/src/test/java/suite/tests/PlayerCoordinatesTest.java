package suite.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;

import org.junit.Test;
import org.mockito.Mockito;

import enemy.Dragon;
import enemy.PlayerShip;
import map.PlayerCoordinates;
import util.Constants;

public class PlayerCoordinatesTest {
	
	@Test
	public void testPlayerCoordinatesOnMap() {
		Graphics g = Mockito.mock(Graphics.class);
		PlayerCoordinates playerCoordinates = new PlayerCoordinates();
		PlayerShip.playerShip = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		
		playerCoordinates.draw(g);
		
		Mockito.verify(g).drawImage(PlayerShip.playerShip.getImage(), 1118, 529, 50, 50, playerCoordinates);
		
        assertEquals(168, PlayerShip.playerShip.getImage().getWidth(playerCoordinates));
        assertEquals(63, PlayerShip.playerShip.getImage().getHeight(playerCoordinates));
	}

}