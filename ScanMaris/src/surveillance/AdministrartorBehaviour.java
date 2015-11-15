package surveillance;


import java.util.ArrayList;

import controle.Controleur;
import simulation.SITUlistener;
import visualisation.ObjetAffichable;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class AdministrartorBehaviour extends OneShotBehaviour implements SITUlistener{

	private int b=1;
	//private static AgentContainer container;
	@Override
	public void action() {
		// TODO Auto-generated method stub
		try {
			initAndRun(new SupervisorAgent(), "Surveillant", new Object[]{"Supervisor!"});
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(b){
		
		case 1: System.out.println("I'm Administrator agent and I'm starting "+b);b++;  break;
		case 2: System.out.println("I'm starting"+b);	break;
		default: break;
		}
		//System.out.println("I'm starting");
		
	}
	
private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
			
			
	    
	    AgentController controller= Controleur.container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
	    }
	

	@Override
	public void updateAll(ArrayList<ObjetAffichable> objets, int step) {
		// TODO Auto-generated method stub
		System.out.println("ça marche à partir de Behaviour");
	}
	
	/*public boolean done() {
		//b=b+1;
		return (b==2);
	}*/
	
	
}
