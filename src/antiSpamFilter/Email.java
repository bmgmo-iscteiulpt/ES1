package antiSpamFilter;

import java.util.ArrayList;

public class Email {

	private String id;
	private ArrayList<String> rules;
	
	public Email(String id) {
		this.id = id;
		this.rules = new ArrayList<String>();
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void addRule(String rule) {
		rules.add(rule);
	}
}
