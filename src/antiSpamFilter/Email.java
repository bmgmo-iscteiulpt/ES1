/*
 * 
 */
package antiSpamFilter;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Email.
 */
public class Email {

	/** The id. */
	private String id;
	
	/** The rules index. */
	private ArrayList<Integer> rulesIndex;
	
	/**
	 * Instantiates a new email.
	 *
	 * @param id the id
	 */
	public Email(String id) {
		this.id = id;
		this.rulesIndex = new ArrayList<Integer>();
	}

	/**
	 * Gets the rules index.
	 *
	 * @return the rules index
	 */
	public ArrayList<Integer> getRulesIndex() {
		return rulesIndex;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Adds the rule index.
	 *
	 * @param j the j
	 */
	public void addRuleIndex(int j) {
		rulesIndex.add(j);
		
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}
