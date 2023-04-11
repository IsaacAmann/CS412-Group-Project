import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import java.awt.Image;
import java.awt.image.*;
import javax.imageio.ImageIO;

import java.lang.Thread;
import java.lang.InterruptedException;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class GamePanel extends JPanel
{
	//Constants
	public static final int NUMBER_STATES = 1;
	public static final int frameWaitTime = 30;
	
	public static State[] states = new State[NUMBER_STATES];
	private static BufferedImage mapImage;
	private GamePanelMouseListener mouseListener;
	
	private Timer gameTimer;
	
	public static State selectedState;
	
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
		//Add mouse listener to game panel. Mouse events only triggered by mouse input inside game panel
		//Other inputs from the sidebar should be handled separately
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
		//Load states
		loadStates();
		
		//Start game loop
		gameTimer = new Timer(frameWaitTime, new GameLoop());
	}
	//Game update function, also calls repaint to draw the screen
	private class GameLoop implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}
	}
	

	//Creating state objects
	private void loadStates()
	{
		states[0] = new State("Test State", "assets/testState.png", 0);
	}
	//Go through each state image and see if the color is anything other than transparent
	//If it is, this indicates that state was clicked on
	public static void checkStateMouse(int mouseX, int mouseY)
	{
		int currentRGB;
		for(int i = 0; i < states.length; i++)
		{
			currentRGB = states[i].image.getRGB(mouseX, mouseY);
			//System.out.println("RGB " + i + " " + currentRGB);
			//RGB of 0 indicates the pixel is transparent
			if(currentRGB != 0)
				states[i].click();
		}
	}
	
	public void paint(Graphics g)
	{
		//Have to call this first, resets the panel size otherwise
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.drawImage(mapImage,0,0,this);
		
		//draw all states
		for(int i = 0; i < states.length; i++)
		{
			states[i].draw(g2D, this);
		}
		Rectangle2D rectangle = new Rectangle2D.Float(40,40,20,20);
		g2D.draw(rectangle);
	}
	
}
