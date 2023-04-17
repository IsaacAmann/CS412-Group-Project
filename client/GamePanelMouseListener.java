import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GamePanelMouseListener implements MouseListener
{
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		//System.out.println(e.getX() + "  " + e.getY());
		//detect a left click
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			GamePanel.checkStateMouse(e.getX(), e.getY());
		}
		//detect right click
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			//Deselect current states
			GamePanel.selectedState = null;
			GamePanel.selectedState2 = null;
			System.out.println("right");
			//update StateSelectionPanel labels
			Client.stateSelectionPanel.getSelectedStateLabel().setText("Selected State: ");
			Client.stateSelectionPanel.getSelectedStateLabel2().setText("Target State: ");
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}

}
