
import java.awt.Graphics2D;


/**
 * Serves as an interface of the different extra objects, namely:
 * Facebook, BrokenHeart, and Beer.
 * 
 * @author Mikee Jazmines & Alyssa Ty
 * @version May 18, 2016
 * 
 */
public interface Extra {
	void draw(Graphics2D g2d);
	void animate();
	void effect();
	int getValue();
	boolean getHit();
}
