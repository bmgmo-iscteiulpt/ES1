/*
 * 
 */
package antiSpamFilter;

/**
 * The Class Rule.
 */
public class Rule {

	/** The name. */
	private String name;
	
	/** The peso. */
	private double peso;
	
	/**
	 * Instantiates a new rule.
	 *
	 * @param name the name
	 */
	public Rule(String name) {
		this.name = name;
	}

	public Rule(String name, double peso) {
		this.name=name;
		this.peso=peso;
	}

	/**
	 * Gets the peso.
	 *
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Sets the peso.
	 *
	 * @param peso the new peso
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
