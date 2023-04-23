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


		selectedStateName = new JLabel(" -- Selected State -- ");
		submitTurnButton = new JButton("<html><font color=#ffffff> End Turn </font><html>");
		submitTurnButton.setBackground(Color.DARK_GRAY);
		selectedState2Name = new JLabel(" -- Targeted State -- ");

		selectedStateName.setFont(new Font("Monospaced", Font.BOLD, 20));
		selectedState2Name.setFont(new Font("Monospaced", Font.BOLD, 20));
		selectedStateName.setForeground(Color.white);
		selectedState2Name.setForeground(Color.white);
		selectedStateName.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectedState2Name.setAlignmentX(Component.CENTER_ALIGNMENT);

		submitTurnButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		submitTurnButton.setPreferredSize((new Dimension(200,40)));
		submitTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		submitTurnButton.addActionListener(new SubmitListener());


		unitSlider = new JSlider(0, 10, 0);
		unitSlider.setPreferredSize((new Dimension(200,400)));
		unitSlider.setBackground(Color.DARK_GRAY);
		unitSlider.setForeground(Color.WHITE);


		this.add(Box.createVerticalStrut(5));
		this.add(selectedStateName);
		this.add(Box.createVerticalStrut(40));
		this.add(selectedState2Name);
		this.add(Box.createVerticalStrut(40));
		this.add(unitSlider);
		this.add(Box.createVerticalStrut(20));
		this.add(submitTurnButton, CENTER_ALIGNMENT);

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
