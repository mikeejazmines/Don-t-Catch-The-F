import java.awt.*;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * This class creates a Facebook object that implements the Extra class.
 * This Facebook object increases the stressbar once it collides with the human.
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class Facebook implements Extra {
	private int x, y, width, height;
	private int fallSpeed, value;
	Random rand = new Random(); // creates random number 	
	private StressBar sb;
	private CircleTest c;
	private ImageIcon face; 
	private MainFrame mf;
	protected boolean isHit;
	
	/**
	 * Constructor for the class which accepts a reference to the StressBar and CircleTest.
	 * @param b A reference to the StressBar
	 * @param ct A reference to the CircleTest
	 */
	public Facebook(StressBar b, CircleTest ct)  {
		generateRandomXLocation();
		generateRandomFallSpeed();
		y=120;
		width=60;
		height=60;
		sb = b;
		c = ct;
		isHit = false;
		
	 }
	

	/**
	 * Constructor for the class which accepts a reference to the StressBar, CircleTest, XCoord, FallSpeed, and Value.
	 * 
	 * @param b StressBar
	 * @param ct CircleTest
	 * @param num X Coordinate
	 * @param speed Fall Speed
	 * @param v Value
	 */
	public Facebook(StressBar b, CircleTest ct, int num, int speed, int v)  {
		x = num;
		value = v;
		fallSpeed = speed;
		y=120;
		width=60;
		height=60;
		sb = b;
		c = ct;
		isHit = false;
		
	 }
	
	/**
	 * This method returns the integer value of value.
	 * @return value
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * This method generates a random X location to be used by the class.
	 */
	public void generateRandomXLocation(){
		x= rand.nextInt(420);
	}
	
	/**
	 * This method draws the icon of Facebook.
	 * @param g2d Accepts a Graphics2D parameter
	 */
	public void draw(Graphics2D g2d){
			face = new ImageIcon ("fb_ver2.png");
			face.paintIcon(mf,g2d,x,y);
	}
	
	/**
	 * This method increases the stressbar.
	 */
	public void effect(){
		sb.upStress();
	}
	
	/**
	 * This method creates the downward movement of the class.
	 * If the object hits the person or the floor, isHit is turned into true and 
	 * is then removed from its respective array list found in the Game/NetGame Class.
	 */
	public void animate(){
		if (y+height<650){
			y += fallSpeed;
		}
		else if (y + height >= 650){
			generateRandomXLocation();
			y=101;
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
	 * This method checks whether the object has collided with the CircleTest
	 * @return isHit
	 */
	public boolean getHit(){
		return isHit;
	}
	
	/**
	 * This method returns the x coordinate of the class.
	 * @return x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * This method returns the y coordinate of the class.
	 * @return y
	 */
	public int getY(){
		return y; 
	}
	
	/**
	 * This method generates a random fall speed to allow 
	 * the objects to fall at various speeds. 
	 */
	public void generateRandomFallSpeed(){
		fallSpeed= rand.ints(30,10,50).findFirst().getAsInt();
	}
}
