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
	        System.out.println("I'm supervisor agent "+navire.getId()+" for the shipe of type:  "+navire.getType());
	        System.out.println(" "+this.getName());
	          //this.addBehaviour(new BuyerBehaviours(this, period));
	      
	    }
	}

	public void setNavire(Navire nav) {
		// TODO Auto-generated method stub
		this.navire = nav;
		System.out.println("Navire : "+navire.getAIS()+" mis a jour");
		
	}

}
