package anomalies;

import java.util.ArrayList;
import java.util.HashMap;

import anomalies.LecteurParametres.Type;
import simulation.Navire;
import simulation.Navire.NavType;

/**
 * Moteur de regles qui analyse le comportement d'un navire et retourne des anomalies simples
 * @author riviere
 *
 */
public class MAN {
	private LecteurParametres parametres;

	// Parametres fixes : ne pas modifier
	// Zone cotiere
	private int XzC=1580;
	private int YzC=250;
	private int RayonZC=500;
	private int vitLimite=15;

	// Zone peche interdite
	private int XpI=800;
	private int YpI=213;
	private int RayonpI=170;

	// Vitesses max
	private int vitTanker=28;
	private int vitCargo=35;
	private int vitPlaisance=45;
	private int vitPeche=45;

	// Liste noire
	private ArrayList<Integer> blackList;

	public MAN(int alerte){		
		// Chargement des parametres (anomalies, marques etc.)
		parametres=new LecteurParametres(alerte);	

		blackList=new ArrayList<Integer>();
		blackList.add(5125);
		blackList.add(888);
	}

	/**
	 * Methode qui renvoie l'ensemble des anomalies simples en cours pour le navire n 
	 * @param n Navire a analyser
	 * @return un tableau d'anomalies
	 */
	public ArrayList<Anomalie> analyse(Navire n){
		ArrayList<Anomalie> ano=new ArrayList<Anomalie>();
		NavType type=n.getType();
		double vitesse=n.getVitesse();
		int AIS=n.getAIS();

		if(AIS==0){
			// Anomalie id inconnue
			FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"AIS");
			HashMap<String, Parametre> p=family.getMembers();
			FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
			Double coef=1.0;
			if(nav.getMembers().get("AIS") != null) coef=nav.getMembers().get("AIS").getValue();
			Anomalie anomalie=new Anomalie("AIS", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
			ano.add(anomalie);

		}
		else if(blackList.contains(AIS)){
			// Anomalie liste noire
			FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"BlackList");
			HashMap<String, Parametre> p=family.getMembers();
			FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
			Double coef=1.0;
			if(nav.getMembers().get("BlackList") != null) coef=nav.getMembers().get("BlackList").getValue();
			Anomalie anomalie=new Anomalie("BlackList", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
			ano.add(anomalie);
		}

		if(n.getVitesse()==0){
			// Anomalie arret
			FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"Arret");
			HashMap<String, Parametre> p=family.getMembers();
			FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
			Double coef=1.0;
			if(nav.getMembers().get("Arret") != null) coef=nav.getMembers().get("Arret").getValue();
			Anomalie anomalie=new Anomalie("Arret", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
			ano.add(anomalie);
			
			if((type.equals(NavType.Peche))&&(n.collision(XpI, YpI, RayonpI))){
				// Anomalie peche interdite
				FamilleParametres family2=parametres.getParameter(LecteurParametres.Type.Anomalies,"PecheInterdite");
				HashMap<String, Parametre> p2=family2.getMembers();
				nav=parametres.getParameter(Type.Vessels, type.toString());
				coef=1.0;
				if(nav.getMembers().get("PecheInterdite") != null) coef=nav.getMembers().get("PecheInterdite").getValue();
				Anomalie anomalie2=new Anomalie("PecheInterdite", p2.get("InitValue").getValue()*coef, p2.get("IncreaseValue").getValue()*coef, p2.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie2);
			}			
		}
		else if(type.equals(NavType.Cargo)){
			if((n.collision(XzC, YzC, RayonZC))&&(vitesse>vitLimite)){
				// Anomalie zone cotiere
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"ZoneCotiere");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("ZoneCotiere") != null) coef=nav.getMembers().get("ZoneCotiere").getValue();
				Anomalie anomalie=new Anomalie("ZoneCotiere", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}
			else if(vitesse>vitCargo){
				// Anomalie Vitesse Excessive
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"Vitesse");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("Vitesse") != null) coef=nav.getMembers().get("Vitesse").getValue();
				Anomalie anomalie=new Anomalie("Vitesse", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}			
		}
		else if(type.equals(NavType.Peche)){
			if((n.collision(XzC, YzC, RayonZC))&&(vitesse>vitLimite)){
				// Anomalie zone cotiere
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"ZoneCotiere");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("ZoneCotiere") != null) coef=nav.getMembers().get("ZoneCotiere").getValue();
				Anomalie anomalie=new Anomalie("ZoneCotiere", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue());
				ano.add(anomalie);
			}
			else if(vitesse>vitPeche){
				// Anomalie Vitesse Excessive
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"Vitesse");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("Vitesse") != null) coef=nav.getMembers().get("Vitesse").getValue();
				Anomalie anomalie=new Anomalie("Vitesse", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}			
		}
		else if(type.equals(NavType.Plaisance)){
			if((n.collision(XzC, YzC, RayonZC))&&(vitesse>vitLimite)){
				// Anomalie zone cotiere
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"ZoneCotiere");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("ZoneCotiere") != null) coef=nav.getMembers().get("ZoneCotiere").getValue();
				Anomalie anomalie=new Anomalie("ZoneCotiere", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}
			else if(vitesse>vitPlaisance){
				// Anomalie Vitesse Excessive
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"Vitesse");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("Vitesse") != null) coef=nav.getMembers().get("Vitesse").getValue();
				Anomalie anomalie=new Anomalie("Vitesse", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}			
		}
		else if(type.equals(NavType.Tanker)){
			if((n.collision(XzC, YzC, RayonZC))&&(vitesse>vitLimite)){
				// Anomalie zone cotiere
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"ZoneCotiere");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("ZoneCotiere") != null) coef=nav.getMembers().get("ZoneCotiere").getValue();
				Anomalie anomalie=new Anomalie("ZoneCotiere", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}
			else if(vitesse>vitTanker){
				// Anomalie Vitesse Excessive
				FamilleParametres family=parametres.getParameter(LecteurParametres.Type.Anomalies,"Vitesse");
				HashMap<String, Parametre> p=family.getMembers();
				FamilleParametres nav=parametres.getParameter(Type.Vessels, type.toString());
				Double coef=1.0;
				if(nav.getMembers().get("Vitesse") != null) coef=nav.getMembers().get("Vitesse").getValue();
				Anomalie anomalie=new Anomalie("Vitesse", p.get("InitValue").getValue()*coef, p.get("IncreaseValue").getValue()*coef, p.get("DecreaseValue").getValue()*coef);
				ano.add(anomalie);
			}			
		}
//		afficher(ano,n);
		return ano;
	}
	
	public void afficher(ArrayList<Anomalie> ano, Navire n){
		System.out.println("Anomalies levees par MAN pour le navire "+n.getId()+" : "+ano.size());
		for(int i=0;i<ano.size();i++){
			System.out.println(ano.get(i).toString());
		}
	}

}
