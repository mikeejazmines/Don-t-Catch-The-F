import java.io.*;
import java.net.*;
import java.util.Random;

import javax.swing.Timer;

import java.awt.event.*;

/**
 * This class creates the Server.
 *
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 */
public class Server {

    private ServerSocket theServer;
    
    private static final int MAX = 2;
    private int numPlayers;
    
	public static double multiplier;
	public static int counterServer, done;
	private boolean hasStarted;
	
	Random rand = new Random();
	
    private int p1Score, p2Score, p1x, p2x;
    public int complete;
    
    private Socket sock1, sock2;
    
    private TalkToClientThread p1Thread;
    private TalkToClientThread p2Thread;
    private Timer lifeSaver;
    
    public int xExtra, xAP, xFP, fExtra, fAP, fFP, num; 
    private int extraSpeed, extraX, extraNum, coffeeSpeed, coffeeX, magisSpeed, magisX;
    private int APSpeed, APX, FPSpeed, FPX, removeP1, removeP2, removeX1, removeX2;
    
    private boolean running, moveP1, moveP2, moveX1, moveX2;
    

    /**
     * Constructor of the server.
     */
    public Server() {
    	hasStarted = false;
    	multiplier = 1;
    	
        numPlayers = 0;
        complete = 0;
        num = 0;
        try {
            theServer = new ServerSocket(6574);
            System.out.println("===== THE SERVER IS RUNNING =====");
        } catch (IOException ex) {
            System.out.println("Error in Server.");
        } 
        
        ActionListener alT = new ActionListener(){
		public void actionPerformed(ActionEvent ae){
    		extraSpeed = randomFall();
    		extraX = randomX();
    		extraNum = rand.nextInt(3);
    		APSpeed = randomFall();
			APX = randomX();
			FPSpeed = randomFall();
			FPX = randomX();
    		if(num % 2 == 0){
    			coffeeSpeed = randomFall();
    			coffeeX = randomX();
    		}
    		if(num % 3 == 0){
    			magisSpeed = randomFall();
    			magisX = randomX();
    		}
			num++;
	      }
	    };
	    lifeSaver = new Timer(1000, alT);
	    lifeSaver.start();
    }
    
    /**
     * This method accepts the connections if the number of players connected is less than 2.
     * It also starts the threads once complete.
     */
    public void acceptConnections() {
        try {
            while (numPlayers < MAX) {
                numPlayers++;
                
                if(numPlayers == 1) {
                    sock1 = theServer.accept();
                    System.out.println("[PLAYER " + numPlayers + "] has connected.");
                	p1Thread = new TalkToClientThread(sock1, numPlayers);
                } else {
                	sock2 = theServer.accept();
                	System.out.println("[PLAYER " + numPlayers + "] has connected.");
                    complete = 1;
                    p2Thread = new TalkToClientThread(sock2, numPlayers);
                    running = true;
                    p1Thread.sendComplete();
                    p2Thread.sendComplete();
                }
            }
            
        } catch (IOException ex) {
            System.out.println("Error in acceptConnections() method.");  
        }
    }
    
    /**
     * This generates a random X Coordinate.
     * @return x
     */
    public int randomX(){
    	int x = rand.nextInt(420);
    	return x;
    }
    
    /**
     * This generates a random Fall Speed.
     * @return fallSpeed
     */
    public int randomFall(){
    	int fallSpeed= rand.ints(20,40,100).findFirst().getAsInt();
    	return fallSpeed;
    }
    
    class TalkToClientThread implements Runnable {
        
        // Each thread will have a player ID (which was determined by the 
        // value of numPlayers when each thread was created).
        // That way, we can differentiate between the 2 threads.
        // We need to do this because we want to pass player 1 data to player 2
        // and vice versa.
        private int playerID;
        private Socket theSocket;
        private DataInputStream in;
        private DataOutputStream out;

        public TalkToClientThread(Socket s, int pid) {
            playerID = pid;
            theSocket = s;
            try {  
                in = new DataInputStream(theSocket.getInputStream());
                out = new DataOutputStream(theSocket.getOutputStream());
                
                if (playerID == 1) {
                	//flushes the ID
                    out.writeInt(playerID);
                    out.flush();
                    p1Score = 0;                
                } else {
                    out.writeInt(playerID);
                    out.flush();
                    p2Score = 0;
                }    
            } catch (IOException ex) {    
                System.out.println("Error in TalkToClientThread constructor.");   
            }
        }
        
        /**
         * This method sends whether both clients have connected to the server.
         */
        public void sendComplete(){
        	try {
				out.writeInt(complete);
				out.flush();
				System.out.println("HELLO----" + complete + "-----COMPLETE");
				hasStarted = true; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
            Thread t = new Thread(this);
            t.start();
            startCountdown();
        }
        
        public void run() {
            // This part runs once the threads are started. As mentioned 
            // in the comments in the acceptConnections() method, threads will start only
            // when two players have connected.
            readWriteToClient();
        }

        public void readWriteToClient() {
            // For your game, you'll most likely need this to be in a loop.
            // That's because you'll probably be sending data constantly.
            // In this simple example, this part just sends player 1's name to player 2,
            // and vice versa.
            try {
            	int n = 0;
            	int p = 0;
            	int e = 0;
            	
            	while(running){
            		
            		
	                if(playerID == 1) {
	                //own score in
	                    p1Score = in.readInt();
	                    
	                // own x value 
	                    p1x = in.readInt();
	                    
	               //flush p2 score
	                    out.writeInt(p2Score);
	                    out.flush();
	                    
	               // flush p2 x
	                    out.writeInt(p2x);
	                    out.flush();
	                    
	                //flush counterServer    
	                    out.writeInt(counterServer);
	                    out.flush();
	                    
	                    //Extra
	                    if(n % 10 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(extraX);
	                    	out.writeInt(extraSpeed);
	                    	out.writeInt(extraNum);
	                    	out.writeInt(e);
	                    	out.flush();
	                    	e++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //Coffee
	                    if(n % 20 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(coffeeX);
	                    	out.writeInt(coffeeSpeed);
	                    	out.writeInt(e);
	                    	out.flush();
	                    	e++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //Magis
	                    if(counterServer <= 15){
		                    if(n % 30 == 0){
		                    	out.writeInt(1);
		                    	out.flush();
		                    	out.writeInt(magisX);
		                    	out.writeInt(magisSpeed);
		                    	out.writeInt(e);
		                    	out.flush();
		                    	e++;
		                    }
		                    else{
		                    	out.writeInt(0);
		                    	out.flush();
		                    }
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //APaper
	                    if(n % 10 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(APX);
	                    	out.writeInt(APSpeed);
	                    	out.writeInt(p);
	                    	out.flush();
	                    	p++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                  //FPaper
	                    if(n % 10 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(FPX);
	                    	out.writeInt(FPSpeed);
	                    	out.writeInt(p);
	                    	out.flush();
	                    	p++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //RemovingPapers
	                    boolean paperCheck = in.readBoolean();
	                    if(paperCheck){
	                    	removeP1 = in.readInt();
	                    	moveP1 = true;
	                    }
	                    
	                    boolean extraCheck = in.readBoolean();
	                    if(extraCheck){
	                    	removeX1 = in.readInt();
	                    	moveX1 = true;
	                    }
	                    
	                    //Removes from P2
	                    if(moveP2){
	                    	out.writeInt(1);
	                    	out.writeInt(removeP2);
	                    	out.flush();
	                    	moveP2 = false;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //Removes from P2
	                    if(moveX2){
	                    	out.writeInt(1);
	                    	out.writeInt(removeX2);
	                    	out.flush();
	                    	moveX2 = false;
	                    }
	                    else {
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    
	                //flush done     
	                    out.writeInt(done);
	                    out.flush();
	                } else {
	                	
	               //PLAYER 2
	                	//own score in  	
	                    p2Score = in.readInt();
	                    
	               // own x value 
	                    p2x = in.readInt();
	                    
	               //flush p1 score     
	                    out.writeInt(p1Score);
	                    out.flush();
	                    
	               // flush p1 x
	                    out.writeInt(p1x);
	                    out.flush();
	                    
	               //flush counterServer     
	                    out.writeInt(counterServer);
	                    out.flush();
	                    
	                    //Extra
	                    if(n % 10 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(extraX);
	                    	out.writeInt(extraSpeed);
	                    	out.writeInt(extraNum);
	                    	out.writeInt(e);
	                    	out.flush();
	                    	e++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //Coffee
	                    if(n % 20 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(coffeeX);
	                    	out.writeInt(coffeeSpeed);
	                    	out.writeInt(e);
	                    	out.flush();
	                    	e++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //Magis
	                    if(counterServer <= 20){
		                    if(n % 30 == 0){
		                    	out.writeInt(1);
		                    	out.flush();
		                    	out.writeInt(magisX);
		                    	out.writeInt(magisSpeed);
		                    	out.writeInt(e);
		                    	out.flush();
		                    	e++;
		                    }
		                    else{
		                    	out.writeInt(0);
		                    	out.flush();
		                    }
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //APaper
	                    if(n % 10 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(APX);
	                    	out.writeInt(APSpeed);
	                    	out.writeInt(p);
	                    	out.flush();
	                    	p++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                  //FPaper
	                    if(n % 10 == 0){
	                    	out.writeInt(1);
	                    	out.flush();
	                    	out.writeInt(FPX);
	                    	out.writeInt(FPSpeed);
	                    	out.writeInt(p);
	                    	out.flush();
	                    	p++;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	                    //Removes Paper
	                    boolean paperCheck = in.readBoolean();
	                    if(paperCheck){
	                    	removeP2 = in.readInt();
	                    	moveP2 = true;
	                    }
	                    
	                    //Removes Extra
	                    boolean extraCheck = in.readBoolean();
	                    if(extraCheck){
	                    	removeX2 = in.readInt();
	                    	moveX2 = true;
	                    }
	                    
	                    //Removes from P1
	                    if(moveP1){
	                    	out.writeInt(1);
	                    	out.writeInt(removeP1);
	                    	moveP1 = false;
	                    }
	                    else{ 
	                    	out.writeInt(0); 
	                    	out.flush();
	                    }
	                    
	                    //Removes from P1
	                    if(moveX1){
	                    	out.writeInt(1);
	                    	out.writeInt(removeX1);
	                    	out.flush();
	                    	moveX1 = false;
	                    }
	                    else{
	                    	out.writeInt(0);
	                    	out.flush();
	                    }
	                    
	               //flush done     
	                    out.writeInt(done);
	                    out.flush();
	                }
	                try{
	                	Thread.sleep(100);
	                }
	                catch(InterruptedException ie){
	                	System.out.println("Interrupted!");
	                }
	                n++;
            	}
            } catch (EOFException ex){
            	closeSocket(theSocket);
            }
            catch (IOException ex) {
                System.out.println("Not Running!");
            } 
        }
    }
	
    public void closeSocket(Socket s){
    	try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }
    
    public void startCountdown(){
		
		//Prevents multiple timers from starting
    	if(hasStarted==true){
			
			Thread timer = new Thread(new Runnable(){
				
				public void run(){
					
					counterServer = 60;
					done = 0;
					
					try{
						while(hasStarted){
							
							counterServer--;
//							System.out.println(counterServer);
							
							if(counterServer <= 24){
								multiplier = 1.5;
							}
							else if(counterServer <= 18){
								multiplier = 2;
							}
							else if(counterServer <= 12){
								multiplier = 2.5;
							}
							else if(counterServer <= 6){
								multiplier = 3;
							}
							
							if(counterServer == 0){
								hasStarted = false;
								done = 1;
								return;
							}
							Thread.sleep(1000);
							
						}
					}
					catch(Exception e){
						//e.printStackTrace();
					}
				}
			});
			timer.start();
		}
	}	
	public String getTime(){
		return Integer.toString(counterServer);
	}
    
    // main method for game server
    public static void main(String[] args) {
        Server gs = new Server();
        gs.acceptConnections();
    }

}
