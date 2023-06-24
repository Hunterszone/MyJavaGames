package suite.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.mockito.Mockito;

import enums.Sprite;
import map.MapEntity;

public class MapEntityTest {
	
	@Test
	public void testEvilHeadCoordinatesOnMap() throws IOException {
		Graphics g = Mockito.mock(Graphics.class);
		MapEntity mapEntity = new MapEntity();
		BufferedImage mapImg = ImageIO.read(new File(Sprite.MAP.getImg()));
		Image scaledMap = mapImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		
		mapEntity.draw(g);
		
        assertEquals(200, scaledMap.getWidth(mapEntity));
        assertEquals(200, scaledMap.getHeight(mapEntity));
	}

}