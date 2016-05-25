import java.awt.color.*;
import java.awt.geom.Rectangle2D;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This class in charge of the drawing of the buttons of the menu screen
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */

public class MenuScreen {
	public static Rectangle2D.Double tempBackground = new Rectangle2D.Double(0, 0, MainFrame.WINDOW_WIDTH, MainFrame.WINDOW_HEIGHT);
	public static Rectangle2D.Double playButton = new Rectangle2D.Double(190,420,120,40);
	public static Rectangle2D.Double instructionsButton = new Rectangle2D.Double(130,470,250,40);
	public static Rectangle2D.Double networkButton = new Rectangle2D.Double(160,520,180,40);
	
	public MenuScreen(){
	}
	/**
	 * The method draws the play, instruction and network button and it writes the text indicated which buttons do what action
	 */
	public void draw(Graphics2D g2d){
		
		Font fnt2 = new Font("arial", Font.PLAIN, 30);
		g2d.setFont(fnt2);
		g2d.drawString("PLAY",215, 450);
		g2d.draw(playButton);
		g2d.drawString("INSTRUCTIONS",145, 500);
		g2d.draw(instructionsButton);
		g2d.drawString("NETWORK",175, 550);
		g2d.draw(networkButton);
	}
}