/*
 * 
 */
package antiSpamFilter;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * Classe Email.
 */
public class Email {

	/** O id. */
	private String id;
	
	/** Lista de index's das regras presentes nos emails */
	private ArrayList<Integer> rulesIndex;
	
	/**
	 * Instancia um novo Email.
	 *
	 * @param id do email
	 */
	public Email(String id) {
		this.id = id;
		this.rulesIndex = new ArrayList<Integer>();
	}

	/**
	 * Retorna a lista de indexs
	 *
	 * @return a lista de indexs
	 */
	public ArrayList<Integer> getRulesIndex() {
		return rulesIndex;
	}

	/**
	 * Define o id
	 *
	 * @param id do email
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Adiciona um index á lista 
	 *
	 * @param index j
	 */
	public void addRuleIndex(int j) {
		rulesIndex.add(j);
		
	}

	/**
	 * Retorna o id
	 *
	 * @return o id
	 */
	public String getId() {
		return id;
	}
}
