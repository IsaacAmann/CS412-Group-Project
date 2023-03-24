import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

public class GamePanel extends JPanel
{
	
	public void paint(Graphics g)
	{
		//Have to call this first, resets the panel size otherwise
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		
		Rectangle2D rectangle = new Rectangle2D.Float(40,40,20,20);
		g2D.draw(rectangle);
	}
}
