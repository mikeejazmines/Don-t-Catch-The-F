import java.awt.geom.*;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;

/**
 * This class serves as the bridge between all classes.
 * It calls the different classes, and draws them based on the buttons that you click.
 *
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class MainFrame extends JComponent{

	private JFrame frame;
	private Instructions instr;
	
	private ImageIcon menuPicture;
	private ImageIcon waitingBackground; 
	
	private MenuScreen menu; 
	private EndGame endScreen;
	private MainFrame mf;
	
	private Game game;
	private NetGame netGame;
	
	private static boolean isRunning, isEnd, hasStarted;
	protected boolean isNetwork, connectedToServer;
	private boolean isRemoved;
	
	private MyMouseListener mnl;
	
	static final int WINDOW_WIDTH =500;
	static final int WINDOW_HEIGHT =650;
	
	public static int counter;
	public static double multiplier;
	
 public static enum STATE {
		MENUSCREEN,
		GAME,
		INSTRUCTIONS,
		ENDSCREEN,
		NETWORK,
		WAITING
	};
	
	public static STATE State = STATE.MENUSCREEN;
	
	/**
	 * Constructor of the class that instantiates the different objects needed, and also takes in a JFrame to add mouse listeners to.
	 * 
	 * @param f JFrame
	 */
	public MainFrame(JFrame f){
		mf = this;
		frame = f;
		
		//Instantiation of Instructions
		instr = new Instructions(this);
		
		//Instantiation of Game
		game = new Game(this, frame);	
		
		//Instantiation of Menu
		menu = new MenuScreen();
		
		//Instantiation of EndScreen
		endScreen = new EndGame(this, game.getScoreClass());
		
		mnl = new MyMouseListener();
		this.addMouseListener(new MouseMenuScreen());
		this.addMouseListener(mnl);
		isRemoved = false;
		multiplier = 1;
		isRunning = true;
		isEnd = false;
		hasStarted = false;
		isNetwork = false;
		connectedToServer = false;
	}
	
	/**
	 *	This method starts the repaint thread to allow the mainframe to always remain repainting. 
	 */
	public void start(){
		
		Thread t = new Thread(new Runnable(){
			
			public void run(){
				try{
					while(isRunning){
						mf.repaint();
						Thread.sleep(100);
					}
					if(!isRunning) {
						State = STATE.ENDSCREEN;
						isEnd = true;
						mf.repaint();
					}
					while(isEnd){
						mf.repaint();
						Thread.sleep(100);
						game.stopTimers();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
		/**
		 * This method starts the countdown of the timer. 
		 */
		public static void startCountdown(){
		
		//Prevents multiple timers from starting
		if(!hasStarted){
			
			Thread timer = new Thread(new Runnable(){
				
				public void run(){
					
					counter = 60;
					
					try{
						while(true){
							
							counter--;
							System.out.println(counter);
							
							if(counter <= 24){
								multiplier = 1.5;
							}
							else if(counter <= 18){
								multiplier = 2;
							}
							else if(counter <= 12){
								multiplier = 2.5;
							}
							else if(counter <= 6){
								multiplier = 3;
							}
							
							if(counter == 0){
								isRunning = false;
								hasStarted = false;
								return;
							}
							Thread.sleep(1000);
							
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			});
			timer.start();
			hasStarted = true;
		}
	}
	
	/**
	 * This is the drawing component of the class and draws the different states of the game.
	 */
	public void paintComponent(Graphics g){	
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2d);
		
		if (State == STATE.GAME){
			if(isRemoved){
				isRemoved = false;
			}
			game.draw(g2d);
			isRunning = true;
			game.startTimers();
			isNetwork = false;
		}
		
		if (State == STATE.NETWORK){
			if(isRemoved){
				isRemoved = false;
			}
			netGame.draw(g2d);
			netGame.startTimers();
			isRunning = true;
			isNetwork = true;
			
		}
		
		else if (State == STATE.MENUSCREEN){
			menuPicture = new ImageIcon("mainscreennareal.png");
			menuPicture.paintIcon(this, g2d, 0, 0);
			g2d.setRenderingHints(rh);
			menu.draw(g2d);
			isEnd = false;
			isRunning = true;
			game.stopTimers();
			start();
			if(!isNetwork) game.getScoreClass().restartScore();
			else netGame.getScoreClass().restartScore();
		}
		
		else if (State == STATE.INSTRUCTIONS){
			instr.draw(g2d);
		}
		
		else if (State == STATE.ENDSCREEN) {
			if(!isRemoved){
				if(!isNetwork) game.removeAll();
				else netGame.removeAll();
				//lol 
				if(!isNetwork) game.stopTimers();
				else netGame.stopTimers();
				isRemoved = true;
			}
			endScreen.draw(g2d);
		}
		else if (State == STATE.WAITING){
			waitingBackground = new ImageIcon("gamewaiting2.png");
			waitingBackground.paintIcon(mf, g2d, 0, 0);
			if(netGame.connectedBa()){
				State = STATE.NETWORK;
			}
		}
	}
	
	class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        	int mx = e.getX();
        	int my = e.getY();
        	
			if(State == STATE.MENUSCREEN){
				if(mx > 160 && mx < 160 + 180 && my > 520 && my < 520 + 40){
					SwingUtilities.invokeLater(new Runnable(){
						public void run(){
							netGame = new NetGame(mf, frame);
							endScreen = new EndGame(mf, netGame.getScoreClass(), netGame);
							 	if (!netGame.connectedBa()){
							 		State = STATE.WAITING;}
							 	else{
							 		State = STATE.NETWORK;
							 	}
							frame.getContentPane().removeMouseListener(mnl);
							netGame.connectToServer();
							repaint();
						}
					});

				}
			}
			else{
			}
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    }
	
	/**
	 * This method returns the time left on the counter.
	 * 
	 * @return time
	 */
	public String getTime(){
		return Integer.toString(counter);
	}
	 
	 /**
	  * This method checks if the server is connected.
	  * 
	  * @return isConnected
	  */
	public boolean isConnected(){
		 return connectedToServer;
	 }
}