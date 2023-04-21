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
		unitSlider.setPaintTicks(true);
		unitSlider.setPaintLabels(true);
		this.add(selectedStateName);
		this.add(selectedState2Name);
		this.add(unitSlider);
		this.add(moveUnitsButton);
		this.add(submitTurnButton);
		this.setVisible(true);
	}
	//Update information in the panel when the selected state changes
	public void setSelectedState(State state)
	{
		selectedStateName.setText("Selected State: " + state.name);
		unitSlider.setMaximum(state.units);
		unitSlider.setMajorTickSpacing(state.units / 2);
		
	}
	//Update information in the panel when the target state changes
	public void setSelectedState2(State state)
	{
		selectedState2Name.setText("Target State: " + state.name);
	}
	
	public void deselect()
	{
		selectedStateName.setText("Selected State: ");
		selectedState2Name.setText("Target State: ");
	}
	
	//Action listener for the submit turn button
	private class SubmitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(GamePanel.gameState.currentPlayerID == Client.playerID)
			{
				try
				{
					Client.server.postTurn(GamePanel.currentGameStateUpdate, Client.playerID);
					
					//Increment the currentPlayerID to prevent double sending the turn update and allow
					//Client to accept a new game state, value does not matter, server will replace it with new gamestate
					GamePanel.gameState.currentPlayerID++;
				}
				catch(RemoteException exception)
				{
					exception.printStackTrace();
				}
			
				GamePanel.currentGameStateUpdate = null;
			}
		}
	}
	
	private class MoveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(GamePanel.selectedState != null && GamePanel.selectedState2 != null)
			{
				int sourceStateID = GamePanel.selectedState.stateID;
				int destinationStateID = GamePanel.selectedState2.stateID;
				int senderPlayerID = Client.playerID;
				int units = unitSlider.getValue();
				
				//Check if move is valid
				if(GamePanel.gameState.states.get(sourceStateID).numberUnits >= units)
				{
					//Creating a local game state update to merge a move in real time as they are made client side
					GameStateUpdate tempUpdate = new GameStateUpdate();
					tempUpdate.addMove(units, sourceStateID, destinationStateID, senderPlayerID);
					GamePanel.gameState.mergeGameStateUpdate(tempUpdate);
					
					GamePanel.currentGameStateUpdate.addMove(units, sourceStateID, destinationStateID, senderPlayerID);
					
					//Update slider min / max
					unitSlider.setMaximum(GamePanel.gameState.states.get(sourceStateID).numberUnits);
					unitSlider.setValue(0);
				}
			}
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
