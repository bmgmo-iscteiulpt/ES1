package antiSpamFilter;

public class Main {

	public static void main(String[] args) {
		Controller c = new Controller();
		c.setRulesPath("rules.cf");
		c.readRules();

	}

}
