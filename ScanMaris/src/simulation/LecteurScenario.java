package simulation;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import simulation.Navire.NavType;

/**
 * NE PAS MODIFIER
 * Utilisee par la SITU (@see {@link SITU}), cette classe permet de lire un scenario, constitue 
 * d'une succession de Situations (@see {@link Situation}), a partir d'un fichier .xml * 
 * @author riviere
 *
 */
public class LecteurScenario {
	private static Element racine;
	private static Document document;
	private SAXBuilder sxb ;
	private int echelle;
	private String path;

	private HashMap<Integer, Situation> situations;

	public LecteurScenario(String path, int scale){
		this.path=path;
		echelle=scale;
		sxb=new SAXBuilder();

		try {
			document = sxb.build(new File(path));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		racine = document.getRootElement();

		situations=new HashMap<Integer, Situation>();
	}

	public void chargerScenario(){
		try {
			this.lireScenario();
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construire et memoriser l'ensemble des situations a partir du fichier .xml
	 * @throws DataConversionException
	 */
	private void lireScenario() throws DataConversionException{
		int pas=0;
		List<Element> listePas=racine.getChildren("pas");
		Iterator<Element> i = listePas.iterator();
		while(i.hasNext()){
			Element courant = (Element)i.next();	

			Situation s=new Situation();
			HashMap<Integer, Navire> navires=new HashMap<Integer, Navire>();

			List<Element> listeNavires=courant.getChildren("Navire");
			Iterator<Element> j = listeNavires.iterator();
			while(j.hasNext()){						
				Element navire=(Element)j.next();	
				int id=Integer.valueOf(navire.getAttribute("id").getValue());
				String name=navire.getChild("nom").getText();
				String type=navire.getChild("type").getText();
				int X=Integer.valueOf(navire.getChild("X").getText());
				int Y=Integer.valueOf(navire.getChild("Y").getText());
				double vitesse=Double.valueOf(navire.getChild("vitesse").getText());
				double cap=Double.valueOf(navire.getChild("cap").getText());
				String toAIS=navire.getChild("AIS").getText();
				int AIS=0;
				if(toAIS.compareTo("")!=0) AIS=Integer.valueOf(toAIS);
				Navire n=new Navire(id, name, NavType.valueOf(type), vitesse, cap, X, Y, AIS);
				navires.put(id, n);
			}
			s.maj(navires);
			situations.put(pas,s);
			pas=pas+1;
		}
	}

	/**
	 * ATTENTION !!
	 * Cette methode permet de modifier le fichier scenario
	 * pour mettre en place de nouvelles situations
	 * @param idNavire
	 * @param index 
	 * @param nbPas le nombre de pas total souhaite
	 */
	public void genererScenario(int idNavire, int index, int nbPas){
		int compteur=0;
		int X=-1;
		int Y=-1;		
		double vitesse=-1;
		double cap=-1;		
		nbPas=nbPas+index;

		List<Element> listePas=racine.getChildren("pas");
		Iterator<Element> i = listePas.iterator();
		while((compteur<=nbPas)&&(i.hasNext())){
			Element courant = (Element)i.next();	
			if(compteur>=index){
				List<Element> listeNavires=courant.getChildren("Navire");
				Element navire=listeNavires.get(idNavire);
				if(X==-1){
					X=Integer.valueOf(navire.getChild("X").getText());
					Y=Integer.valueOf(navire.getChild("Y").getText());
					vitesse=Double.valueOf(navire.getChild("vitesse").getText());
					cap=Double.valueOf(navire.getChild("cap").getText());
				}
				else{
					int[] position=nextPosition(X, Y, vitesse, cap);
					X=position[0];
					Y=position[1];
					System.out.println("X = "+X+" , Y = "+Y);
					navire.getChild("X").setText(""+X);
					navire.getChild("Y").setText(""+Y);
					navire.getChild("cap").setText(""+cap);
					navire.getChild("vitesse").setText(""+vitesse);
				}
			}
			compteur++;
		}
		save();

	}

	/**
	 * A partir de la position (x,y) d'un navire, de son cap,
	 * de sa vitesse, et de l'echelle de la simulation, calcule
	 * la prochaine position du navire
	 * @param x
	 * @param y
	 * @param vitesse
	 * @param cap
	 * @return
	 */
	private int[] nextPosition(int x, int y, double vitesse, double cap){
		int[] position=new int[2];
		double radian=cap*(Math.PI/180);
		position[0]=(int) (x+(vitesse/echelle)*Math.sin(radian));
		position[1]=(int) (y-(vitesse/echelle)*Math.cos(radian));
		return position;
	}

	public Situation getSituation(int pas){
		return situations.get(pas);
	}

	public boolean hasNext(int pas){
		return situations.containsKey(pas);
	}

	private void save()
	{
		try
		{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(path));
		}
		catch (java.io.IOException e){
			e.printStackTrace();
		}
	}
}
