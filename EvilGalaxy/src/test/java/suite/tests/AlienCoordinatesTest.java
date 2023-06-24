package suite.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;
import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

import enemy.Alien;
import map.AlienCoordinates;

public class AlienCoordinatesTest {
	
	@Test
	public void testAlienCoordinatesOnMap() {
		Graphics g = Mockito.mock(Graphics.class);
		AlienCoordinates alienCoordinates = new AlienCoordinates();
		Alien.aliens = new ArrayList<Alien>();
		Alien.aliens.add(new Alien(TestUtils.POS_X, TestUtils.POS_Y));
		
		alienCoordinates.draw(g);
		
		Mockito.verify(g).drawImage(Alien.aliens.get(0).getImage(), 1460, 745, 20, 20, alienCoordinates);
		
        assertEquals(75, Alien.aliens.get(0).getImage().getWidth(alienCoordinates));
        assertEquals(40, Alien.aliens.get(0).getImage().getHeight(alienCoordinates));
	}

}