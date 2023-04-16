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
	JLabel selectedState2Name;
	JButton submitTurnButton;
	JButton moveUnitsButton;
	//Use JSlider for selecting units to move
	JSlider unitSlider;
	
	
	public StateSelectionPanel()
	{
		super();
		this.setBackground(Color.BLUE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		selectedStateName = new JLabel("Selected State: ");
		submitTurnButton = new JButton("End Turn");
		moveUnitsButton = new JButton("Move Units");
		selectedState2Name = new JLabel("Target State: ");
		submitTurnButton.addActionListener(new SubmitListener());
		moveUnitsButton.addActionListener(new MoveListener());
		unitSlider = new JSlider(0, 10, 0);
		this.add(selectedStateName);
		this.add(selectedState2Name);
		this.add(unitSlider);
		this.add(moveUnitsButton);
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
	
	private class MoveListener implements ActionListener
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
