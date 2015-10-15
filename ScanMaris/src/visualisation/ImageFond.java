package visualisation;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 * ImageFond permet d'initialiser l'image de fond 
 * @author riviere
 *
 */
public class ImageFond{
	
	private BufferedImage image;
	
	private double x;
	private double y;
	
	
	public ImageFond(String s) {
		
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPosition(double x, double y) {
		this.x = x % Environnement.WIDTH;
		this.y = y % Environnement.HEIGHT;
	}
		
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x, (int)y, null);
		
		if(x < 0) {
			g.drawImage(
				image,
				(int)x + Environnement.WIDTH,
				(int)y,
				null
			);
		}
		if(x > 0) {
			g.drawImage(
				image,
				(int)x - Environnement.WIDTH,
				(int)y,
				null
			);
		}
	}
	
}







