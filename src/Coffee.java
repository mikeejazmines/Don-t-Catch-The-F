import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


/**
 * A class that creates a Coffee Icon that boosts the human's speed.
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 *
 */
public class Coffee implements Extra{
	private int x, y, width, height;
	private int fallSpeed, value;
	private int n; // Timer for the length of the effect of the coffee.
	
	Random rand = new Random(); // creates random number 	
	
	private CircleTest c; // Reference to the Human
	private MainFrame mf; 
	
	private Timer time;
	private ImageIcon joe; 
	
	protected boolean isHit;
	
	
	/**
	 * Constructor for the class which accepts MainFrame and Circle Test.
	 * 
	 * @param ct A reference to the CircleTest
	 * @param f A reference to the MainFrame
	 */
	public Coffee (CircleTest ct, MainFrame f)  {
		generateRandomXLocation();
		generateRandomFallSpeed();
		y=120;
		width=50;
		height=65;
		c = ct;
		mf = f;
		n = 0;
		isHit = false;
		
		
		// Timer for the length of the effect of the coffee.		
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) effect();
		      }
		    };
	    time = new Timer(1000, al);
	 }
	
	/**
	 * Constructor for the class which accepts MainFrame and Circle Test, x coordinate, fallspeed, and a value.
	 * @param ct CircleTest
	 * @param f MainFrame
	 * @param x XValue
	 * @param fall Fall Speed
	 * @param v Value
	 */
	public Coffee (CircleTest ct, MainFrame f, int num, int speed, int v)  {
		x = num;
		value = v;
		fallSpeed = speed;
		y=120;
		width=50;
		height=65;
		c = ct;
		mf = f;
		n = 0;
		isHit = false;
		
		
		// Timer for the length of the effect of the coffee.		
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) effect();
		      }
		    };
	    time = new Timer(1000, al);
	 }
	
	/**
	 * This method generates a random X location to be used by the coffee. 
	 */
	public void generateRandomXLocation(){
		x= rand.nextInt(420);
		
	}
	
	/**
	 * This method returns the integer value of value.
	 * @return value
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * 
	 * This method draws the icon of the coffee.
	 * 
	 * @param g2d Accepts a Graphics2D parameter
	 */
	public void draw(Graphics2D g2d){
			joe = new ImageIcon ("Coffee_ver1.png");
			joe.paintIcon(mf,g2d,x,y);
	}
	
	/**
	 * This method affects the speed of the CircleTest for a limited amount of seconds
	 * and starts the timer when called.
	 */
	public void effect(){
		if(n < 10){
			c.increaseSpeed();
			time.start();
		}
		else{
			c.regularSpeed();
			n = 0;
			time.stop();
		}
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
			generateRandomXLocation();
			isHit = true;
		}
		// left side 
		if(y + height > c.getY() && x + width > c.getX() && x + width < c.getX() + c.getWidth()){
			effect();
			y = 101;
			generateRandomXLocation();
			isHit = true;
		}
		//right
		else if(y + height > c.getY() && x < c.getX() + c.getWidth() && x > c.getX()){
			effect();
			y = 101;
			generateRandomXLocation();
			isHit = true;
		}
	}
	
	/**
	 * @return x Returns the x coordinate of the coffee object
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * @return y Returns the y coordinate of the coffee object
	 */
	public int getY(){
		return y; 
	}
	
	/**
	 * This method checks whether the object has collided with the CircleTest
	 * @return isHit
	 */
	public boolean getHit(){
		return isHit;
	}
	
	/**
	 * This method generates a random fall speed to allow 
	 * the objects to fall at various speeds. 
	 */
	public void generateRandomFallSpeed(){
		fallSpeed= rand.ints(1,1,50).findFirst().getAsInt();
	}
}


