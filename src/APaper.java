import java.awt.*;
import javax.swing.ImageIcon;

/**
 * This class creates the A papers that fall in the game. They increase the score of the player.
 * It also extends the Paper class. 
 *
 * @author Mikee Jazmines, Alyssa Ty
 * @version May 18, 2016
 */
public class APaper extends Paper {
	
	private MainFrame mf; 
	private ImageIcon paper; 
	private boolean isF;
	public APaper(){}
	private int value;
	
	/**
	 * Constructor for the class which accepts Score and Circle Test.
	 * 
	 * @param ct A reference to the CircleTest
	 * @param sc for score.
	 * It also has a boolean that checks if the papers turns into F (when stress mode is activated)
	 */
	public APaper(Score sc, CircleTest ct){
		super(sc, ct);
		isF = false;
	}
	
	/**
	 * 
	 * Constructor for the class which accepts Score and Circle Test, x coordinate, fallspeed, and a value.
	 * @param sc score
	 * @param ct CircleTest
	 * @param x XValue
	 * @param fall Fall Speed
	 * @param v Value
	 */
	public APaper(Score sc, CircleTest ct, int x, int fall, int v){
		super(sc, ct, x, fall, v);
		isF = false;
	}
	
	/**
	 * This method changes the images based on if the stress bar is filled. If the isF is false then
	 * it shows a picture of an A paper, otherwise it shows the F paper
	 */
	//Draws either an A or an F depending if F is true or false. 
	public void draw(Graphics2D g2d){
		if(!isF){
		paper= new ImageIcon ("APaper_ver2.png");
		paper.paintIcon(mf, g2d, x, y);
		}
		else{
		paper = new ImageIcon ("FPaper_ver2.png");
		paper.paintIcon(mf, g2d, x, y);
		}
	} 
	
	/**
	 * This method makes isF boolean true (meaning the A papers should turn into F papers)
	 */
	public void makeF(){
		isF = true;
	}
	
	/**
	 *this method makes isF false meaning the papers are A papers. The papers should be back to normal state
	 */
	public void noF(){
		isF = false;
	}
	
	/**
	 * This changes the score of the player depending on isF boolean
	 * if the isF is false, it means it shows the A paper thus that score should increase
	 * otherwise it should show the F papers which sound decrease the grade
	 * the methods in charge of changing the score are found in the Score method
	 */
	public void updateScore(){
		if(!isF){
			getScore().upScore();
		}
		else{
			getScore().downScore();
		}
	}
}
