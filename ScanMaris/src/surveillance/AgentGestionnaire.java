package surveillance;

import java.security.cert.Extension;
import java.util.ArrayList;

import simulation.Navire;
import simulation.SITUlistener;
import visualisation.ObjetAffichable;




import jade.core.Agent;
import jade.util.leap.HashMap;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


@SuppressWarnings("serial")
public class AgentGestionnaire extends Agent implements SITUlistener {

	
	//map des agents surveillant
	HashMap agentsMap= new HashMap(); 
	
	
	
	
	
	 private static AgentContainer container;
	  
	    
	 
	    @SuppressWarnings("unused")
		private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
	    
	    AgentController controller= container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
	    }
	
	
	@Override
	public void updateAll(ArrayList<ObjetAffichable> objets, int step) {
		// TODO Auto-generated method stub
		start(objets);
		
	}
	
	@Override 
	protected void setup() {
		Object[] args =this.getArguments();
	    if (args != null) { 
	    	System.out.println("la liste des arguments"+args[1]);
	        String agentName=this.getAID().getName();
	          // this.addBehaviour(new BuyerBehaviours(this, period));
	      
	    }
	}
	
	/**
	 * this method allows ro start the agents
	 */
	
	
	public String start(ArrayList<ObjetAffichable> objets){
		
		for(int i=0; i<=objets.size();i++){
			Object obj=objets.get(i);
			if(obj instanceof Navire){			
				try {
					initAndRun(new AgentSurveillant(), "Surveillant "+((Navire) obj).getId(), new Object[]{obj});
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "the begin it done";
	} 
	

}
