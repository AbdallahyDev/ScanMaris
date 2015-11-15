package controle;

import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import simulation.SITU;
import surveillance.AdministratorAgent;
import visualisation.Vue;
import anomalies.MAN;

/**
 * Classe principale qui lance le programme :
 * d'une part l'interface graphique (@see {@link Vue}) et d'autre part la simulation (@see {@link SITU}) elle-meme
 * ainsi que le moteur de regle (@see {@link MAN}), non utilise pour le moment
 * 
 * @author riviere
 *
 */
public class Controleur {

	private Vue ihm;
	private final SITU situ;
	private final MAN moteur;
	private AdministratorAgent AgentGestion;
	private static AgentContainer container;

	public Controleur(int simulationTime, int alerte, int firstStep, int largeur, int hauteur, int echelle) throws StaleProxyException{
		// Initialisation du moteur de regles
		moteur=new MAN(alerte);	
		
		ihm=new Vue(this,largeur,hauteur,simulationTime);
		AgentGestion=new AdministratorAgent();
		 //AgentGestion.setup();
		// Initialisation de l'observateur de situation
		situ=new SITU(ihm,simulationTime,echelle,firstStep);		
		//AgentGestionnaire gest= new AgentGestionnaire();
		
		initAndRun(AgentGestion, "Gestionnaire", new Object[]{""});
		
		
		start();
	}
	
private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
			
			
	    
	    AgentController controller= container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
	    }
	

	public void pause() {
		situ.pause();
	}


	public void stop() {
		situ.stop();
		System.exit(0);
	}

	public void start() {
		new Thread(situ).start();	
	}


	public static void main(String[] args) throws StaleProxyException{
		// Parametres de la simulation
		// Pour accelerer ou ralentir la simulation
		int simulationTime=3500;
		int alerte=20000;
		// Premier pas de simulation : de 0 a 148
		int firstStep=0;
		int largeur=1578;
		int hauteur=719;
		int echelle=4;	
		new Controleur(simulationTime, alerte, firstStep, largeur, hauteur, echelle);
	}
}
