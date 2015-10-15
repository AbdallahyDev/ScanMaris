package simulation;

import javax.swing.event.EventListenerList;

import visualisation.Vue;

/**
 * La SITU simule la perception de l'environnement, represente par une Situation.
 * Tous les pas de simulation, elle met en place une nouvelle situation 
 * a partir d'un scenario lu par une instance de la classe {@link LecteurScenario} et previent l'ihm.
 * @author riviere
 *
 */
public class SITU implements Runnable {

	private String scenario="./Ressources/scenario.xml";
	private LecteurScenario reader;
	private int simulationStep;
	private int simulationTime;
	private EventListenerList listeners;
	private boolean running;
	private boolean pause;

	public SITU(Vue ihm, int simulationTime, int echelle, int firstStep){
		listeners=new EventListenerList();
		this.addIhmListener(ihm);
		simulationStep=firstStep;
		reader=new LecteurScenario(scenario,echelle);
		reader.chargerScenario();
		this.simulationTime=simulationTime;
		running=true;
		pause=false;
	}


	private Situation playScenario() {
		// Lire le scenario au temps simulationStep
		Situation situ=reader.getSituation(getSimulationStep());

		// Incrementer le pas de simulation
		setSimulationStep(getSimulationStep()+1);
		return situ;
	}

	/**
	 * Tous les pas de simulation,
	 * met en place une nouvelle Situation, et transmet
	 * la mise a jour a l'ihm 
	 */
	@Override
	public void run() {
		long start;
		long end;		

		while((reader.hasNext(getSimulationStep()))&&(running)){

			start=System.currentTimeMillis();
			Situation situation=this.playScenario();

			// Prevenir d'une mise a jour des objets affichables
			for(final SITUlistener listen : listeners.getListeners(SITUlistener.class)){
				listen.updateAll(situation.getObjetsAffichables(), getSimulationStep());
			}

			end=System.currentTimeMillis();
			try {
				Thread.sleep(simulationTime-(end-start));
			} catch (InterruptedException e) {
				e.printStackTrace();	
			}

			while(isPause()){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}}

		}


	}

	private void addIhmListener(Vue ihm) {
		listeners.add(SITUlistener.class, ihm);			
	}

	public int getSimulationStep() {
		return simulationStep;
	}


	public void setSimulationStep(int simulationStep) {
		this.simulationStep = simulationStep;
	}


	public void stop() {
		this.setRunning(false);		
	}


	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		this.running = running;
	}


	public boolean isPause() {
		return pause;
	}


	private void setPause(boolean pause) {
		this.pause = pause;
	}

	public void pause(){
		setPause(!pause);
	}


}
