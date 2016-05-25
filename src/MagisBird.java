import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.Graphics2D;


/**
 * This class creates a MagisBird instance that turns all papers into an A Paper.
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class MagisBird implements Extra{
	private int x, y, width, height;
	private int fallSpeed;
	Random rand = new Random(); // creates random number 	
	private CircleTest c;
	private Game g;
	private NetGame ng;
	private int n, value;
	private Timer time;
	
	protected static boolean on;
	protected boolean game, isHit;
	
	private ImageIcon bird; 
	
	/**
	 * Constructor for the class which accepts Game and Circle Test.
	 * 
	 * @param ct A reference to the CircleTest
	 * @param gg A reference to the Game object
	 */
	public MagisBird (CircleTest ct, Game gg)  {
		generateRandomXLocation();
		generateRandomFallSpeed();
		y=120;
		width=50;
		height=65;
		c = ct;
		g = gg;
		n = 0;
		on = false;
		game = true;
		isHit = false;
		
		// Timer for the length of the effect of the MagisBird.
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) stopTime();
				else g.everythingA();
		      }
		    };
	    time = new Timer(1000, al);
	 }
	
	public MagisBird (CircleTest ct, NetGame gg, int num, int speed, int v)  {
		x = num;
		fallSpeed = speed;
		y=120;
		width=50;
		height=65;
		c = ct;
		ng = gg;
		n = 0;
		value = v;
		on = false;
		game = false;
		isHit = false;
		
		// Timer for the length of the effect of the MagisBird.
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) stopTime();
				else ng.everythingA();
		      }
		    };
	    time = new Timer(1000, al);
	 }
	
	
	/**
	 * Constructor for the class which accepts NetGame and Circle Test.
	 * 
	 * @param ct A reference to the CircleTest
	 * @param gg A reference to the NetGame object
	 */
	public MagisBird (CircleTest ct, NetGame gg)  {
		generateRandomXLocation();
		generateRandomFallSpeed();
		y=120;
		width=50;
		height=65;
		c = ct;
		ng = gg;
		n = 0;
		on = false;
		game = false;
		
		
		// Timer for the length of the effect of the MagisBird.
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) stopTime();
				else {
					if (game) g.everythingA();
					else ng.everythingA();
				}
		      }
		    };
	    time = new Timer(1000, al);
	 }
	
	/**
	 * This method returns the integer value of value.
	 * @return value
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * This method activates the effect of the MagisBird, and transforms all papers into A's
	 */
	public void effect(){
		time.start();
	}
	
	/**
	 * This method generates a random X location to be used by the coffee.
	 */
	public void generateRandomXLocation(){
		x= rand.nextInt(420);
	}
	
	/**
	 * 
	 * This method draws the icon of the MagisBird.
	 * 
	 * @param g2d Accepts a Graphics2D parameter
	 */
	public void draw(Graphics2D g2d){
			bird = new ImageIcon ("ver4bird.png");
			bird.paintIcon(g, g2d, x, y);
	}
	
	/**
	 * This method creates the downward movement of the class.
	 */
	public void animate(){
		if (y+height<650){
			y += fallSpeed;
		}
		else if (y + height >= 650){
			y=101;
			isHit = true;
		}
		// left side 
		if(y + height > c.getY() && x + width > c.getX() && x + width < c.getX() + c.getWidth()){
			effect();
			on = true;
			y = 101;
			isHit = true;
			
			
			if(StressBar.on == true){
				if(game) g.stressOff();
				else ng.stressOff();
			}
		}
		//right
		else if(y + height > c.getY() && x < c.getX() + c.getWidth() && x > c.getX()){
			effect();
			on = true;
			y = 101;
			isHit = true;
			
			if(StressBar.on == true){
				if(game) g.stressOff();
				else ng.stressOff();
			}
		}
	}
	
	/**
	 * This method checks whether the object has collided with the CircleTest
	 * @return isHit
	 */
	public boolean getHit(){
		return isHit;
	}
	
	
	/**
	 * This method stops the timer of the MagisBird and turns all the papers back to normal.
	 */
	public void stopTime(){
		time.stop();
		n = 0;
		if(game) g.normalPapers();
		else ng.normalPapers();
		on = false;
	}
	
	/**
	 * @return x Returns the x coordinate of the object
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * @return y Returns the y coordinate of the object
	 */
	public int getY(){
		return y; 
	}
	
	/**
	 * This method moves the MagisBird object to the side, to be hid until needed. 
	 */
	public void moveMe(){
		x = -100;
	}
	
	/**
	 * This method generates a random fall speed to allow 
	 * the objects to fall at various speeds. 
	 */
	public void generateRandomFallSpeed(){
		fallSpeed= rand.ints(1,1,50).findFirst().getAsInt();
	}
	
	/**
	 * This method generates a faster random fall speed to allow 
	 * the objects to fall at faster speeds. 
	 */
	public void generateFastFallSpeed(){
		fallSpeed= rand.ints(20,40,100).findFirst().getAsInt();
	}
}


