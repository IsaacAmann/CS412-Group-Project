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
	public int ownerPlayerID;
	
	public State(String name, String imagePath)
	{
		this.name = name;
		//start as neutral
		ownerPlayerID = -1;
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
		//changeColor(-324234);
		if(ownerPlayerID == Client.playerID)
		{
			GamePanel.selectedState = this;
		}
	}
	
	//go through each pixel in the image that does not equal 0 (transparent) and modify its color
	public void changeColor(int newRGB)
	{
		for(int i = 0; i < Client.GAME_PANEL_WIDTH; i++)
		{
			for(int j = 0; j < Client.GAME_PANEL_HEIGHT; j++)
			{
				if(image.getRGB(i,j) != 0)
				{
					image.setRGB(i, j, newRGB); 
					
				}
			}
		}
	}
	
	//Call from paint when the image needs to be drawn, g2D passed from paint, observer should be a reference to the GamePanel object
	public void draw(Graphics2D g2D, ImageObserver observer)
	{
		//draw image at 0,0, state images should be the same size as the game window (1000pixels x 700 pixels)
		g2D.drawImage(image, 0, 0, observer);
		
	}
}
