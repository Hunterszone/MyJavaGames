package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import potogold.Points;

public class PointsTest {

	private Font font;
	private int x, y;

	@Test
	public void testPoints() {
		Points points = new Points(x, y, font);
		assertNotNull(points.incrementPoints(new Integer(Points.points)));
	}

}
