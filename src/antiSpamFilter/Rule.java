package antiSpamFilter;

public class Rule {

	private String name;
	private double peso;
	
	public Rule(String name) {
		this.name = name;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getName() {
		return name;
	}
	
	
}
