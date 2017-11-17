package antiSpamFilter;

import java.util.ArrayList;

public class Email {

	private String id;
	private ArrayList<String> rules;
	private ArrayList<Integer> rulesIndex;
	
	public Email(String id) {
		this.id = id;
		this.rules = new ArrayList<String>();
		this.rulesIndex = new ArrayList<Integer>();
	}

	public ArrayList<String> getRules() {
		return rules;
	}

	public ArrayList<Integer> getRulesIndex() {
		return rulesIndex;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void addRule(String rule) {
		rules.add(rule);
	}

	public void addRuleIndex(int j) {
		rulesIndex.add(j);
		
	}
}
