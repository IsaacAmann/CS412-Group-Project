
import javax.swing.*;
import javax.swing.border.*;

import java.awt.geom.*;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Client extends JFrame
{
	//Constants
	public static final int WINDOW_WIDTH = 1300;
	public static final int WINDOW_HEIGHT = 740;
	
	public static final int GAME_PANEL_WIDTH = 1000;
	public static final int GAME_PANEL_HEIGHT = 700;
	
	public static final int RIGHT_PANEL_WIDTH = WINDOW_WIDTH - GAME_PANEL_WIDTH;
	public static final int RIGHT_PANEL_HEIGHT = WINDOW_HEIGHT;
	
	//Server statuses
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	public static JPanel rightPanel;
	public static GamePanel gamePanel;
	public static StateSelectionPanel stateSelectionPanel;
	public static StatusBar statusBar;
	public static ConnectionWindow connectionWindow;
	
	public static JTextField chatField;
	public static JTextArea chatArea;
	public static int chatNum = 0;
	
	public static Graphics gameGraphics;
	
	public static WarRoomServerInterface server;
	public static String host = "localhost";
	public static int port = 12345;
	
	public static int playerID;
	
	
	public Client()
	{
		super("War Room - 412");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//Background Color
		this.getContentPane().setBackground(Color.DARK_GRAY);
		
		
		//Set Icon Image
		//Method One
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("assets/double_bars.png"));
		
		//Method Two
		final String iconPath = "assets/double_bars.png";
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
		this.setIconImage(icon.getImage());
		
		//Create rightPanel
		createRightPanel();
		
		//Create gamePanel
		createGamePanel();
		
		//Create status bar
		statusBar = new StatusBar();
		
		//Add connection window
		connectionWindow = new ConnectionWindow();
		this.add(connectionWindow);
		
		//add panels 
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		this.add(statusBar, BorderLayout.PAGE_START);
		
		this.setVisible(true);
		
		JOptionPane pane = new JOptionPane();
		pane.setFont(new Font("Monospaced", Font.BOLD, 10));
		pane.setBackground(Color.BLACK);
		pane.showMessageDialog(null, "Game over, Winner: " );
	}
	
	//register with server
	//Register with server and wait for game to start
	public static void registerWithServer(String playerName)
	{
		try
		{
			playerID = Client.server.registerPlayer(playerName);
		}
		catch(RemoteException exception)
		{
			exception.printStackTrace();
		}
	}
	
	
	//Attempt to connect to the server 
	public static void initializeRMI(String address, int port)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(address,port);
			server = (WarRoomServerInterface) registry.lookup("WarRoomRMIImplementation");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	
	
	private void createRightPanel()
	{
		
		rightPanel = new JPanel();
		stateSelectionPanel = new StateSelectionPanel();
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		rightPanel.setBackground(Color.BLACK);
		rightPanel.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, RIGHT_PANEL_HEIGHT));
		
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		rightPanel.setBorder(border);
		
		///Chat Box
		chatField = new JTextField(" - Enter Message Here -");
		
		chatField.setBackground(Color.BLACK);
		chatField.setForeground(Color.WHITE);
		chatField.setFont(new Font("Monospaced", Font.ITALIC, 18));
		chatField.setCaretColor(Color.WHITE);
		
		chatField.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					chatField.setText("");
					try {
						Client.server.sendChatMessage(event.getActionCommand(), playerID);
						chatField.setText("");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		);				

		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setPreferredSize(new Dimension(100, 525));
		chatArea.setBackground(Color.BLACK);
		chatArea.setForeground(Color.WHITE);
		
		
		
		rightPanel.add(stateSelectionPanel);
		rightPanel.add(chatArea);
		rightPanel.add(chatField);
		
		rightPanel.setVisible(true);
	}
	
	
	
	private void createGamePanel()
	{
		gamePanel = new GamePanel();
		gamePanel.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
		gamePanel.setBackground(Color.BLACK);
		
		gamePanel.setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		gamePanel.setBorder(border);
		
		gamePanel.setVisible(true);
	}
	
}
