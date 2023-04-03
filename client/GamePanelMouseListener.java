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
		GamePanel.checkStateMouse(e.getX(), e.getY());
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}

}
