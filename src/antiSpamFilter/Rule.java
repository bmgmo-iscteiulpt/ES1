/*
 * 
 */
package antiSpamFilter;

// TODO: Auto-generated Javadoc
/**
 * Classe Rule.
 */
public class Rule {

	/** O nome */
	private String name;
	
	/** O peso. */
	private double peso;
	
	/**
	 * Instanciação de nova regra.
	 *
	 * @param Nome da regra
	 */
	public Rule(String name) {
		this.name = name;
	}

	/**
	 * Instanciação de nova regra com peso já definido
	 *
	 * @param Nome da regra
	 * @param Peso da regra
	 */
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
