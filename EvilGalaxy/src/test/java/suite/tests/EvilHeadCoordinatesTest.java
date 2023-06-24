package suite.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;

import org.junit.Test;
import org.mockito.Mockito;

import enemy.EvilHead;
import map.EvilHeadCoordinates;
import util.Constants;

public class EvilHeadCoordinatesTest {
	
	@Test
	public void testEvilHeadCoordinatesOnMap() {
		Graphics g = Mockito.mock(Graphics.class);
		EvilHeadCoordinates evilHeadCoordinates = new EvilHeadCoordinates();
		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		
		evilHeadCoordinates.draw(g);
		
		Mockito.verify(g).drawImage(EvilHead.evilHead.getImage(), 1310, 670, 50, 50, evilHeadCoordinates);
		
        assertEquals(110, EvilHead.evilHead.getImage().getWidth(evilHeadCoordinates));
        assertEquals(55, EvilHead.evilHead.getImage().getHeight(evilHeadCoordinates));
	}

}