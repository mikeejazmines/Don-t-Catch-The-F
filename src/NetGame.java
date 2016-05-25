import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class creates the NetGame object if the player chooses to play in network mode.
 *
 * @author Mikee Jazmines, Alyssa Ty
 * @version May 18, 2016
 */
public class NetGame extends JComponent {
	private MainFrame mf;
	private ImageIcon image;
	private JFrame frame;
	private Server server; 
	protected int complete; 
	protected String stuff; 
	private Random random = new Random();
	
	private CircleTest ct, otherPlayer;
	private StressBar sb;
	private Score score;
	private int id;
	
	private MagisBird bird;
	private Timer timer;
	
	protected boolean mBird; 
	
	private ArrayList<Paper> papersList;
	private ArrayList<Extra> extras;
	
	private int counter, enemyScore;
	private int enemyX, paperNum, extraNum; 
	private boolean isConnected, removeExtra, removePaper;
	private String ipAddress; 
	
	
	/**
	 * This serves as the constructor of the NetGame that accepts a reference to MainFrame and JFrame
	 * It also contains the timers that will keep the netgame running.
	 * 
	 * @param m MainFrame
	 * @param f JFrame
	 */
	public NetGame(MainFrame m, JFrame f){
		mf = m;
		frame = f;
		
		removeExtra = false;
		removePaper = false;
		
		frame.getContentPane().addKeyListener(new MyKeyListener());
		frame.getContentPane().setFocusable(true);
		frame.getContentPane().requestFocus();
		
		ct = new CircleTest(200, 450, mf);
		otherPlayer = new CircleTest(400,450,mf);
		sb = new StressBar(0, this);
		score = new Score();
		
		papersList = new ArrayList<Paper>();
		extras = new ArrayList<Extra>();

		score.restartScore();
		boolean hasStarted = false; 
		isConnected =false; 
		complete = 0;
		counter = 0;
		
		//is hit is found here //if the object hits a person or the floor it removes from the array list
		//this method also animates the objects 
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				for(Extra a: extras){
					a.animate();
				}	
				
				for(Paper a: papersList){
					a.animate();
				}
			
				int size = papersList.size();
				for(int i = 0; i < size; i++){
					if(papersList.get(i).getHit()){
						paperNum = papersList.get(i).getValue();
						papersList.remove(i);
						i--;
						removePaper = true;
						size--;
					}
				}
				
				int eSize = extras.size();
				for(int i = 0; i < eSize; i++){
					if(extras.get(i).getHit()){
						extraNum = extras.get(i).getValue();
						extras.remove(i);
						removeExtra = true;
						i--;
						eSize--;
					}
				}
		      }
		    };
		    
	    timer = new Timer(400, al);
	}	

	/**
	 * This method draws what is needed in the NetGame Class.
	 * 
	 * @param g2d Graphics2D
	 */
	public void draw(Graphics2D g2d){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		image = new ImageIcon("Classroom_size.png");
		image.paintIcon(this, g2d, 0, 0);
		g2d.setRenderingHints(rh);
		
		ct.setPlayer(id);
		ct.draw(g2d);
		
		sb.draw(g2d);
		score.draw(g2d);

		Font topFont = new Font("arial", Font.PLAIN, 40);
		g2d.setColor(Color.BLACK);
		g2d.setFont(topFont);
		g2d.drawString(Integer.toString(counter), 240, 50);
	
		//draw enemy person 
		if (id==1){
			otherPlayer.setPlayer(2);
			otherPlayer.draw(g2d);
		} else if (id==2){
			otherPlayer.setPlayer(1);
			otherPlayer.draw(g2d);
		}
	
		//enemy score
		Font enemyFont = new Font("arial", Font.PLAIN, 25);
		g2d.setColor(Color.BLACK);
		g2d.setFont(enemyFont);
		g2d.drawString(Integer.toString(enemyScore), 405, 80);
		Font enemyFont1 = new Font("arial", Font.PLAIN, 20);
		g2d.setColor(Color.BLACK);
		g2d.setFont(enemyFont1);
		g2d.drawString("Opponent's Score", 320, 40);
		
		if(papersList.size() > 0){
			for(Paper a: papersList){
				a.draw(g2d);
			}
		}
		
		if(extras.size() > 0){
			for(Extra a: extras){
				a.draw(g2d);
			}
		}
	}
	
	public void makeExtra(int xCoord, int fall, int n, int v){
		int num = n;
		switch(num){
		case 0:
			extras.add(new Beer(sb, ct, xCoord, fall, v));
			break;
		case 1:
			extras.add(new BrokenHeart(sb, ct, xCoord, fall, v));
			break;
		case 2:
			extras.add(new Facebook(sb, ct, xCoord, fall, v));
			break;
		case 3:
			extras.add(new Coffee(ct, mf, xCoord, fall, v));
			break;
		case 4:
			extras.add(new MagisBird(ct, this, xCoord, fall, v));
		}
	}
	
	/**
	 * This method creates an instance of an APaper and adds it to the list.
	 * @param x
	 * @param fall
	 * @param v
	 */
	public void makeAPaper(int x, int fall, int v){
		papersList.add(new APaper(score, ct, x, fall, v));
	}

	/**
	 * This method creates an instance of an FPaper and adds it to the list.
	 * @param x
	 * @param fall
	 * @param v
	 */
	public void makeFPaper(int x, int fall, int v){
		papersList.add(new FPaper(score, ct, x, fall, v));
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
	 * This method removes an object inside Extra if it is equal to a value sent by the server.
	 * @param x
	 */
	public void removeExtra(int x){
		for(int i = 0; i < extras.size(); i++){
			if(extras.get(i).getValue() == x){
				extras.remove(i);
			}
		}
	}
	
	/**
	 * This method removes an object inside Paper if it is equal to a value sent by the server.
	 * @param x
	 */
	public void removePaper(int x){
		for(int i = 0; i < papersList.size(); i++){
			if(papersList.get(i).getValue() == x){
				papersList.remove(i);
			}
		}
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
	 * This turns off all the timers in the NetGame class.
	 */
	public void stopTimers(){
		//paperTimer.stop();
		timer.stop();
		//extraTimer.stop();
	}
	
	/**
	 * This turns on all the timers in the NetGame class.
	 */
	public void startTimers(){
		//paperTimer.start();
		timer.start();
		//extraTimer.start();
	}
	
	/**
	 * This method sets the Counter to whatever number is inside the parameter
	 * @param n number
	 */
	public void setCounter(int n){
		counter = n;
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
	 * This method returns the integer value of the score.
	 * @return IntScore
	 */
	public int getScore(){
		return score.getIntScore();
	}
	
	/**
	 * This method sets the EnemyScore to whatever number is inside the parameter
	 * @param n number
	 */
	public void setEnemyScore(int n){
		enemyScore = n;
	}
	
	/**
	 * This method returns the integer value of the enemyScore
	 * @return enemyScore
	 */
	public int getEnemyScore(){
		return enemyScore;
	}
	
	public void setEnemyX(int n){
		enemyX =n;
	}
	
	public int getEnemyX(){
		return enemyX;
	}
	
	/**
	 * This method calls the startTimers method.
	 */
	public void start(){
		startTimers();
	}
	
	/**
	 * Once clicked, this method calls a popup to allow the player to type the ip address of the server.
	 */
	public void ipPopup(){
		String input1;
	
	input1 = JOptionPane.showInputDialog("Enter server's ip address");
	ipAddress =input1;
	JOptionPane.showMessageDialog(null," IP Address received: " + ipAddress );
	}

	 /**
	 * This method connects the client to the server.
	 */
	public void connectToServer() {
	        Socket s;
	        try {
	        	ipPopup();
	        	System.out.println(ipAddress);
	            s = new Socket(ipAddress, 6574);
	            TalkToServerThread ttst = new TalkToServerThread(s);
	            Thread t = new Thread(ttst);
	            t.start();
	            System.out.println("hi");
	        } catch (IOException ex) {
	        	//ex.printStackTrace();
	            System.out.println("Error in connectToServer() method.");
	        }
	    }

	/**
	 * This method returns whether the server is connected or not.
	 * @return isConnected
	 */
	public boolean connectedBa() {
		 return isConnected; 
	 }
	 
	 /**
	  * This is the thread of the client that connects it with the server.
	  * This accepts and sends data to the server.
	 * @author Mikee Jazmines & Alyssa Ty
	 * @version May 18, 2016
	 *
	 */
	class TalkToServerThread implements Runnable {

		    private Socket theSocket;
		    private DataInputStream dataIn;
		    private DataOutputStream dataOut;
		    private int enemyScore, check;
		    private boolean running;
		    private NetGame ng = NetGame.this;
		    
		    public TalkToServerThread(Socket s) {
		        theSocket = s;
		        running = false;
		        try {
		            dataIn = new DataInputStream(theSocket.getInputStream());
		            dataOut = new DataOutputStream(theSocket.getOutputStream());
		            System.out.println("created");
					running = true;
		        } catch (IOException ex) {
		            System.out.println("Error in TalkToServerThread constructor.");
		        }
		    }

		    public void run() {
		    	
		        readWriteToServer();
		    }
		    
		    public void readWriteToServer() {
		    	//gets id number
		    	try{
		    	id = dataIn.readInt();
		    	//Checks if the server is full.
		    	complete = dataIn.readInt(); 
		    		if (complete==1){
		    			isConnected =true; 
		    		}
		    		
		    	System.out.println(id +" recieved");
		    	System.out.println(complete +"====COMPLETE====");
		    	} catch (IOException ex){
		    		System.out.println("id assigned was not recieved");
		    	
		    	}
		    	
		    	try {
		    		while(running){
		    		//flush own score
		            dataOut.writeInt(ng.getScore());
		            dataOut.flush();
		            
		            // flush own x 
		            dataOut.writeInt(ng.ct.getX());
		            dataOut.flush();
		            
		            //get score
		            enemyScore = dataIn.readInt();
		            ng.setEnemyScore(enemyScore);
		            
		            // get enemy x
		            enemyX = dataIn.readInt();
		            ng.otherPlayer.setX(enemyX);
		            
		            //get counterServer
		            ng.setCounter(dataIn.readInt());
		            
		            int extra = dataIn.readInt();
		            if(extra == 1){
		            	int x = dataIn.readInt();
		            	int fall = dataIn.readInt();
		            	int num = dataIn.readInt();
		            	int v = dataIn.readInt();
		            	makeExtra(x, fall, num, v);
		            }
		            
		            int coffee = dataIn.readInt();
		            if(coffee == 1){
		            	int x = dataIn.readInt();
		            	int fall = dataIn.readInt();
		            	int v = dataIn.readInt();
		            	makeExtra(x, fall, 3, v);
		            }
		            
		            int magis = dataIn.readInt();
		            if(magis == 1){
		            	int x = dataIn.readInt();
		            	int fall = dataIn.readInt();
		            	int v = dataIn.readInt();
		            	makeExtra(x, fall, 4, v);
		            }
		            
		            int aPaper = dataIn.readInt();
		            if(aPaper == 1){
		            	int x = dataIn.readInt();
		            	int fall = dataIn.readInt();
		            	int v = dataIn.readInt();
		            	makeAPaper(x, fall, v);
		            }
		            
		            int fPaper = dataIn.readInt();
		            if(fPaper == 1){
		            	int x = dataIn.readInt();
		            	int fall = dataIn.readInt();
		            	int v = dataIn.readInt();
		            	makeFPaper(x, fall, v);
		            }
		            
		            if(removePaper){
		            	dataOut.writeBoolean(removePaper);
		            	dataOut.writeInt(paperNum);
		            	dataOut.flush();
		            	removePaper = false;
		            }
		            else{
		            	dataOut.writeBoolean(removePaper);
		            	dataOut.flush();
		            }
		            
		            if(removeExtra){
		            	dataOut.writeBoolean(removeExtra);
		            	dataOut.writeInt(extraNum);
		            	dataOut.flush();
		            	removeExtra = false;
		            }
		            else{
		            	dataOut.writeBoolean(removeExtra);
		            	dataOut.flush();
		            }
		            
		            int paperHi = dataIn.readInt();
		            if(paperHi == 1){
		            	ng.removePaper(dataIn.readInt());
		            }

		            int extraHi = dataIn.readInt();
		            if(extraHi == 1){
		            	ng.removeExtra(dataIn.readInt());
		            }
		            
		            //get done 
		            check = dataIn.readInt();
		            if(check == 1) running = false;
		            if (running == false) MainFrame.State = MainFrame.STATE.ENDSCREEN;
		    		}
		    	 } catch (IOException ex) {
			            System.out.println("Error in readWriteToServer() in NetGame out.");
		    	 }
		}
   
	}
	 
	class MyKeyListener implements KeyListener {
	      public void keyTyped( KeyEvent ke ) {
	      }

	      public void keyPressed( KeyEvent ke ) {
	        int keyCode = ke.getKeyCode();
	        if(MainFrame.State == MainFrame.STATE.NETWORK){  
	        switch(keyCode) {
	                case KeyEvent.VK_LEFT:
	                	left();
                		if(ct.getX() >= otherPlayer.getX() && ct.getX() <= otherPlayer.getX() + otherPlayer.getWidth()){
                			ct.setX(otherPlayer.getX() - ct.getWidth());
                		}
	                  	repaint();
	                    break;
	                case KeyEvent.VK_RIGHT:
	                	right();
	                		if (ct.getX()+ ct.getWidth() >= otherPlayer.getX() && ct.getX() + ct.getWidth() < otherPlayer.getX() + otherPlayer.getWidth()){
	                			ct.setX(otherPlayer.getX()+otherPlayer.getWidth());
	                		}
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

