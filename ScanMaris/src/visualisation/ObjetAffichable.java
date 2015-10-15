package visualisation;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Chaque objet (ou agent) destine a etre affiche dans l'interface graphique
 * doit implementer la classe abstraite ObjetAffichable, reliee a une position,
 * une image et une methode permettant de savoir si 2 objets affichables sont en collision
 * (i.e. intersection des images)
 * @author riviere
 *
 */
public abstract class ObjetAffichable {

	public int getPositionX() {
		return 0;
	}
	public int getPositionY() {
		return 0;
	}
	public BufferedImage getImage() {
		return null;
	}

	public int getWidth(){
		return this.getImage().getWidth();
	}

	public int getHeight(){
		return this.getImage().getHeight();
	}

	public int getId(){
		return 0;
	}

	public AffineTransform getTransform(){
		return null;
	}

	/**
	 * @param ob
	 * @return vrai si l'image de l'objet ob est en intersection
	 * avec l'image de l'objet appelant la methode
	 */
	public boolean collision(ObjetAffichable ob){
		int x1=this.getPositionX();
		int y1=this.getPositionY();
		int w1=this.getWidth();
		int h1=this.getHeight();

		int x2=ob.getPositionX();
		int y2=ob.getPositionY();
		int w2=ob.getWidth();
		int h2=ob.getHeight();

		return !((x2>=x1+w1)||(x2+w2<=x1)||(y2>=y1+h1)||(y2+h2<=y1));
	}

	/** 
	 * @param x2
	 * @param y2
	 * @param w2
	 * @param h2
	 * @return vrai si l'image placee en (x2,y2) de taille
	 * w2 x h2 est en intersection avec l'image de l'objet appelant la methode
	 */
	public boolean collision(int x2, int y2, int w2, int h2){
		int x1=this.getPositionX();
		int y1=this.getPositionY();
		int w1=this.getWidth();
		int h1=this.getHeight();

		return !((x2>=x1+w1)||(x2+w2<=x1)||(y2>=y1+h1)||(y2+h2<=y1));		
	}

	/**
	 * @param x2
	 * @param y2
	 * @param rayon
	 * @return  vrai si  le cercle de centre (x2,y2) de rayon rayon
	 * est en intersection avec l'image de l'objet appelant la methode
	 */
	public boolean collision(int x2, int y2, int rayon){
		int x1=this.getPositionX();
		int y1=this.getPositionY();
		int d = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
		if (d>rayon*rayon) return false;
		else return true;
	}
	

	public boolean equals(Object obj){
		ObjetAffichable obj2;
		if(obj instanceof ObjetAffichable) obj2=(ObjetAffichable) obj;
		else return false;
		return this.getId() == obj2.getId(); 
	}

}
