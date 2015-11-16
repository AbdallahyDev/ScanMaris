package surveillance;


import java.util.ArrayList;

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



	//private int b=1;
	Vue vue;
	boolean b=true;
	private static ArrayList<ObjetAffichable> objets;
	private static int step;
	private int lastSize=0;

	//private static AgentContainer container;
	/*@Override
	public void action() {
		
		
		if(getStep()!=0){
		    
			
			
			if(isB()){
				System.out.println("I'm ready to call startAgent");
				start(getObjets());
				lastSize=getObjets().size();
				setB(false);	
			}
			if((getObjets().size())>lastSize){
				
				setB(true);
			}
		}
			
	
		
	}
	*/
	
	
	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public ArrayList<ObjetAffichable> getObjets() {
		return objets;
	}

	public void setObjets(ArrayList<ObjetAffichable> objets) {
		this.objets = objets;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}


/**
 * starting a supervisor agents	
 * @param objets
 * @return
 */
public String start(ArrayList<ObjetAffichable> objets){
	
	    	
		for(int i=0; i<objets.size();i++){
						
				try {
					initAndRun(new SupervisorAgent(), "Surveillant "+i, new Object[]{objets.get(i)});
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		return "the begin is done";
	}
	
private void initAndRun(Agent agent, String nickname, Object parametre[]) throws StaleProxyException{
			
			
	    
	    AgentController controller= Controleur.container.acceptNewAgent(nickname, agent);
	    agent.setArguments(parametre);
	    controller.start();
	    
	    }
	

	
	public void updateAll(ArrayList<ObjetAffichable> objets, int step) {
		// TODO Auto-generated method stub
		System.out.println("ça marche à partir de Behaviour");
	}



	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		//System.out.println("I'm ready to call startAgent");
		if(isB()){
			System.out.println("I'm ready to call startAgent");
			start(getObjets());
			lastSize=getObjets().size();
			setB(false);	
		}
		if((getObjets().size())>lastSize){
			
			setB(true);
		}
		
		
	}
	
	
	
}
