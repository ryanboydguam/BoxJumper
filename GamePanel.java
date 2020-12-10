package boxJumperProgram;

import java.awt.Graphics;

import javax.swing.JPanel;
//Allows you to control the panel thats being painted
public class GamePanel extends JPanel {

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		BoxJumper.boxJumper.repaint(g);
	}
}

