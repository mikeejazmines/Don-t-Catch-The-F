import java.awt.*;
import javax.swing.ImageIcon;

/**
 * This class creates the F papers that fall in the game. They decrease the score of the player.
 * It also extends the Paper class. 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class FPaper extends Paper {

	private ImageIcon paper; 
	private MainFrame mf; 
	private boolean isF;
	public FPaper(){}
	private int value;
	
	/**
	 * Constructor for the class which accepts Score and Circle Test.
	 * 
	 * @param ct A reference to the CircleTest
	 * @param sc for score.
	 * It also has a boolean that checks if the papers turns into F (when stress mode is activated)
	 */
	public FPaper(Score sc, CircleTest ct){
		super(sc, ct);
		isF = true;
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
	public FPaper(Score sc, CircleTest ct, int x, int fall, int v){
		super(sc, ct, x, fall, v);
		isF = true;
	}
	
	/**
	 * This method changes the images based on if the stress bar is filled. If the isF is false then
	 * it shows a picture of an A paper, otherwise it shows the F paper
	 */
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
	 *this method makes isF true meaning the papers are F papers. The papers should be back to normal state.
	 */
	public void makeF(){
		isF = true;
	}
	
	/**
	 * This method makes isF boolean false (meaning the F papers should turn into A papers)
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
