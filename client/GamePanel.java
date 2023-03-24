import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.io.File;
import java.io.IOException;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import java.awt.Image;
import java.awt.image.*;
import javax.imageio.ImageIO;


public class GamePanel extends JPanel
{
	private static BufferedImage mapImage;
	
	public GamePanel()
	{
		super();
		//Load Images
		try
		{
			mapImage = ImageIO.read(new File("assets/map.png"));
		}
		catch(IOException e)
		{
			System.out.println("Could not load image");
		}
	}
	
	public void paint(Graphics g)
	{
		//Have to call this first, resets the panel size otherwise
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.drawImage(mapImage,0,0,this);
		
		Rectangle2D rectangle = new Rectangle2D.Float(40,40,20,20);
		g2D.draw(rectangle);
	}
}
