package gameDevelopment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class LoadIcon {

	public static ByteBuffer[] loadIcon(String filepath,Game app)
	{
	    BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

	    try
	    {
	        image = ImageIO.read(new FileInputStream(filepath));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    ByteBuffer[] buffers = new ByteBuffer[3];
	    buffers[0] = loadIconInstance(image, 128);
	    buffers[1] = loadIconInstance(image, 32);
	    buffers[2] = loadIconInstance(image, 16);
	    return buffers;
	}

	private static ByteBuffer loadIconInstance(BufferedImage image, int dimension)
	{
	    BufferedImage scaledIcon = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = scaledIcon.createGraphics();
	    double ratio = 1;
	    if(image.getWidth() > scaledIcon.getWidth())
	    {
	        ratio = (double) (scaledIcon.getWidth()) / image.getWidth();
	    }
	    else
	    {
	        ratio = scaledIcon.getWidth() / image.getWidth();
	    }
	    if(image.getHeight() > scaledIcon.getHeight())
	    {
	        double r2 = (double) (scaledIcon.getHeight()) / image.getHeight();
	        if(r2 < ratio)
	        {
	            ratio = r2;
	        }
	    }
	    else
	    {
	        double r2 =  scaledIcon.getHeight() / image.getHeight();
	        if(r2 < ratio)
	        {
	            ratio = r2;
	        }
	    }
	    double width = image.getWidth() * ratio;
	    double height = image.getHeight() * ratio;
	    g.drawImage(image, (int) ((scaledIcon.getWidth() - width) / 2), (int) ((scaledIcon.getHeight() - height) / 2),
	            (int) (width), (int) (height), null);
	    g.dispose();

	    byte[] imageBuffer = new byte[dimension*dimension*4];
	    int counter = 0;
	    for(int i = 0; i < dimension; i++)
	    {
	        for(int j = 0; j < dimension; j++)
	        {
	            int colorSpace = scaledIcon.getRGB(j, i);
	            imageBuffer[counter + 0] =(byte)((colorSpace << 8) >> 24 );
	            imageBuffer[counter + 1] =(byte)((colorSpace << 16) >> 24 );
	            imageBuffer[counter + 2] =(byte)((colorSpace << 24) >> 24 );
	            imageBuffer[counter + 3] =(byte)(colorSpace >> 24 );
	            counter += 4;
	        }
	    }
	    return ByteBuffer.wrap(imageBuffer);
	}
}
