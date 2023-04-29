package client;


import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.awt.*;

public class StatusBar extends JPanel
{
	
	JLabel serverStatusLabel;
	JLabel playerNameLabel;
	JButton requestStartButton;
	
	public StatusBar()
	{
		this.setBackground(Color.DARK_GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //Create labels:
        playerNameLabel = new JLabel(" Player Name: " );
        serverStatusLabel = new JLabel("Server Status: ");
        requestStartButton  = new JButton(" Request Game Start ");

        serverStatusLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        playerNameLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        
        serverStatusLabel.setForeground(Color.WHITE);
        playerNameLabel.setForeground(Color.WHITE);
        
        requestStartButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        
        requestStartButton.setBackground(Color.BLACK);
        requestStartButton.setForeground(Color.WHITE);
        requestStartButton.addActionListener(new startListener());
 

        
        //add components
        //this.add(Box.createVerticalStrut(5));
        //this.add(Box.createHorizontalStrut(5));
        this.add(requestStartButton);
        this.add(playerNameLabel);
        this.add(serverStatusLabel);
        
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
