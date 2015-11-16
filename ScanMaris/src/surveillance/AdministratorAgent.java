package surveillance;

import java.security.cert.Extension;
import java.util.ArrayList;

import simulation.Navire;
import simulation.SITUlistener;
import visualisation.ObjetAffichable;




import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.HashMap;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


@SuppressWarnings("serial")
public class AdministratorAgent extends Agent implements SITUlistener {

	
	//map des agents surveillant
	//HashMap agentsMap= new HashMap(); 
	private static AgentContainer container;
	
	 
	  
	    
	 /*
	    public AdministratorAgent() {
		
	    	//R�cup�rer l�environnement d�ex�cution 
			jade.core.Runtime rt = jade.core.Runtime.instance(); 
			// Cr�er un profil par d�faut 
			Profile p = new ProfileImpl();
			// est equivalent � : // Profile p = new ProfileImpl(adresse_ip, 1099, adresse_ip :1099/JADE", true); 
			// Cr�er un conteneur principal par d�faut (i.e. sur cet h�te, port 1099) 
			container = rt.createMainContainer(p);
	}*/


		@SuppressWarnings("unused")
		private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
			
			
	    
	    AgentController controller= container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
	    }
	
	
	@Override
	public void updateAll(ArrayList<ObjetAffichable> objets, int step) {
		// TODO Auto-generated method stub
		System.out.println("�a marche");
		
		
	}
	
	@Override 
	public void setup() {
		Object[] args =this.getArguments();
	    if (args != null) { 
	    	System.out.println(""+args[0]);
	        String agentName=this.getAID().getName();
	          // this.addBehaviour(new BuyerBehaviours(this, period));
	    
	      
	    }else
	    	System.out.print("is not started");
	    this.addBehaviour(new AdministrartorBehaviour(this,10000));
	    
	}
	
	/**
	 * this method allows to start the agents
	 */
	
	
	
	
	
	public void takeDown(){
	    
	    System.out.println("I'm killed");
    }
	

}
