package simulation;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import visualisation.ObjetAffichable;

/**
 * Un Navire est un objet affichable (@see {@link ObjetAffichable}) qui a une vitesse,
 * un cap, une position, un nom, un identifiant AIS et un identifiant dans le systeme
 * Il peut egalement etre de plusieurs types NavType.
 * 
 * @author riviere
 *
 */
public class Navire extends ObjetAffichable{
	private int id;
	private String nom;
	private NavType type;
	private double vitesse;
	private double cap;
	private int positionX;
	private int positionY;
	private BufferedImage image;
	
	private int AIS;
	
	public enum NavType{
		Cargo,
		Tanker,
		Plaisance,
		Peche
	}
	
	public Navire(int ID, String Nom, NavType Type, double Vitesse, double Cap,
			int X, int Y, int AIS){
		id=ID;
		nom=Nom;
		type=Type;
		vitesse=Vitesse;
		cap=Cap;
		positionX=X;
		positionY=Y;
		this.AIS=AIS;
		
		image=null;
		initImage();		
	}
	

	private void initImage(){
		try {
			switch(type){
			case Cargo: setImage(ImageIO.read(getClass().getResourceAsStream("/cargoS.png")));break;
			case Tanker: setImage(ImageIO.read(getClass().getResourceAsStream("/tankerS.png")));break;
			case Plaisance: setImage(ImageIO.read(getClass().getResourceAsStream("/plaisanceS.png")));break;
			case Peche: setImage(ImageIO.read(getClass().getResourceAsStream("/pecheS.png")));break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image;
	}


	public double getVitesse() {
		return vitesse;
	}


	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}


	public double getCap() {
		return cap;
	}


	public void setCap(double cap) {
		this.cap = cap;
	}


	public int getPositionX() {
		return positionX;
	}


	public void setPosition(int X, int Y) {
		this.positionX = X;
		this.positionY = Y;
		
	}
	
	public Point getPosition(){
		return new Point(this.getPositionX(),this.getPositionY());
	}


	public int getId() {
		return id;
	}


	public String getNom() {
		return nom;
	}


	public NavType getType() {
		return type;
	}


	public int getPositionY() {
		return positionY;
	}	
	
	/**
	 * Permet, lors de l'affichage par l'EnvironnementPanel,
	 * de "tourner" le navire dans le sens de son cap
	 */
	public AffineTransform getTransform(){
		AffineTransform transform=new AffineTransform();
		double radian=this.getCap()*(Math.PI/180);
		transform.translate(getPositionX()-getWidth()/2, getPositionY()-getHeight()/2);
		transform.rotate(radian,(int)(getWidth()/2),(int)(getHeight()/2));
		return transform;
	}
	
	public void majSituation(int X, int Y, double vitesse, double cap){
		this.setPosition(X, Y);
		this.setVitesse(vitesse);
		this.setCap(cap);
	}

	public int getAIS() {
		return AIS;
	}


	public void setAIS(int aIS) {
		AIS = aIS;
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
