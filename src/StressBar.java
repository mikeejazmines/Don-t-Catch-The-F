import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

/**
 * This class creates a StressBar object that displays a rectangle showing the level of stress of the player 
 *
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class StressBar {
	public int x, y, width, height, caseNum, curWidth;
	
	public Rectangle2D.Double border, inside;
	private Game g;
	private NetGame ng;
	private Timer time;
	
	private int n; // Time of the stress burst
	protected static boolean on;
	protected boolean game;
	
	/**
	 * Constructor of the StressBar class that accepts a number and a reference to the Game
	 * 
	 * @param number Case number
	 * @param gg Reference to game
	 */
	public StressBar(int number, Game gg){
		caseNum = number;
		x = 50;
		y = 80;
		width = 100;
		height = 20;
		g = gg;
		n = 0;
		on = false;
		
		//Length of time that all papers will turn into an F.
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) stopTime();
				else g.everythingF();
		      }
		    };
	    time = new Timer(1000, al);
	    game = true;
	}
	
	/**
	 * Constructor of the StressBar class that accepts a number and a reference to the NetGame
	 * 
	 * @param number Case number
	 * @param gg Reference to NetGame
	 */
	public StressBar(int number, NetGame g){
		caseNum = number;
		x = 50;
		y = 80;
		width = 100;
		height = 20;
		ng = g;
		n = 0;
		on = false;
		
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				n++;
				if(n >= 10) stopTime();
				else g.everythingF();
		      }
		    };
	    time = new Timer(1000, al);
	    game = false;
	}

	/**
	 * Draws the border of the rectangle for the StressBar
	 * and draws the level of stress. Once it reaches 10, it makes everything F.
	 * 
	 * @param g2d Graphics2D object
	 */
	public void draw(Graphics2D g2d){
		border = new Rectangle2D.Double (x, y, width, height);
		g2d.setColor(Color.BLACK);
		g2d.draw(border);
		
		switch(caseNum){
		case 0:
			inside = new Rectangle2D.Double(x, y, 0, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 1:
			inside = new Rectangle2D.Double(x, y, 10, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 2:
			inside = new Rectangle2D.Double(x, y, 20, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 3:
			inside = new Rectangle2D.Double(x, y, 30, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 4:
			inside = new Rectangle2D.Double(x, y, 40, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 5:
			inside = new Rectangle2D.Double(x, y, 50, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 6:
			inside = new Rectangle2D.Double(x, y, 60, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 7:
			inside = new Rectangle2D.Double(x, y, 70, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 8:
			inside = new Rectangle2D.Double(x, y, 80, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 9:
			inside = new Rectangle2D.Double(x, y, 90, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		case 10:
			inside = new Rectangle2D.Double(x, y, 100, height);
			g2d.fill(inside);
			g2d.draw(inside);
			break;
		}	
		
		Font fnt2 = new Font("arial", Font.PLAIN, 15);
		g2d.setFont(fnt2);
		g2d.drawString("STRESS BAR", 53, 115);
		
		
	}
	/**
	 * This method increases the stress bar of the player.
	 * Once it reaches 10, it makes everything into F. 
	 */
	public void upStress(){
		if (caseNum<11){
			caseNum+=1;
			if(caseNum == 10){
				if(game) g.everythingF();
				else ng.everythingF();
				time.start();
				on = true;
				if(MagisBird.on == true){
					g.magisOff();
				}
			}
		}
		else {
			caseNum=0;
		}
	}
	
	/**
	 * This method resets the stressbar to zero.
	 */
	public void resetStress(){
		caseNum = 0;
	}
	
	/**
	 * This method stops the timer of the effect and reverts everything back to normal papers.
	 */
	public void stopTime(){
		time.stop();
		n = 0;
		on = false;
		if(game) g.normalPapers();
		else ng.normalPapers();
	}
}
