package client;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.*;
import javax.swing.border.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JButton;


public class StateSelectionPanel extends JPanel
{
	State selectedState;
	JLabel selectedStateName;
	JLabel selectedState2Name;
	JButton submitTurnButton;
	//Use JSlider for selecting units to move
	JSlider unitSlider;
	
	
	public StateSelectionPanel()
	{
		super();
		this.setBackground(Color.BLACK);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		//Font Color
		//Change text color of the second Paragraph


		selectedStateName = new JLabel("<html><br><font color=#00FFFF> Selected State: </font><html>");

		submitTurnButton = new JButton("<html><br><font color=#00FFFF> End Turn </font><html>");
		submitTurnButton.setBackground(Color.DARK_GRAY);

		selectedState2Name = new JLabel("<html><br><font color=#00FFFF> Target State: </font><html>");


		submitTurnButton.addActionListener(new SubmitListener());


		unitSlider = new JSlider(0, 10, 0);
		unitSlider.setBackground(Color.GRAY);
		unitSlider.setForeground(Color.CYAN);

		this.add(selectedStateName);
		this.add(selectedState2Name);
		this.add(unitSlider);
		this.add(submitTurnButton);
		this.setVisible(true);
	}
	
	//Action listener for the submit turn button
	private class SubmitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
	
	public JLabel getSelectedStateLabel()
	{
		return this.selectedStateName;
	}
	
	public JLabel getSelectedStateLabel2()
	{
		return this.selectedState2Name;
	}
	
	public JSlider getUnitSlider()
	{
		return this.unitSlider;
	}
	
	
}
