import java.awt.event.*;
import java.awt.geom.*;
/**
* This class is acts as the MouseListener for the buttons found in the Main Frame. 
* It gets the x and y values and checks if the person clicks within that area. 
* It then changes the State of the MainFrame depending the button you press. 
* This mouselistener is to make the buttons work so that the user can navigate around the main menu screen 
* @author Mikee Jazmines & Alyssa Ty
* @version May 18, 2016
*/
public class MouseMenuScreen implements MouseListener  {

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		Point2D click = e.getPoint();
		
		//MenuScreen State
		if(MainFrame.State == MainFrame.STATE.MENUSCREEN){
			if(MenuScreen.playButton.contains(click)){
				
				MainFrame.State = MainFrame.STATE.GAME;
				MainFrame.startCountdown();
			}
			else if(MenuScreen.instructionsButton.contains(click)){
				MainFrame.State = MainFrame.STATE.INSTRUCTIONS;
			}
		}
		
		//Instructions State
		else if (MainFrame.State == MainFrame.STATE.INSTRUCTIONS){
			if (Instructions.Manual == Instructions.MANUAL.OBJECTIVES){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						MainFrame.State = MainFrame.STATE.MENUSCREEN;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.PAPERS;
					}
				}
			}
			
			else if (Instructions.Manual == Instructions.MANUAL.PAPERS){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						Instructions.Manual = Instructions.MANUAL.OBJECTIVES;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.CONTROLS;
					}
				}
			}
			
			else if (Instructions.Manual == Instructions.MANUAL.CONTROLS){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						Instructions.Manual = Instructions.MANUAL.PAPERS;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.POWERUPS;
					}
				}
			}
			
			else if (Instructions.Manual == Instructions.MANUAL.POWERUPS){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						Instructions.Manual = Instructions.MANUAL.CONTROLS;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.STRESSBAR;
					}
				}
			}
			
			else if (Instructions.Manual == Instructions.MANUAL.STRESSBAR){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						Instructions.Manual = Instructions.MANUAL.POWERUPS;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.TWOPLAYERMODE;
					}
				}
			}
			
			else if (Instructions.Manual == Instructions.MANUAL.TWOPLAYERMODE){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						Instructions.Manual = Instructions.MANUAL.STRESSBAR;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.CREDITS;
					}
				}
			}
			
			else if (Instructions.Manual == Instructions.MANUAL.CREDITS){
				if (mx > 23 && mx <= 93){
						if (my >= 558 && my <= 634){
						Instructions.Manual = Instructions.MANUAL.TWOPLAYERMODE;
					}
				}
				else if (mx>398 && mx<=468){
					if(my>=560 && my<=634){
						Instructions.Manual = Instructions.MANUAL.OBJECTIVES;
						MainFrame.State = MainFrame.STATE.MENUSCREEN;
					}
				}
			}
		}
		
		//EndScreen State
		else if (MainFrame.State == MainFrame.STATE.ENDSCREEN){
			if (mx > 250 && mx <= 280){
				if (my >= 375 && my <= 410){
				//	MainFrame.State = MainFrame.STATE.MENUSCREEN;
					System.exit(0);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
