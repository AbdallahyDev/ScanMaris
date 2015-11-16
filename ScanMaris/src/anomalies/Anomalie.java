package anomalies;

import java.util.Date;
import java.util.HashMap;

/**
 * Une Anomalie est d'un certain type et a une valeur initiale,
 * une valeur de croissance, une valeur de decroissance et peut etre en cours ou pas.
 * Tous ces parametres sont lus depuis le fichier parameters.xml charge par la classe @see {@link LecteurParametres}
 * @author riviere
 *
 */
public class Anomalie{
	private double initValue;
	private double decreasingValue;
	private double increasingValue;
	private boolean stopped=false;
	private String type;
	private Date generationDate = null;
	private Date endDate=null;
	private double lastValue=-1;

	public Anomalie(String type,double initValue,double creasingValue,double decreasingValue){
		this.type=type;
		this.initValue=initValue;
		this.decreasingValue=decreasingValue;
		this.increasingValue=creasingValue;		
		this.setGenDate(new Date());
	}

	public void setGenDate(Date date){
		generationDate = date;
	}

	public double getLastValue() {
		return lastValue;
	}

	public void setLastValue(double lastValue) {
		this.lastValue = lastValue;
	}

	public HashMap<String, Double> getAllAttributes(){
		HashMap<String, Double> hash=new HashMap<String, Double>();
		hash.put("InitValue", getInitValue());
		hash.put("IncreaseValue", getIncreasingValue());
		hash.put("DecreaseValue", getDecreasingValue());
		return hash;		

	}

	public Date getDate(){
		return generationDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getInitValue() {
		return initValue;
	}

	public void setInitValue(double initValue) {
		this.initValue = initValue;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean isStopped) {
		this.stopped = isStopped;
		if(isStopped){
			this.setEndDate(new Date());
		}
	}

	public double getDecreasingValue() {
		return decreasingValue;
	}

	public void setDecreasingValue(double decreasingValue) {
		this.decreasingValue = decreasingValue;
	}

	public double getIncreasingValue() {
		return increasingValue;
	}

	public void setIncreasingValue(double increasingValue) {
		this.increasingValue = increasingValue;
	}

	/**
	 * Calcule la prochaine valeur de l'anomalie
	 * Si elle est en cours, sa prochaine valeur sera sa precedente + sa valeur de croissance,
	 * sinon sa prochaine valeur sera sa precedente + sa valeur de decroissance.
	 * @return la prochaine valeur de l'anomalie
	 */
	public double getValueAtNextStep(){
		double result=0;
		if(getLastValue()==-1){
			//first step
			setLastValue(getInitValue());
			result=getLastValue();
		}
		else{
			if(!isStopped()){
				result=getLastValue()+getIncreasingValue();
			}
			else{
				result=getLastValue()-getDecreasingValue();
			}
			if(result<0) result=0;
			setLastValue(result);
		}
		return result;
	}

	public String toString(){
		return "Anomalie "+getType()+" generee a "+getDate()+" en cours ? "+(!isStopped())+" Valeur = "+getLastValue();
	}

	public boolean equals(Object o){
		Anomalie a=null;
		if(o instanceof Anomalie) a=(Anomalie)o;
		else return false;
		boolean type=this.getType().equalsIgnoreCase(a.getType());
		return (type);
	}


}
