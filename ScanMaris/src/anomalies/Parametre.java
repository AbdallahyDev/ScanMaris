package anomalies;

/**
 * Un Parametre appartient a une famille de parametre (@see {@link FamilleParametres})
 * (par exemple, les parametres de l'anomalie Stop appartiennent a la famille de nom Anomalie et de surnom Stop)
 * Un Parametre a un nom (typiquement "valeur initiale", "de croissance" etc. @see {@link MAN} pour plus de details)
 * et une valeur. 
 * @author riviere
 *
 */
public class Parametre {
	private String name;
	private double value;

	public Parametre(String n, double v){
		name=n;
		value=v;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public String toString(){
		return "Parametre "+name+" "+value;
	}


}
