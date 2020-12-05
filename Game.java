import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {

	Random random = new Random();
	public Timer timer;
	int delay = 8;
	
	Rectangle tube = new Rectangle(1370,0,100,100);
	Rectangle tube2= new Rectangle(1370,200,100,1000);
	
	  public Game()
	  {
	    addKeyListener(this);
	    setFocusable(true);
	    setFocusTraversalKeysEnabled(false);
	    timer = new Timer(delay,this);
	    timer.start();
	    
	  }
	
	
	public void paint(Graphics g)
	  {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.cyan); 
	    g.fillRect(1,1,1900,1500);
		
	    g.setColor(Color.green.darker());
	    g2d.fill(tube);
		g2d.fill(tube2);
		
		
		
	  }
	
	 public void actionPerformed(ActionEvent e)
	  {
		 
		
		 int num = random.nextInt(400)+50;
		 System.out.println(num);
		 
		 
		 tube.x-= 10;
		 tube2.x-=10;
		  
		 if(tube.x  < -100)
		  {
			  int i = 10;
			  tube.x = 1370;
			  tube2.x =1370;
			  do {
				  tube.height= num;
				  tube2.y =num +100;
				 
				  
			  } while(i<0);
		  }
		 
		 
		 
		 
		 
		 
		 
		 repaint();
	  }
	 
	 
	 public void keyTyped(KeyEvent e)
	  {
	    
	  }
	  
	 
	 public void keyPressed(KeyEvent e)
	  {
	    
	    
	  }
	  
	  public void keyReleased(KeyEvent e)
	  {
	     
	  }
}
