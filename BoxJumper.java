package boxJumperProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoxJumper implements ActionListener, MouseListener,KeyListener {
	public String username = "";
	public static BoxJumper boxJumper;
	public loginPanel loginframe;
	//Constructor
	public GamePanel gamePanel;
	public Rectangle player;
	public ArrayList<Rectangle> boxes;
	public Random randomNumGen;
	public int gameFPS;
	public int yAxisModifer;
	public boolean gameOver;
	public boolean gameStart;
	public int score;
	public boolean login;
	//GRAPHIC SIZE CONTROLER
	//
	public int[] gameObjectSize = new int[] {500-120,120,20,500/2-10,0};
	public class loginPanel extends JPanel{
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawString("hello", 0, 0);
			
		}
	}
	public BoxJumper() {
		//Creates the frame for the game to run in
		JFrame jframe = new JFrame();
		//Sets the size constraints for the game Frame
		jframe.setSize(500,500);
		//Sets the frame visibility to true. 
		jframe.setVisible(true);
		//Closes the application when you exit it. 
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Stops users from changing the size of the game client/frame.
		jframe.setResizable(true);
		//Sets the frame Title
		jframe.setTitle("FINAL CS380 Project");
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		Timer timer = new Timer(20, this);
		gamePanel = new GamePanel();
		jframe.add(gamePanel);
		randomNumGen = new Random();
		player = new Rectangle(100,361, gameObjectSize[2], gameObjectSize[2]);
		boxes = new ArrayList<Rectangle>();
		addColumn(true);
		timer.start();
	}
	
	public static void main(String[] args) {
		boxJumper = new BoxJumper();
	}
	
	public void addColumn(boolean start) {
		double boxSpacerDouble =Math.random();
		boxSpacerDouble *= 500;
		int boxSpacer = (int) boxSpacerDouble;
		
		System.out.println(boxSpacer);
		if(start) {
			boxes.add(new Rectangle(500 + 100 +boxes.size()*300+boxSpacer			, 280, 100, 100));
		}else {
			
			boxes.add(new Rectangle(boxes.get(boxes.size() - 1).x+600+boxSpacer		, 280, 100, 100));
		}
	}
	public void jump() {
		if(gameOver) {
			player = new Rectangle(100,361, gameObjectSize[2], gameObjectSize[2]);
			boxes.clear();
			yAxisModifer =0;
			score = 0;
			addColumn(true);
			gameOver=false;
		}
		if(!gameStart) {
			gameStart = true;
		}else if(!gameOver&&player.y>350) {
			if(yAxisModifer >0) {
				yAxisModifer =0;
			}
			yAxisModifer = yAxisModifer - 20;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameFPS++;
		int speed =10;
		if(gameStart && !gameOver) {
			for(int i = 0; i<boxes.size();i++) {
				Rectangle column = boxes.get(i);
				column.x -= speed;
			}
			if(gameFPS%40==0) {
				score++;
			}
			addColumn(false);
			if(gameFPS % 2 ==0 && yAxisModifer < 15) {
				yAxisModifer= yAxisModifer + 2;
			}
			player.y =player.y + yAxisModifer;
			if(player.y>=361) {
				player.y =361;
			}
			for(Rectangle column : boxes) {
				if(column.intersects(player)) {
					gameOver= true;
					player.x = column.x - player.width;
				}
			}
			
			if(gameOver) {
				player.y = gameObjectSize[0]-player.height;
			}
			gamePanel.repaint();
		}
	}
	
	
	public void paintColumn(Graphics g,Rectangle column ) {
		g.setColor(Color.orange.darker().darker().darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	
	//prints out the game graphics
	public void repaint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 500, 500);
		
		g.setColor(Color.orange);
		g.fillRect(0, gameObjectSize[0], 500, gameObjectSize[1]);
		
		g.setColor(Color.green);
		g.fillRect(0, gameObjectSize[0], 500, gameObjectSize[2]);
		
		g.setColor(Color.red);
		g.fillRect(player.x, player.y, player.width, player.height);
		
		for(Rectangle column: boxes) {
			paintColumn(g, column);
		}
		
		if(gameOver) {
			g.setColor(Color.blue.darker());
			g.fillRect(500/4, 500/4, 500/4*2, 500/4*2);
			
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1,40));
			g.drawString("SCORE:" + score, 500/4+10, 500/4+50);
			
			g.setColor(Color.blue);
			g.fillRect(500/4+20, 500/4+70, 500/4*2-40, 500/4*2-90);
			
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1,30));
			g.drawString("FirstPlace", 500/4+40, 500/4+100);
			score = 0;
		}
		if(!gameOver) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1,30));
			g.drawString(String.valueOf(score), 400, 40);
		}
		if(!gameOver&&score<=0) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1,30));
			g.drawString("Click To Start", 10, 30);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		jump();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		System.out.println(arg0);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println(arg0);
		
	}
}
