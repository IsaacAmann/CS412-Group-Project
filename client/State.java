import java.awt.Image;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.lang.Math;
import java.awt.Font;

import java.net.URL;
import java.lang.ClassLoader;

public class State
{
	public String name;
	public BufferedImage image;
	public int ownerPlayerID;
	//Used to lookup StateData within the hashmap inside of the GameState class
	public int stateID;
	public int color;
	public int units;
	
	public int unitCounterX;
	public int unitCounterY;
	public int unitCounterOffsetX;
	public int unitCounterOffsetY;
	
	private static Font counterFont;
	
	public State(String name, String imagePath, int stateID)
	{
		this.name = name;
		//start as neutral
		ownerPlayerID = -1;
		this.stateID = stateID;
		if(counterFont == null)
			counterFont = new Font("TimesRoman", Font.PLAIN, 18);
		//Default color value, States should initally draw as the color the png is, 
		//Leaving this null may cause an issue in the comparison in the changeColor method
		this.color = 0;
		this.units = 0;
		unitCounterOffsetX = 0;
		unitCounterOffsetY = 0;
		try
		{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			image = ImageIO.read(loader.getResource(imagePath));
			//image = ImageIO.read(new File(imagePath));
		}
		catch(IOException e)
		{
			System.out.println("Could not load image: " + imagePath);
		}
		//Calculate unit counter position May optimize this later or multithread, very slow
		int imageX = 0;
		int imageY = 0;
		int imageX2 = 0;
		int imageY2 = 0;
		boolean foundFirstPixel = false;
		//Looking for left most horizontal pixel and highest vertical pixel
		for(int x = 0; x < image.getWidth(); x++)
		{
			for(int y = 0; y < image.getHeight(); y++)
			{
				//Hit on a pixel
				if(image.getRGB(x,y) != 0)
				{
					//top corner pixel
					if(foundFirstPixel != true)
					{
						imageX = x;
						imageY = y;
						foundFirstPixel = true;
					}
					else
					{
						//Should return bottom corner pixel
						imageX2 = x;
						imageY2 = y;
					}
				}
			}
		}
		//Figure estimate of midpoint of the state image
		int width = imageX + imageX2;
		int height = imageY + imageY2;
		this.unitCounterX = width / 2;
		this.unitCounterY = height / 2;
		System.out.println(name + ": " + unitCounterX+ " "+ unitCounterY);
	}
	
	//Should be called when the state has been clicked on 
	public void click()
	{
		System.out.println(name + " has been clicked on");
		//changeColor(-324234);
		/*
		if(ownerPlayerID == Client.playerID)
		{
			GamePanel.selectedState = this;
		}
		*/
	}
	
	//go through each pixel in the image that does not equal 0 (transparent) and modify its color
	public void changeColor(int newRGB)
	{
		if(newRGB != color)
		{
			color = newRGB;
			for(int i = 0; i < Client.GAME_PANEL_WIDTH; i++)
			{
				for(int j = 0; j < Client.GAME_PANEL_HEIGHT; j++)
				{
					if(image.getRGB(i,j) != 0)
					{
						//Preserve the black border around the state
						if(image.getRGB(i,j) != Color.BLACK.hashCode())
							image.setRGB(i, j, newRGB); 
					}
				}
			}
		}
	}
	
	public void setUnitOffset(int x, int y)
	{
		this.unitCounterOffsetX = x;
		this.unitCounterOffsetY = y;
	}
	
	//Call from paint when the image needs to be drawn, g2D passed from paint, observer should be a reference to the GamePanel object
	public void draw(Graphics2D g2D, ImageObserver observer)
	{
		//draw image at 0,0, state images should be the same size as the game window (1000pixels x 700 pixels)
		g2D.drawImage(image, 0, 0, observer);
		g2D.setPaint(Color.BLACK);
		g2D.setFont(counterFont);
		g2D.drawString(Integer.toString(units), unitCounterX + unitCounterOffsetX, unitCounterY + unitCounterOffsetY);
		//g2D.drawString("TEST", this.unitCounterX, this.unitCounterY);
		//System.out.println(unitCounterX + " :::: " +unitCounterY);
	}
}
