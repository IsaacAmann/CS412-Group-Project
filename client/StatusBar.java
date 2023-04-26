import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.Dimension;
import java.rmi.RemoteException;

public class StatusBar extends JPanel
{
	
	JLabel serverStatusLabel;
	JLabel playerNameLabel;
	JButton requestStartButton;
	
	public StatusBar()
	{
		this.setBackground(Color.GREEN);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//Create labels:
		serverStatusLabel = new JLabel("Server Status: ");
		playerNameLabel = new JLabel("Player Name: " );
		requestStartButton = new JButton("Request Game Start");
		requestStartButton.addActionListener(new startListener());
		
		//add components
		this.add(playerNameLabel);
		this.add(serverStatusLabel);
		this.add(requestStartButton);
	}
	
	public void setStatusMessage(String input)
	{
		serverStatusLabel.setText("Server Status: " + input);
	}
	
	public JButton getRequestStartButton()
	{
		return requestStartButton;
	}
	
	public class startListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				if(Client.playerID != -1)
				{
					Client.server.requestGameStart(Client.playerID);
					Client.statusBar.getRequestStartButton().setVisible(false);
				}
			}
			catch(RemoteException exception)
			{
				exception.printStackTrace();
			}
		}
	}
}
