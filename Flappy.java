import javax.swing.JFrame;

public class Flappy {
	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Game gamePlay = new Game();
	    
	    obj.setBounds(0,0,1370,870);
	    obj.setTitle("Flappy Test");
	    obj.setResizable(true);
	    obj.setVisible(true);
	    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    obj.add(gamePlay);
	    
	    
	    
	    //
	}
}
