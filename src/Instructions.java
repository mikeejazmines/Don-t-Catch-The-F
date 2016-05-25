import java.awt.*;
import javax.swing.*;

import javax.swing.ImageIcon;
/**
* This class is in charge of the instruction screen which appears when the instruction button is clicked.
* It shows the game mechanics as well as the goals and objectives of the game 
* 
* @author Mikee Jazmines & Alyssa Ty
* @version May 18, 2016
*/
public class Instructions extends JComponent{
	private ImageIcon back;
	private MainFrame mf;
	public static enum MANUAL{
		OBJECTIVES,
		PAPERS,
		CONTROLS,
		POWERUPS,
		STRESSBAR,
		TWOPLAYERMODE,
		CREDITS
	}
	public static MANUAL Manual = MANUAL.OBJECTIVES;
	
	/**
	 * The Instruction is connected to the MainFrame 
	 * @param m
	 */
	public Instructions(MainFrame m){
		mf = m;
	}
	/**
	 * This methods draws the images in the {@link Graphics2D} object. When the person clicks the next or back button it is connected to another state that shows a new image on the screen 
	 * @param g2d
	 */
	
	public void draw(Graphics2D g2d){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		if (Manual == MANUAL.OBJECTIVES){
			back = new ImageIcon("ss.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
		else if (Manual == MANUAL.PAPERS){
			back = new ImageIcon("Instructionforgame_2.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
		else if (Manual == Manual.CONTROLS){
			back = new ImageIcon("Controls_Instructions.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
		else if (Manual == Manual.POWERUPS){
			back = new ImageIcon("Powerup_instructions.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
		else if (Manual == Manual.STRESSBAR){
			back = new ImageIcon("StressBar_instructions.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
		else if (Manual == Manual.TWOPLAYERMODE){
			back = new ImageIcon("twoplayermode.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
		else if (Manual == Manual.CREDITS){
			back =  new ImageIcon("Creditsreal.png");
			back.paintIcon(mf, g2d, 0, 0);
		}
	}
}