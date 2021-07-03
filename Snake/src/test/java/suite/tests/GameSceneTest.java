package suite.tests;

import static org.junit.Assert.assertFalse;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import snake.GameScene;

public class GameSceneTest {
	
	@Test
	public void testBoardWidthIsFullscreen() {
		GameScene mockedObj = Mockito.mock(GameScene.class);
	    Mockito.when(mockedObj.getWidth()).thenReturn(1920);

	    int boardWidth = mockedObj.getWidth();

	    Assert.assertEquals(1920, boardWidth);
	    Mockito.verify(mockedObj).getWidth();
	}
	
	@Test
	public void testBoardWidthIsNaN() {
		GameScene mockedObj = Mockito.mock(GameScene.class);
		assertFalse("Board width is NaN!", Double.isNaN(mockedObj.getWidth()));
		Mockito.verify(mockedObj).getWidth();
	}
	
	@Test
	public void testBoardWidthIsNotNegative() {
		GameScene mockedObj = Mockito.mock(GameScene.class);
		assertFalse("Board width is negative!", mockedObj.getWidth() < 0);
		Mockito.verify(mockedObj).getWidth();
	}
	
	@Test
	public void testBoardHeightIsFullscreen() {
		GameScene mockedObj = Mockito.mock(GameScene.class);
	    Mockito.when(mockedObj.getHeight()).thenReturn(1080);

	    int boardHeight = mockedObj.getHeight();

	    Assert.assertEquals(1080, boardHeight);
	    Mockito.verify(mockedObj).getHeight();
	}
	
	@Test
	public void testBoardHeigthIsNaN() {
		GameScene mockedObj = Mockito.mock(GameScene.class);
		assertFalse("Board width is NaN!", Double.isNaN(mockedObj.getHeight()));
		Mockito.verify(mockedObj).getHeight();
	}
	
	@Test
	public void testBoardHeigthIsNotNegative() {
		GameScene mockedObj = Mockito.mock(GameScene.class);
		assertFalse("Board width is negative!", mockedObj.getHeight() < 0);
		Mockito.verify(mockedObj).getHeight();
	}
	
	@Test
	public void loadSnakeBodyImg() throws IOException {
		ImageIcon mockedObj = Mockito.mock(ImageIcon.class);
		Mockito.when(mockedObj.getImage()).thenReturn(ImageIO.read(new File("images/dot.png")));

	    Image snakeBody = mockedObj.getImage();

	    Assert.assertNotNull("Image is null!", snakeBody);
	    Mockito.verify(mockedObj).getImage();
	}
	
	@Test
	public void loadItemToCollectImg() throws IOException {
		ImageIcon mockedObj = Mockito.mock(ImageIcon.class);
		Mockito.when(mockedObj.getImage()).thenReturn(ImageIO.read(new File("images/apple.png")));

	    Image itemToCollect = mockedObj.getImage();

	    Assert.assertNotNull("Image is null!", itemToCollect);
	    Mockito.verify(mockedObj).getImage();
	}
	
	@Test
	public void loadSnakeHeadImg() throws IOException {
		ImageIcon mockedObj = Mockito.mock(ImageIcon.class);
		Mockito.when(mockedObj.getImage()).thenReturn(ImageIO.read(new File("images/head.png")));

	    Image snakeHead = mockedObj.getImage();

	    Assert.assertNotNull("Image is null!", snakeHead);
	    Mockito.verify(mockedObj).getImage();
	}
}
