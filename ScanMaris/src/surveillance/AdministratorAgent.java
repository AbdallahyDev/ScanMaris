package surveillance;

import java.security.cert.Extension;
import java.util.ArrayList;

import simulation.Navire;
import simulation.SITU;
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
public class AdministratorAgent extends Agent {

	
	//map des agents surveillant
	//HashMap agentsMap= new HashMap(); 
	private static AgentContainer container;
	

		@SuppressWarnings("unused")
		private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
			
			
	    
	    AgentController controller= container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
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
	    //AdministrartorBehaviour adminBehaviour =new AdministrartorBehaviour(this,10000);
	    //this.addBehaviour(adminBehaviour);
	    
	    
	}
	

	
	public void takeDown(){
	    
	    System.out.println("I'm killed");
    }
	

}
