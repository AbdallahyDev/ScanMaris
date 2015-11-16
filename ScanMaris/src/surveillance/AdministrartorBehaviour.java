package surveillance;


import java.util.ArrayList;
import java.util.HashMap;

import controle.Controleur;
import simulation.Navire;
import simulation.SITUlistener;
import visualisation.ObjetAffichable;
import visualisation.Vue;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.introspection.SuspendedAgent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class AdministrartorBehaviour extends TickerBehaviour implements SITUlistener{

	public AdministrartorBehaviour(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}

	
	private static HashMap<Integer,SupervisorAgent> listeAgentNavire = new HashMap<Integer,SupervisorAgent>();



/**
 * starting a supervisor agents	
 * @param objets
 * @return
 */
public String startAgentSupervisor(Navire nav){
	System.out.println("Navire : "+nav.getType());
				try {
					SupervisorAgent agent = new SupervisorAgent();
					initAndRun(agent, "Surveillant "+nav.getType()+" : "+nav.getAIS(), new Object[]{nav});
					listeAgentNavire.put(nav.getAIS(), agent);					
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return "the begin is done";
	}
	
private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
			
			
	    
	    AgentController controller= Controleur.container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
	    }
	

	
	public void updateAll(ArrayList<ObjetAffichable> listeObjets, int step) {
		if(step == 1){
			for(int i=0; i<listeObjets.size();i++){
				startAgentSupervisor((Navire)listeObjets.get(i));
			}
		}else{
			for(int i=0; i<listeObjets.size();i++){
				Navire nav = (Navire)listeObjets.get(i);
				if(listeAgentNavire.containsKey(nav.getAIS())){
					((SupervisorAgent)listeAgentNavire.get(nav.getAIS())).setNavire(nav);
				}else{
					startAgentSupervisor(nav);				
				}
			}
		}
	}



	@Override
	protected void onTick() {
			
	
		
		
	}
	
	
	
}
