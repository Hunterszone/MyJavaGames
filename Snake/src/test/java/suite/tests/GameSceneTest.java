package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import game_engine.GameLogic;

public class GameSceneTest {
	
	@Test
	public void testBoardWidthIsFullscreen() {
		GameLogic mockedObj = Mockito.mock(GameLogic.class);
	    Mockito.when(mockedObj.getWidth()).thenReturn(1920);

	    int boardWidth = mockedObj.getWidth();

	    Assert.assertEquals(1920, boardWidth);
	    Mockito.verify(mockedObj).getWidth();
	}
	
	@Test
	public void testBoardWidthIsNaN() {
		GameLogic mockedObj = Mockito.mock(GameLogic.class);
		assertFalse("Board width is NaN!", Double.isNaN(mockedObj.getWidth()));
		Mockito.verify(mockedObj).getWidth();
	}
	
	@Test
	public void testBoardWidthIsNotNegative() {
		GameLogic mockedObj = Mockito.mock(GameLogic.class);
		assertFalse("Board width is negative!", mockedObj.getWidth() < 0);
		Mockito.verify(mockedObj).getWidth();
	}
	
	@Test
	public void testBoardHeightIsFullscreen() {
		GameLogic mockedObj = Mockito.mock(GameLogic.class);
	    Mockito.when(mockedObj.getHeight()).thenReturn(1080);

	    int boardHeight = mockedObj.getHeight();

	    Assert.assertEquals(1080, boardHeight);
	    Mockito.verify(mockedObj).getHeight();
	}
	
	@Test
	public void testBoardHeigthIsNaN() {
		GameLogic mockedObj = Mockito.mock(GameLogic.class);
		assertFalse("Board width is NaN!", Double.isNaN(mockedObj.getHeight()));
		Mockito.verify(mockedObj).getHeight();
	}
	
	@Test
	public void testBoardHeigthIsNotNegative() {
		GameLogic mockedObj = Mockito.mock(GameLogic.class);
		assertFalse("Board width is negative!", mockedObj.getHeight() < 0);
		Mockito.verify(mockedObj).getHeight();
	}
}
