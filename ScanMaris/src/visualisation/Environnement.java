package visualisation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;

import simulation.Navire;


/**
 * Environnement est le composant principal de l'ihm de visualisation.
 * Elle fait la mise a jour des objets qui implementent ObjetAffichable (@see {@link Navire}).
 * (Cette classe a ete adaptee en partie du code de l'interface de "Dragon Tale", http://neetlife2.blogspot.fr/)
 * @author riviere
 * 
 */
@SuppressWarnings("serial")
public class Environnement extends JPanel{

	// dimensions
	public static int WIDTH;
	public static int HEIGHT;
	public static double SCALE=1;

	// ihm synchro
	private boolean drawing;

	//image
	private BufferedImage image;
	private Graphics2D g;
	private ImageFond bg;

	private HashMap<Integer,ObjetAffichable> ihmObjects;

	/**
	 * 
	 * @param width
	 * @param height
	 * @param fond
	 * @param time
	 */
	public Environnement(int width, int height, String fond, int time){
		super();
		WIDTH=width;
		HEIGHT=height;


		SCALE=0.66;

		ihmObjects=new HashMap<Integer, ObjetAffichable>();

		setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
		setFocusable(true);
		requestFocus();
		bg= new ImageFond(fond);
		init();
	}


	private void draw() {
		this.setDrawing(true);
		bg.draw(g);
		g.drawOval(630, 43, 340, 340);
		g.drawOval(1080, -250, 1000, 1000);
		Iterator<Integer> it=ihmObjects.keySet().iterator();
		while(it.hasNext()){
			ObjetAffichable objet=ihmObjects.get(it.next());
			AffineTransform transform=objet.getTransform();
			if(transform != null){
				g.drawImage(objet.getImage(), transform, null);
			}
			else g.drawImage(objet.getImage(), objet.getPositionX(), objet.getPositionY(),null);
		}
		drawToScreen();
		this.setDrawing(false);
	}

	private void drawToScreen() {
		Graphics g2=getGraphics();
		g2.drawImage(image, 0, 0, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), null);
		g2.dispose();
	}

	private void init() {
		image=new BufferedImage(WIDTH, HEIGHT,BufferedImage.TRANSLUCENT);
		g = (Graphics2D) image.getGraphics();
	}

	/**
	 * Met a jour l'ensemble des objets affichables en une seule fois
	 * @param objets la liste des objets affichables a mettre a jour
	 */
	public void updateAll(ArrayList<ObjetAffichable> objets){
		while(this.isDrawing()){}
		ihmObjects=new HashMap<Integer, ObjetAffichable>();
		for(int i=0;i<objets.size();i++){
			ObjetAffichable o=objets.get(i);
			ihmObjects.put(o.getId(),o);
		}
		draw();
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

}
