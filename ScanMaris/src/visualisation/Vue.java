package visualisation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import simulation.SITUlistener;
import surveillance.AdministratorAgent;
import controle.Controleur;

/**
 * Vue est la fenetre principale de l'ihm de visualisation
 * Elle contient le menu et est liee au controleur
 * Elle implemente {@link SITUlistener}, et est donc a l'ecoute des mises a jour de la SITU
 * @author riviere
 *
 */
public class Vue extends JFrame implements SITUlistener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5242005945232250130L;
	private final Environnement ihm;
	private final Controleur controleur;

	private boolean pause=false;
	AdministratorAgent gestion;
	private final JButton pauseB;
	private final static String title="Situation en temps reel";


	public Vue(Controleur c,int largeur, int hauteur, int tempsSimulation){
		super(title);
		controleur=c;
		ihm=new Environnement(largeur,hauteur,"/background2.jpg",tempsSimulation);
		gestion=new AdministratorAgent();

		// Creation d'un menu pour l'ihm
		JMenuBar bar=new JMenuBar();
		pauseB=new JButton("Mettre en pause");
		pauseB.setEnabled(false);
		JButton stopB=new JButton("Arreter la simulation");
		pauseB.setMnemonic(KeyEvent.VK_SPACE);
		stopB.setMnemonic(KeyEvent.VK_ESCAPE);
		pauseB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controleur.pause();				
				setPause(!isPause());
				if(isPause()){
					pauseB.setText("Reprendre");
				}
				else pauseB.setText("Mettre en pause");				
			}
		});

		stopB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controleur.stop();
			}
		});

		bar.add(pauseB);
		bar.add(stopB);
		bar.setVisible(true);
		this.add(ihm);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				controleur.stop();			
			}
		});

		this.setResizable(false);
		this.setVisible(true);
		this.setJMenuBar(bar);
		addMouse();
		this.pack();
	}

	private void addMouse(){
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(arg0.getX()+","+arg0.getY());
			}
		});

	}

	/**
	 * Met a jour l'ensemble des objets affichables en une seule fois
	 * Methode qui implemente l'interface SITUlistener
	 */
	@Override
	public void updateAll(ArrayList<ObjetAffichable> objets, int step) {
		//System.out.println("ça marche");
		
		
		pauseB.setEnabled(true);
		this.setTitle(title+" : pas "+step);
		ihm.updateAll(objets);		
	
		//gestion.start(objets);
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

}
