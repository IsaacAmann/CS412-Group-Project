import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Client extends JFrame
{
	//Constants
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 650;
	
	public static final int GAME_PANEL_HEIGHT = WINDOW_HEIGHT;
	public static final int GAME_PANEL_WIDTH = 700;
	
	public static JPanel rightPanel;
	public static JPanel gamePanel;
	
	
	public Client()
	{
		super("War Room Client");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//Create rightPanel
		createRightPanel();
		
		//Create gamePanel
		createGamePanel();
		
		//add panels 
		this.add(gamePanel);
		this.add(rightPanel);
		
		
		this.setVisible(true);	
	}
	
	private void createRightPanel()
	{
		rightPanel = new JPanel();
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		rightPanel.setBackground(Color.RED);
		//rightPanel.setSize(RIGHT_PANEL_WIDTH, RIGHT_PANEL_HEIGHT);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		rightPanel.setBorder(border);
		
		rightPanel.setVisible(true);
	}
	
	private void createGamePanel()
	{
		gamePanel = new JPanel();
		gamePanel.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
		//gamePanel.setBackground(Color.GREEN);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		gamePanel.setBorder(border);
		
		gamePanel.setVisible(true);
	}
}
