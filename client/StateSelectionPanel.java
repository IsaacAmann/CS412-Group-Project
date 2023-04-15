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

public class StateSelectionPanel extends JPanel
{
	State selectedState;
	JLabel selectedStateName;
	
	JButton submitTurnButton;
	//Use JSlider for selecting units to move
	JSlider unitSlider;
	
	
	
	public StateSelectionPanel()
	{
		super();
		this.setBackground(Color.BLUE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//this.setSize(new Dimension(100,400));
		
		selectedStateName = new JLabel("Selected state: ");
		submitTurnButton = new JButton("End Turn");
		submitTurnButton.addActionListener(new SubmitListener());
		
		unitSlider = new JSlider(0, 10, 0);
		
		
		this.add(selectedStateName);
		this.add(unitSlider);
		this.add(submitTurnButton);
		
		
		
		
		this.setVisible(true);
	}
	
	//Action listener for the connect button
	private class SubmitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
}
