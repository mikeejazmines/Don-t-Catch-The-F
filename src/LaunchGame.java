/**
* This class launches the game. It calls the MainFrame and MenuScreen so that the Game can start. 
 * It sets the size of the Frame 
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */

import javax.swing.*;
import java.awt.*;
public class LaunchGame {

	private JFrame f;
	private MainFrame mf;
	private MenuScreen ms;
	public LaunchGame() throws InterruptedException{
		
		f = new JFrame();
		f.getContentPane().setPreferredSize(new Dimension(500, 650));
		f.setTitle("Don't Catch the F!");
	    f.pack();
	    f.setResizable(false);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setVisible(true);
	    f.setLocationRelativeTo(null);
	 
	    mf = new MainFrame(f);
	    ms = new MenuScreen();
	    f.add(mf);
	    f.revalidate();
	}
	public static void main (String args[]) throws InterruptedException {
		LaunchGame game = new LaunchGame();
	}	
}
