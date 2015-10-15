package anomalies;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 * Cette classe charge la valeur des parametres des anomalies, des marques
 * et des coefficients lies aux differents types de navires a partir du fichier parameters.xml,
 * et regroupe ces parametres dans des familles de parametres (@see {@link FamilleParametres}).
 * Ces parametres sont ensuite notamment utilises par le moteur de regles (@see {@link MAN})
 * @author riviere
 *
 */
public class LecteurParametres {
	private static Document document;
	private static Element racine;
	private String path;
	private ArrayList<FamilleParametres> Vessels=new ArrayList<FamilleParametres>();
	private ArrayList<FamilleParametres>  Anomalies=new ArrayList<FamilleParametres>();

	private ArrayList<String> vesselTypes=new ArrayList<String>();
	private ArrayList<String> typesAnomalies=new ArrayList<String>();
	private double seuilAlerte;
	
	
	// Les différents types de parametres que l'on peut trouver dans le fichier
	public static enum Type{
		Anomalies,
        Vessels
    }


	public LecteurParametres(double alertLimit){
		seuilAlerte=alertLimit;
		path="./Ressources/parameters.xml";
		
		SAXBuilder sxb=new SAXBuilder();

		try {
			document = sxb.build(new File(path));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		racine = document.getRootElement();
		read();
	}

	private void read() {
		Element Anomaly=racine.getChild(Type.Anomalies.toString());
		List<Element> listAnom=Anomaly.getChildren("Enum");
		Iterator<Element> iter=listAnom.iterator();
		while(iter.hasNext()){
			FamilleParametres family=null;
			String nickName=null;
			HashMap<String,Parametre> parameter=new HashMap<String,Parametre>();
			Element anomalie=iter.next();
			List<Element> param=anomalie.getChildren();
			Iterator<Element> iterParam=param.iterator();
			while(iterParam.hasNext()){			
				Element courant=iterParam.next();
				String name=courant.getName();
				if(name.compareTo("Type")==0){
					nickName=courant.getText();
					typesAnomalies.add(nickName);
					family=new FamilleParametres(Type.Anomalies,nickName);
				}
				else{
					Parametre p=new Parametre(name,Double.parseDouble(courant.getText()));
					parameter.put(name,p);
				}
			}		
			if(family!=null){
				family.setMember(parameter);
				Anomalies.add(family);				
			}
		}

		Element Vessel=racine.getChild(Type.Vessels.toString());
		List<Element> listVessel=Vessel.getChildren("Enum");
		Iterator<Element> iter2=listVessel.iterator();
		while(iter2.hasNext()){
			FamilleParametres family=null;
			String nickName=null;
			HashMap<String,Parametre> parameter=new HashMap<String,Parametre>();
			Element vessel=iter2.next();
			List<Element> param=vessel.getChildren();
			Iterator<Element> iterParam=param.iterator();
			while(iterParam.hasNext()){			
				Element courant=iterParam.next();
				String name=courant.getName();
				if(name.compareTo("Type")==0){
					vesselTypes.add(courant.getText());
					nickName=courant.getText();
					family=new FamilleParametres(Type.Vessels,nickName);
				}
				else{
					Parametre p=new Parametre(name,Double.parseDouble(courant.getText()));
					parameter.put(name,p);
				}
			}
			if(family!=null){
				family.setMember(parameter);
				Vessels.add(family);				
			}
		}
	}


	@SuppressWarnings("unused")
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


	@SuppressWarnings("unused")
	private void load(){
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			document = sxb.build(new File(path));
		}
		catch(Exception e){}
		racine = document.getRootElement();

	}

	public FamilleParametres getParameter(Type family, String type){
		if(family.compareTo(Type.Anomalies)==0){
			for(int i=0;i<Anomalies.size();i++){
				FamilleParametres f=Anomalies.get(i);
				if(f.getNickName().compareTo(type)==0){
					return f;
				}				
			}
			return null;	
		}
		else if(family.compareTo(Type.Vessels)==0){
			for(int i=0;i<Vessels.size();i++){
				FamilleParametres f=Vessels.get(i);
				if(f.getNickName().compareTo(type)==0){
					return f;
				}				
			}	
			return null;	
		}
		else return null;		
	}


	public ArrayList<FamilleParametres> getAnomaliesParameters() {
		return Anomalies;
	}

	public ArrayList<String> getVesselTypes() {
		return vesselTypes;
	}


	public void setVesselTypes(ArrayList<String> vesselTypes) {
		this.vesselTypes = vesselTypes;
	}


	public ArrayList<FamilleParametres> getVesselsParameters() {
		return Vessels;
	}

	public double getSeuilAlerte() {
		return seuilAlerte;
	}

	public ArrayList<String> getTypesAnomalies() {
		return typesAnomalies;
	}


}