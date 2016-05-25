import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class creates the Game object if the player chooses to play in single player mode.
 *
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class Game extends JComponent{
	private MainFrame mf;
	private ImageIcon image;
	private JFrame f;
	
	private Random random = new Random();
	
	private CircleTest ct;
	private StressBar sb;
	private Score score;
	
	private MagisBird bird;
	private Timer timer, paperTimer, extraTimer;
	
	protected boolean mBird;
	
	private ArrayList<Paper> papersList;
	private ArrayList<Extra> extras;
	
	public Game(MainFrame m, JFrame frame){
		mf = m;
		f = frame;
		
		frame.getContentPane().addKeyListener(new MyKeyListener());
		frame.getContentPane().setFocusable(true);
		frame.getContentPane().requestFocus();
		
		ct = new CircleTest(300, 450, mf);
		sb = new StressBar(0, this);
		score = new Score();
		
		papersList = new ArrayList<Paper>();
		extras = new ArrayList<Extra>();
		
		score.restartScore();
		mBird = false;
		
		//is hit is found here //if the object hits a person or the floor it removes from the array list
		//this method also animates the objects 
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(MainFrame.counter == 30){
					makeMagisBird();
				}
				
				for(Extra a: extras){
					a.animate();
				}	
				
				for(Paper a: papersList){
					a.animate();
				}
			
				int size = papersList.size();
				for(int i = 0; i < size; i++){
					if(papersList.get(i).getHit()){
						papersList.remove(i);
						i--;
						size--;
					}
				}
				
				int eSize = extras.size();
				for(int i = 0; i < eSize; i++){
					if(extras.get(i).getHit()){
						extras.remove(i);
						i--;
						eSize--;
					}
				}
		      }
		    };
		    
	    timer = new Timer(400, al);
	    
	    ActionListener alP = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				makeAPapers();
				makeFPapers();
		      }
		    };
	    paperTimer = new Timer(1600, alP);
	    
	    // falls random extras
	    ActionListener alE = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int x = random.nextInt(3);
				switch(x){
				case 0:
					extras.add(new Beer(sb, ct));
					break;
				case 1:
					extras.add(new BrokenHeart(sb, ct));
					break;
				case 2:
					extras.add(new Facebook(sb, ct));
					break;
				}
				if(MainFrame.counter % 5 == 0){
					extras.add(new Coffee(ct, mf));
				}
				if(MainFrame.counter < 30 && MainFrame.counter % 5 == 0){
					extras.add(new MagisBird(ct, Game.this));
				}
		      }
		    };
	    extraTimer = new Timer(1400, alE);
	    extraTimer.start();
	}	
	
	/**
	 * This method draws what is needed in the Game Class.
	 * 
	 * @param g2d Graphics2D 
	 */
	public void draw(Graphics2D g2d){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		image = new ImageIcon("Classroom_size.png");
		image.paintIcon(this, g2d, 0, 0);
		g2d.setRenderingHints(rh);
		ct.draw(g2d);
		sb.draw(g2d);
		score.draw(g2d);
		
		Font topFont = new Font("arial", Font.PLAIN, 40);
		g2d.setColor(Color.BLACK);
		g2d.setFont(topFont);
		g2d.drawString(Integer.toString(mf.counter), 240, 50);
		
		for(Paper a: papersList){
			a.draw(g2d);
		}
		
		for(Extra a: extras){
			a.draw(g2d);
		}
	}
	
	
	/**
	 * This method creates APapers. 
	 */
	public void makeAPapers(){
		papersList.add(new APaper(score, ct));
	}
	
	/**
	 * This method creates FPapers.
	 */
	public void makeFPapers(){
		papersList.add(new FPaper(score, ct));
	}
	
	/**
	 * This method makes all of the APapers turn into instances of FPapers.
	 */
	public void everythingF(){
		for(Paper a: papersList){
			if(!(a instanceof FPaper)){
				APaper b = (APaper) a;
				b.makeF();
			}
		}
	}
	
	/**
	 * This method instantiates the MagisBird object
	 */
	public void makeMagisBird(){
		bird = new MagisBird(ct, this);
		extras.add(bird);
		mBird = true;
	}
	
	/**
	 * This method makes all of the FPapers turn into instances of APapers.
	 */
	public void everythingA(){
		for(Paper a: papersList){
			if(!(a instanceof APaper)){
				FPaper b = (FPaper) a;
				b.noF();
			}
		}
	}
	
	/**
	 * This method returns the Papers back to their original state. 
	 */
	public void normalPapers(){
		for(Paper a: papersList){
			if(a instanceof APaper){
				APaper b = (APaper) a;
				b.noF();
			}
			else if(a instanceof FPaper){
				FPaper b = (FPaper) a;
				b.makeF();
			}
		}
	}

	/**
	 *  This method removes all the papers and extras from their respective ArrayLists once the game ends.
	 *  This also restarts the stressbar back to zero.
	 */
	public void removeAll(){
		
		int size = papersList.size();
		if(size > 0){
			for(int i = 0; i < size; i++){
				papersList.remove(i);
				i--;
				size--;
			}
		}
		
		int eSize = extras.size();
		if(eSize > 0){
			for(int i = 0; i < eSize; i++){
				extras.remove(i);
				i--;
				eSize--;
			}
		}
		
		sb.resetStress();
		
	}
	
	/**
	 * This method makes it go left.
	 */
	public void left(){
		ct.left();
	}
	
	/**
	 * This method makes it go right.
	 */
	public void right(){
		ct.right();
	}
	
	/**
	 * This method returns the score object of the Game class.
	 * 
	 * @return score
	 */
	public Score getScoreClass(){
		return score;
	}
	
	/**
	 * This turns off the effect of the MagisBird.
	 */
	public void magisOff(){
		bird.stopTime();
	}
	
	/**
	 * This turns off the effect of the StressBar
	 */
	public void stressOff(){
		sb.stopTime();
	}
	
	/**
	 * This turns off all the timers in the game class.
	 */
	public void stopTimers(){
		paperTimer.stop();
		timer.stop();
		extraTimer.stop();
	}
	
	/**
	 * This turns on all the timers in the game class.
	 */
	public void startTimers(){
		paperTimer.start();
		timer.start();
		extraTimer.start();
	}
	
	//Key Listeners
	
	class MyKeyListener implements KeyListener {
	      public void keyTyped( KeyEvent ke ) {
	      }

	      public void keyPressed( KeyEvent ke ) {
	        int keyCode = ke.getKeyCode();
	        if(MainFrame.State == MainFrame.STATE.GAME){  
	        switch(keyCode) {
	                case KeyEvent.VK_LEFT:
	                  left();
	                  repaint();
	                    break;
	                case KeyEvent.VK_RIGHT:
	                	right();
	                  repaint();
	                    break;
	                default :
	                  break;
	          }
	        }
	      }
	
	      public void keyReleased( KeyEvent ke ) {
	      }
	}
}

