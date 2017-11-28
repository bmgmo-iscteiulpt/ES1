/*
 * 
 */
package antiSpamFilter;

import java.util.ArrayList;

/**
 * The Class Email.
 */
public class Email {

	/** The id. */
	private String id;
	
	/** The rules. */
	private ArrayList<String> rules;
	
	/** The rules index. */
	private ArrayList<Integer> rulesIndex;
	
	/**
	 * Instantiates a new email.
	 *
	 * @param id the id
	 */
	public Email(String id) {
		this.id = id;
		this.rules = new ArrayList<String>();
		this.rulesIndex = new ArrayList<Integer>();
	}

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public ArrayList<String> getRules() {
		return rules;
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
	 * Adds the rule.
	 *
	 * @param rule the rule
	 */
	public void addRule(String rule) {
		rules.add(rule);
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
