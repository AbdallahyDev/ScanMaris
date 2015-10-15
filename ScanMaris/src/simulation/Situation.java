package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import visualisation.ObjetAffichable;

/**
 * La Situation represente l'environnement independant du systeme.
 * Elle est creee a partir d'une instance de la classe {@link LecteurScenario}.
 * Elle contient un certain nombre de navires.
 * @author riviere
 *
 */
public class Situation {
	private HashMap<Integer, Navire> navires;
	private HashMap<Integer, Navire> outOfSitu;
	private ArrayList<Integer> naviresOut;

	public Situation(){
		navires=new HashMap<Integer, Navire>();	
		outOfSitu=new HashMap<Integer, Navire>();
		naviresOut=new ArrayList<Integer>();
	}

	public void maj(HashMap<Integer, Navire> nav){
		Iterator<Integer> it=nav.keySet().iterator();
		while(it.hasNext()){
			int id=it.next();
			Navire n=nav.get(id);
			if((n.getPositionX()<5)||(n.getPositionX()>1578)
					||(n.getPositionY()<25)||(n.getPositionY()>740)){
				if(!naviresOut.contains(id)){
					outOfSitu.put(id, n);
					naviresOut.add(id);
				}
			}
			else navires.put(id, n);
		}
	}

	public HashMap<Integer, Navire> getNavires() {
		return navires;
	}

	public ArrayList<ObjetAffichable> getObjetsAffichables(){
		ArrayList<ObjetAffichable> obj=new ArrayList<ObjetAffichable>();
		Iterator<Integer> it=navires.keySet().iterator();
		while(it.hasNext()){
			int id=it.next();
			obj.add(navires.get(id));
		}
		return obj;
	}
	
	public void setNavires(HashMap<Integer, Navire> navires) {
		this.navires = navires;
	}

	public String toString(){
		String situ="Situation : ";
		Iterator<Integer> it=navires.keySet().iterator();
		while(it.hasNext()){
			situ=situ.concat(navires.get(it.next()).toString());
		}
		return situ;
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer, Navire> getOutOfSitu() {
		HashMap<Integer, Navire> toReturn=(HashMap<Integer, Navire>) outOfSitu.clone();
		outOfSitu.clear();
		return toReturn;
	}


}
