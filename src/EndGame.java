import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import java.awt.Font;

/**
 * This class shows the endGame screen after the timer has finished. 
 * Depending on the score range it shows different messages on the screen and has a button that bring the person back to the main menu
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class EndGame {
	private Score fs;
	private ImageIcon endScreen;
	private MainFrame mf;
	private NetGame ng; 
	private boolean isGame;
	
	/**
	 * Constructor of the class. 
	 * Instantiates the MainFrame and the Score class
	 */
	public EndGame (MainFrame f, Score s){
		fs =s;
		mf = f;
		isGame = true;
	}
	
	public EndGame (MainFrame f, Score s, NetGame g){
		fs = s;
		mf = f;
		isGame = false;
		ng = g;
	}
	
	/**
	 * This method draws the components and shows the score taken from the score class on the game screen
	 * it shows different messages depending on the score range.
	 */
	public void draw(Graphics2D g2d){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		endScreen = new ImageIcon("EndScreen_ver1.png");
		endScreen.paintIcon(mf, g2d, 0, 0);
		Font fnt1 = new Font("arial", Font.PLAIN, 40);
		g2d.setFont(fnt1);
		g2d.drawString(fs.getStringScore(), 150, 235);
	
		if(isGame){
			int finalScore = fs.getIntScore();
			if(finalScore <= 0){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Sorry", 300, 175);	
				g2d.drawString("You're kicked", 260, 210);
				g2d.drawString("out!", 310, 240);
			}
			
			else if (0 < finalScore && finalScore <= 150){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Congratulations", 245, 175);	
				g2d.drawString("You passed!", 260, 210);
			}
			
			else if (150 <= finalScore && finalScore <= 300){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Congratulations", 245, 175);	
				g2d.drawString("You're a", 290, 210);
				g2d.drawString("Dean's Lister!", 260, 240);
			}
			
			else if (300 <= finalScore && finalScore <= 450){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Congratulations", 245, 175);	
				g2d.drawString("You are", 290, 210);
				g2d.drawString("Cum Laude!", 265, 240);
			}
			
			else if (450 <= finalScore && finalScore <= 600){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Congratulations", 245, 175);	
				g2d.drawString("You are Magna", 250, 210);
				g2d.drawString("Cum Laude!", 265, 240);
			}
			else if (finalScore > 600){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Congratulations", 245, 175);	
				g2d.drawString("You are Summa", 250, 210);
				g2d.drawString("Cum Laude!", 265, 240);
			}
		}
		else{
			if(ng.getEnemyScore() > ng.getScore()){
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Sorry", 300, 175);	
				g2d.drawString("You are just", 260, 210);
				g2d.drawString("Salutatorian!", 265, 240);
			}
			else{
				Font fnt2 = new Font ("arial", Font.BOLD,23);
				g2d.setFont(fnt2);
				g2d.drawString("Congratulations", 245, 175);	
				g2d.drawString("You are", 290, 210);
				g2d.drawString("Valedictorian!", 265, 240);
			}
		}
		
		Font button = new Font ("arial", Font.BOLD,20);
		g2d.setFont(button);
		g2d.drawString("PRESS BUTTON", 85, 395);
		g2d.drawString("TO EXIT", 285, 395);
	}
}
