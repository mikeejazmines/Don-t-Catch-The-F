import java.awt.*;
import java.awt.geom.*;
import javax.swing.ImageIcon;
/**
 * This class creates the human player in the game. If it is single player mode it will show Stu Dante if it is in networking mode it will assign 
 * the first person Stu Dante and the second player Iggy. 
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class CircleTest {
	  private int x, y, speed;
	  private ImageIcon bataBoy; 
	  private MainFrame mf; 
	  private int player; 
	  /**
		 * Constructor of the CircleTest class  that accepts MainFrame and x and y components
		 * it also sets the speed of the player to 15
		 * 
		 */
	  public CircleTest(int x, int y, MainFrame f)  {
	    this.x = x;
	    this.y = y;
	    mf = f;
	    speed = 15; 
	  }
	  /**
		 * This method draws the Student based on the player id. If in networking mode the player who connects first will be Stud Dante while the 
		 * second player will be Iggy 
		 * 
		 */
	  public void draw(Graphics2D g2d){
	    AffineTransform trans = g2d.getTransform();
	    if (player ==1){
	    bataBoy = new ImageIcon ("studentboy1_game.png");
	    bataBoy.paintIcon(mf,g2d,x,y);
	    }
	    else if  (player ==2){
	     bataBoy = new ImageIcon ("girltrial.png");
	 	 bataBoy.paintIcon(mf,g2d,x,y);
	    }
	    else{
		    bataBoy = new ImageIcon ("studentboy1_game.png");
		    bataBoy.paintIcon(mf,g2d,x,y);
		    }
	    
	    g2d.setTransform(trans);	  
	  }
	  
	  /**
		 * This method sets the player id once connected to the network 
		 * 
		 */
	  public void setPlayer(int a){
		  player =a;
	  }
	  /**
		 * This method returns the x value of the CircleTest
		 * 
		 */
	  public int getX(){
		  return x;
	  }
	  /**
		 * This method returns the y value of the CircleTest
		 * 
		 */
	  public int getY(){
		  return y;
	  }
	  
	  /**
		 * This method returns the height value of the CircleTest
		 * which is 96
		 * 
		 */
	  public int getHeight(){
		  return 96;
	  }
	  
	  /**
		 * This method returns the width  value of the CircleTest
		 * which is 96
		 * 
		 */
	  public int getWidth(){
		  return 96;
	  }
	  /**
		 * This method makes sure that when the person goes left it doesnt exceed the screen 
		 * 
		 */
	  public void left(){
		  if(x > 0){
		  x-=speed;
		  }
	  }
	  /**
		 * This method makes sure that when the person goes right it doesnt exceed the screen 
		 * 
		 */
	  public void right(){
		  if(x + bataBoy.getIconWidth() < 500){
		  x+=speed;
		  }
	  }
	  /**
	   * This method increases the speed of the CircleTest to 30 
	   */
	  public void increaseSpeed(){
		  speed = 30;
	  }
	  /**
	   * This method returns the CircleTest to normal speed
	   * 
	   */
	  public void regularSpeed(){
		  speed = 15;
	  }
	  /**
	   * This method changes the value of X depending on what int was inputed. 
	   * 
	   */
	  public void setX(int b){
		  x=b;
	  }

}
