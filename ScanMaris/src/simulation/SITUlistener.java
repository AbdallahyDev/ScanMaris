package simulation;

import java.util.ArrayList;
import java.util.EventListener;

import visualisation.ObjetAffichable;
import visualisation.Vue;

/**
 * Cette interface est implementee par la classe Vue (@see {@link Vue}).Elle fait le lien entre l'environnement de simulation 
 * ({@link SITU} pour les navires) et l'ihm de visualisation.
 * @author riviere
 *
 */
public interface SITUlistener extends EventListener {
	
	public void updateAll(ArrayList<ObjetAffichable> objets, int step);

}
