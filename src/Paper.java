import java.awt.*;
import java.util.Random;

/**
* This class animates the A and the F papers falling. They are also in charge of the collision detection of the papers, when it hits the the player (CircleTest)
* This class is also in charge of spawning the papers on random x positions within the frame of the game. 
* It is also in charge of the random speeds of the Papers falling. 
* 
* @author Mikee Jazmines & Alyssa Ty
* @version May 18, 2016
*/
public class Paper {

	private Score s;
	protected int x;
	protected int y;
	private int width;
	private int height;
	private static int fallSpeed; 
	Random rand = new Random();
	private CircleTest c;
	protected int value;
	
	protected boolean isHit;
	
	public Paper(){
		
	}
	/**
	 * Constructor for the class which accepts a reference to the Score and CircleTest.
	 * @param b A reference to the Score
	 * @param ct A reference to the CircleTest (aka the human)
	 * It also calls the methods generateRandXLocation and generateRandomFallSpeed which is in charge of the random speed and position of the falling papers
	 * It sets the y value of where the papers will start to fall
	 * It also sets the width and the height of the papers(to be used when making the collision detection method)
	 * It also sets the boolean isHit to false, since the papers have not hit the player yet 
	 */
	public Paper(Score sc, CircleTest ct){
		s = sc;
		c = ct;
		generateRandomXLocation();
		generateRandomFallSpeed();
		y = 100;
		width = 50;
		height = 50;
		isHit = false;
	}
	
	public Paper(Score sc, CircleTest ct, int xCoord, int fall, int v){
		s = sc;
		c = ct;
		x = xCoord;
		value = v;
		fallSpeed = fall;
		y = 100;
		width = 50;
		height = 50;
		isHit = false;
	}

	/**
	 * This method is in charge of the collision detection of the papers when it hist the CircleTest aka the human. 
	 * It is also in charge of making sure that the papers stay inside the screen of the game. 
	 * It checks when the paper hits the left, right and top side of the player.
	 */
	public void animate(){
		if (y + height < 650){
			y += fallSpeed * MainFrame.multiplier;
		}
		else if (y + height >= 650){
			y=100;
			isHit = true;
		}
		//left side of the human collision detection
		if(y + height > c.getY() && (y + height) < (c.getY() + 50) && x + width > c.getX() && x + width < c.getX() + c.getWidth()){
			updateScore();
			y = 101;
			isHit = true;
		}
		//right side of the human collision detection 
		else if(y + height > c.getY() && (y + height) < (c.getY() + 50) && x < c.getX() + c.getWidth() && x > c.getX()){
			updateScore();
			y = 101;
			isHit = true;
		}
	}
	/**
	 * This method returns of the boolean getHit when called
	 */
	public boolean getHit(){
		return isHit;
	}
	/**
	 * This method generates a random x location for our papers making sure that x values given are inside the frame. 
	 */
	public void generateRandomXLocation(){
		x= rand.nextInt(420);
	}
	/**
	 * This method generates a random speed for the falling papers
	 */
	public void generateRandomFallSpeed(){
		fallSpeed= rand.ints(30,10,50).findFirst().getAsInt();
	}
	/**
	 * This  method returns the x value of the paper
	 */
	public int getX(){
		return x;
	}
	/**
	 * This  method returns the y value of the paper
	 */
	public int getY(){
		return y;
	}
	/**
	 * This  method returns the width of the paper
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * This  method returns the width of the paper
	 */
	public int getHeight(){
		return height;
	}
	
	public void updateScore(){
		
	}
	/**
	 * This  method returns the score
	 */
	public Score getScore(){
		return s;
	}
	
	public int getValue(){
		return value;
	}
	
	public void draw(Graphics2D g){
		
	}
}
