package surveillance;

import simulation.Navire;
import sun.management.resources.agent;
import jade.core.AID;
import jade.core.Agent;

public class SupervisorAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Navire navire;
	
	@Override 
	protected void setup() {
		
		Object[] args =this.getArguments();
		
	    if (args != null) { 
	    	
	    	navire=(Navire) args[0];
	    	
	    	
	        AID agentId=this.getAID();
	        System.out.println("jje suis lancé mon id est "+agentId);
	          // this.addBehaviour(new BuyerBehaviours(this, period));
	      
	    }
	}

}
