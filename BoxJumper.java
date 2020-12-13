package boxJumperProgram2;

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
import javax.swing.Timer;
import javax.swing.*; 
import java.awt.*;

public class BoxJumper implements ActionListener, MouseListener,KeyListener {
	//name of the png used for the bricks
	String fileName="brick.png";
	//gets the user system name.
	String fileDirectory = System.getProperty("user.home");
	//saves the username 
	public String username = "";
	//the main object for the box jumper program
	public static BoxJumper boxJumper;
	//creates the login panel object
	
	//Constructor
	public GamePanel gamePanel;
	//the object for the player information.
	public Rectangle player;
	//the array for the boxes in the game. 
	public ArrayList<Rectangle> boxes;
	//creates a random number that generated to seprate the boxes
	public Random randomNumGen;
	//game frames per second
	public int gameFPS;
	//modifys the player object on the y axis for up and down movement
	public int yAxisModifer;
	//true or false statement if the game is over or not
	public boolean gameOver;
	//true or false statement if the game has started or not
	public boolean gameStart;
	//var that holds the score
	public int score;
	//if the person has logged in or not
	public boolean login;
	//import image
	ImageIcon im = new ImageIcon(fileName = fileDirectory + "\\Desktop\\"+fileName);  
	Image i = im.getImage();  
	//GRAPHIC SIZE CONTROLER
	//This is the data for the object sizes.
	public int[] gameObjectSize = new int[] {500-120,120,20,500/2-10,0};
	
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
		//adds a mouse listener to the jframe
		jframe.addMouseListener(this);
		//add a key listener to the jframe
		jframe.addKeyListener(this);
		//creates a timer.
		Timer timer = new Timer(20, this);
		//creates a new gamepanel object
		gamePanel = new GamePanel();
		//addes the game panel to the main object
		jframe.add(gamePanel);
		//creates the player object
		player = new Rectangle(100,361, gameObjectSize[2], gameObjectSize[2]);
		//creates a array list that holds all the boxes
		boxes = new ArrayList<Rectangle>();
		//adds 1 box to the beginning
		addColumn(true);
		//starts the timer
		timer.start();
	}
	//the main that start the program
	public static void main(String[] args) {
		boxJumper = new BoxJumper();
	}
	//adds a new box
	public void addColumn(boolean start) {
		//creates a random number for the spacing of the boxes
		double boxSpacerDouble =Math.random();
		boxSpacerDouble *= 500;
		int boxSpacer = (int) boxSpacerDouble;
		
		//if the game start it true add a box
		if(start) {
			boxes.add(new Rectangle(500 + 100 +boxes.size()*300+boxSpacer			, 280, 100, 100));
		}else {
			//this is so when the game has already started it will add a new box with the correct spacing
			boxes.add(new Rectangle(boxes.get(boxes.size() - 1).x+600+boxSpacer		, 280, 100, 100));
		}
	}
	//controls the y axis of the player. So when you jump you move up and down
	public void jump() {
		//if the game is over
		if(gameOver) {
			//create anew player object
			player = new Rectangle(100,361, gameObjectSize[2], gameObjectSize[2]);
			//clear the boxes
			boxes.clear();
			//set your modifer to 0
			yAxisModifer =0;
			//set the score to 0
			score = 0;
			//add a new box
			addColumn(true);
			//set game over to false allowing the game to be played again
			gameOver=false;
		}
		if(!gameStart) {
			//if the game has not started set it to true
			gameStart = true;
			//this part controls the player jump settings
			//also when the player.y > 350 that means they are not in the air so they can jump again
		}else if(!gameOver&&player.y>350) {
			if(yAxisModifer >0) {
				yAxisModifer =0;
			}
			//controls the height of the jump
			yAxisModifer = yAxisModifer - 20;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameFPS++;
		int speed =10;
		//if the game has started and is not over
		if(gameStart && !gameOver) {
			//This moves the boxes over
			for(int i = 0; i<boxes.size();i++) {
				Rectangle column = boxes.get(i);
				column.x -= speed;
			}
			//every 30 game frames add 1 to the score
			if(gameFPS%40==0) {
				score++;
			}
			//adds a new box
			addColumn(false);
			//This is the falling motion of the box. So if the box is less than 15px and every other game frame
			if(gameFPS % 2 ==0 && yAxisModifer < 15) {
				//add 2 to the y axis allowing the player to fall
				yAxisModifer= yAxisModifer + 2;
			}
			//sets the players y axis to the yAxis
			player.y =player.y + yAxisModifer;
			//this allows it so the player can sit on the ground without falling through the floor
			if(player.y>=361) {
				player.y =361;
			}
			//this checks every game frame if the box has run into the player
			for(Rectangle column : boxes) {
				if(column.intersects(player)) {
					//if it is true then the game is over
					gameOver= true;
				}
			}
			//if the game is over put the player on the floor
			if(gameOver) {
				player.y = gameObjectSize[0]-player.height;
			}
			gamePanel.repaint();
		}
	}
	
	///this is used to paint the boxes on the screen.
	public void paintColumn(Graphics g,Rectangle column ) {
		//since you cant set it to brown i made it orange then made it really dark which is brown
		g.setColor(Color.orange.darker().darker().darker());
		//sets the box dimensions on the screen. 
		g.fillRect(column.x, column.y, column.width, column.height);
		// draws the image of the box on the screen
		g.drawImage(i,column.x,column.y,column.width,column.height,null);
	}
	
	
	//prints out the game graphics
	public void repaint(Graphics g) {
		//sets the skybox to yellow
		g.setColor(Color.yellow);
		//sets the size of the skybox and location
		g.fillRect(0, 0, 500, 500);
		//sets the floor to green
		g.setColor(Color.green);
		//sets the size of the floor and location
		g.fillRect(0, gameObjectSize[0], 500, gameObjectSize[1]);
		//sets the player to blue
		g.setColor(Color.blue);
		//sets the size of the player and location
		g.fillRect(player.x, player.y, player.width, player.height);
		//paints all the boxes onto the screen
		for(Rectangle column: boxes) {
			paintColumn(g, column);
		}
		//if the game is over
		if(gameOver) {
			//creates the blue panel at the end of the game
			g.setColor(Color.blue.darker());
			//sets the size and location of the panel at the end of the game
			g.fillRect(500/4, 500/4, 500/4*2, 500/4*2);
			//displays the players score at the end of the game
			g.setColor(Color.white);
			//sets the text to arial with a font of 40
			g.setFont(new Font("Arial", 1,40));
			//displays the score with calling the score int
			g.drawString("SCORE:" + score, 500/4+10, 500/4+50);
			//sets the score to 0
			score = 0;
		}
		//of the game is not over
		if(!gameOver) {
			//sets the color to white
			g.setColor(Color.white);
			//sets the font to arial and size to 30
			g.setFont(new Font("Arial", 1,30));
			//draws the score in the top right hand corner
			g.drawString(String.valueOf(score), 400, 40);
		}
		//if the game is not over and the score is less than or equal to zero
		if(!gameOver&&score<=0) {
			//this draws the click to start on the screen so they player knows how to start the game
			g.setColor(Color.white);
			g.setFont(new Font("Arial", 1,30));
			g.drawString("Click To Start", 10, 30);
		}
	}
//mouse listener for the player to jump
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//if there is a mouse event the player can jump
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	//key listener for the player to jump
	@Override
	public void keyPressed(KeyEvent arg0) {
		//if there is a key event the player can jump
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
