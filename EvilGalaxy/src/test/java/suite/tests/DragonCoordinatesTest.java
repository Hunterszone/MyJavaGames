package suite.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;
import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

import enemy.Alien;
import enemy.Dragon;
import map.DragonCoordinates;

public class DragonCoordinatesTest {
	
	@Test
	public void testDragonCoordinatesOnMap() {
		Graphics g = Mockito.mock(Graphics.class);
		DragonCoordinates dragonCoordinates = new DragonCoordinates();
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Dragon.dragons.add(new Dragon(TestUtils.POS_X, TestUtils.POS_Y));
		
		dragonCoordinates.draw(g);
		
		Mockito.verify(g).drawImage(Dragon.dragons.get(0).getImage(), 1118, 604, 40, 40, dragonCoordinates);
		
        assertEquals(150, Dragon.dragons.get(0).getImage().getWidth(dragonCoordinates));
        assertEquals(150, Dragon.dragons.get(0).getImage().getHeight(dragonCoordinates));
	}

}