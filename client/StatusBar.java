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
	
	
	public StatusBar()
	{
		this.setBackground(Color.GREEN);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		//Create labels:
		serverStatusLabel = new JLabel("Server Status: ");
		playerNameLabel = new JLabel("Player Name: " );
		
		//add components
		this.add(playerNameLabel);
		this.add(serverStatusLabel);
	}
}
