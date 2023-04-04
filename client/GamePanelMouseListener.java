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
		else if(e.getButton() == MouseEvent.BUTTON2)
		{
			//Deselect current state
			GamePanel.selectedState = null;
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}

}
