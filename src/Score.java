import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


/**
 * This class creates a Score object that shows the current score of the player
 * 
 * @author Mikee Jazmines, Alyssa Ty
 * @version May 18, 2016
 */
public class Score {
	private int currentScore;
	private String curStringScore;
	private int x;
	
	/**
	 * Constructor of the class. 
	 * Instantiates currentScore and the position of the object.
	 */
	public Score(){
		currentScore=0;
		curStringScore= Integer.toString(currentScore);
		x=70;
	}
	
	/**
	 * This method displays the current score at the top left portion of the screen.
	 * The change of X makes sure that the score is aligned.
	 * 
	 * @param g2d Graphics2D object
	 */
	public void draw(Graphics2D g2d){
		curStringScore= Integer.toString(currentScore);
			if (currentScore>=10000){
				x=30;
			}
			else if (currentScore>=1000) {
				x=40;
			}
			else if (currentScore>=100){
				x=50;
			}
			else if (currentScore>=10){
				x=60;
			}

		Font fnt2 = new Font("arial", Font.PLAIN, 30 );
		g2d.setFont(fnt2);
		g2d.setColor(Color.BLACK);
		
		g2d.drawString(curStringScore, x, 70);
		Font fnt3 = new Font("arial", Font.PLAIN, 20 );
		g2d.setFont(fnt3);
		g2d.drawString("Your Score", x-20, 40);
	}
		
	/**
	 * This method returns the current score in a String
	 * 
	 * @return curStringScore
	 */
	public String getStringScore() {
		return curStringScore;
	}
	
	/**
	 * This method returns the current score as an Integer.
	 * @return currentScore
	 */
	public int getIntScore() {
		return currentScore;
	}
	
	/**
	 * This method increases the score.
	 */
	public void upScore(){
		currentScore+=50;
	}
	
	/**
	 * This method decreases the score.
	 */
	public void downScore(){
		currentScore-=30;
	}
	
	/**
	 * This method makes the score equal to zero.
	 */
	public void restartScore(){
		currentScore = 0;
	}
}

