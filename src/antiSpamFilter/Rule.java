package antiSpamFilter;

import java.io.Serializable;

public class Rule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
