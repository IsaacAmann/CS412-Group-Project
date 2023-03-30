import java.awt.Image;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class State
{
	public String name;
	public BufferedImage image;
	
	public State(String name, String imagePath)
	{
		this.name = name;
		try
		{
			image = ImageIO.read(new File(imagePath));
		}
		catch(IOException e)
		{
			System.out.println("Could not load image: " + imagePath);
		}
	}
	
	//Should be called when the state has been clicked on 
	public void click()
	{
		System.out.println(name + " has been clicked on");
	}
	
	//Call from paint when the image needs to be drawn, g2D passed from paint, observer should be a reference to the GamePanel object
	public void draw(Graphics2D g2D, ImageObserver observer)
	{
		//draw image at 0,0, state images should be the same size as the game window (1000pixels x 700 pixels)
		g2D.drawImage(image, 0, 0, observer);
	}
}
