package anomalies;

import java.util.HashMap;

import anomalies.Parametre;

/**
 * FamilleParametres est le niveau au-dessus de Parametre (@see {@link Parametre})
 * Un objet de cette classe a un nom qui peut etre de type anomalie, marque ou navire, 
 * suivant les parametres manipules (@see {@link MAN} pour plus de details)
 * Il contient l'ensemble des parametres d'une meme famille, regoupes par type.
 * @author riviere
 * 
 */
public class FamilleParametres {
	private LecteurParametres.Type name;
	private String nickName;
	private HashMap<String, Parametre> member;

	public FamilleParametres(LecteurParametres.Type name, String nickName){
		this.name=name;
		this.nickName=nickName;
	}

	public LecteurParametres.Type getName() {
		return name;
	}

	public void setName(LecteurParametres.Type name) {
		this.name = name;
	}

	public HashMap<String, Parametre> getMembers() {
		return member;
	}

	public void setMember(HashMap<String, Parametre> member) {
		this.member = member;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String toString(){
		return name+" "+nickName+" nb member : "+member.size();
	}


}
