/*
 * 
 */
package antiSpamFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Class Controller.
 *
 * @author Bruno, Soraia, Joana, Tom�s
 */
public class Controller {

	/** The instance. */
	private static Controller instance = null;

	/** The rules path. */
	private String rulesPath;

	/** The ham path. */
	private String hamPath;

	/** The spam path. */
	private String spamPath;

	/** The rules. */
	private ArrayList<Rule> rules;

	/** The ham. */
	private ArrayList<Email> ham;

	/** The spam. */
	private ArrayList<Email> spam;

	/** The count. */
	private int count;

	/**
	 * Instantiates a new controller.
	 *
	 * @
	 */
	protected Controller() {
		this.rules = new ArrayList<>();
		this.ham = new ArrayList<>();
		this.spam = new ArrayList<>();
		this.count = 0;
	}

	// Controller definido como singleton
	/**
	 * Gets the single instance of Controller.
	 *
	 * @return single instance of Controller
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	// Defini��o caminho do ficheiro rules.cf
	/**
	 * Sets the rules path.
	 *
	 * @param path
	 *            the new rules path
	 */
	public void setRulesPath(String path) {
		this.rulesPath = path;
	}

	// Defini��o caminho do ficheiro ham.txt
	/**
	 * Sets the ham path.
	 *
	 * @param path
	 *            the new ham path
	 */
	public void setHamPath(String path) {
		this.hamPath = path;
	}

	// Defini��o caminho do ficheiro spam.txt
	/**
	 * Sets the spam path.
	 *
	 * @param path
	 *            the new spam path
	 */
	public void setSpamPath(String path) {
		this.spamPath = path;
	}

	// Leitura do ficheiro rules.cf, cria��o de Rules e adi��o ao vetor
	/**
	 * Read rules.
	 */
	public void readRules(String tipo) {
		rules.clear();
		try {
			FileReader ficheiro = new FileReader(this.rulesPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				String[] tokens = linha.split("\\s+");
				if(tipo.equals("novoFicheiro")) {
					Rule r = new Rule(tokens[0]);
					rules.add(r);
				}
			
				if(tipo.equals("abrirFichero")) {
				Rule r = new Rule(tokens[0],Double.valueOf(tokens[1]));
				rules.add(r);
				}
				
				linha = leitor.readLine();
			}
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	/**
	 * Sets the count.
	 *
	 * @param count
	 *            the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	// Leitura dos pesos gerados pelo algoritmo
	/**
	 * Read NSGAII.
	 */
	public void readNSGAII(String filePesos,String fileResultados) {
		try {
			ArrayList<Double> FPs = new ArrayList<Double>();

			String currentDirectory = new File("").getAbsolutePath();
			FileReader ficheiro = new FileReader(currentDirectory+"/experimentBaseDirectory/referenceFronts/"+fileResultados);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				String[] tokens = linha.split(" ");
				FPs.add(Double.valueOf(tokens[0]));
				linha = leitor.readLine();
			}
			ficheiro.close();
			double resultado = 0;
			int index = 0;
			for (int i = 0; i < FPs.size(); i++) {
				if (FPs.get(i) > resultado) {
					resultado = FPs.get(i);
					index = i;
				}
			}

			ficheiro = new FileReader(currentDirectory+"/experimentBaseDirectory/referenceFronts/"+filePesos);
			leitor = new BufferedReader(ficheiro);
			for (int i = 0; i < index + 1; i++)
				linha = leitor.readLine();
			String[] tokens = linha.split(" ");
			for (int i = 0; i < tokens.length; i++) {
				rules.get(i).setPeso(Double.valueOf(tokens[i]));
			}
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	/**
	 * Read ham.
	 */
	// Leitura do ficheiro ham.txt, cria��o de Emails Ham e adi��o ao vetor
	public void readHam() {
		try {
			ham.clear();
			FileReader ficheiro = new FileReader(this.hamPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				String[] tokens = linha.split("\\s+");
				Email e = new Email(tokens[0]);
				// for (int i = 1; i < tokens.length; i++) {
				// e.addRule(tokens[i]);
				// }
				for (int i = 1; i < tokens.length; i++) {
					for (int j = 0; j < rules.size(); j++) {
						if (rules.get(j).getName().equals(tokens[i])) {
							e.addRuleIndex(j);
						}
					}
				}

				ham.add(e);
				linha = leitor.readLine();
			}
			System.out.println("Ham lido");

			ficheiro.close();
		} catch (

		IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	/**
	 * Gets the rules path.
	 *
	 * @return the rules path
	 */
	public String getRulesPath() {
		return rulesPath;
	}

	/**
	 * Gets the ham path.
	 *
	 * @return the ham path
	 */
	public String getHamPath() {
		return hamPath;
	}

	/**
	 * Gets the spam path.
	 *
	 * @return the spam path
	 */
	public String getSpamPath() {
		return spamPath;
	}

	/**
	 * Read spam.
	 */
	// Leitura do ficheiro spam.txt, cria��o de Emails Spam e adi��o ao vetor
	public void readSpam() {
		try {
			spam.clear();
			FileReader ficheiro = new FileReader(this.spamPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				String[] tokens = linha.split("\\s+");
				Email e = new Email(tokens[0]);
				// for (int i = 1; i < tokens.length; i++) {
				// e.addRule(tokens[i]);
				// }
				for (int i = 1; i < tokens.length; i++) {
					for (int j = 0; j < rules.size(); j++) {
						if (rules.get(j).getName().equals(tokens[i])) {
							e.addRuleIndex(j);
						}
					}
				}
				spam.add(e);
				linha = leitor.readLine();
			}
			System.out.println("Spam lido");
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	/**
	 * Gets the dados tabela.
	 *
	 * @return the dados tabela
	 */
	// Preenchimento da tabela das regras
	public String[][] getDadosTabela() {
		if (rules.isEmpty()) {
			String[][] dados = new String[][] {};
			return dados;
		} else {
			DecimalFormat df = new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.CEILING);

			String[][] dados = new String[rules.size()][2];
			for (int i = 0; i < rules.size(); i++) {
				dados[i][0] = rules.get(i).getName();
				dados[i][1] = String.valueOf(df.format(rules.get(i).getPeso()));
			}
			return dados;
		}

	}

	// C�lculo de Falsos Positivos recorrendo aos emails Ham considerados SPAM (
	/**
	 * Calcular FP.
	 *
	 * @return the int
	 */
	// somat�rio > 5)
	public int calcularFP() {
		int falsosPositivos = 0;

		for (Email e : ham) {
			int somatorio = 0;

			for (int index : e.getRulesIndex()) {
				somatorio += rules.get(index).getPeso();
			}
			if (somatorio >= 5)
				falsosPositivos++;
		}
		return falsosPositivos;

	}

	// C�lculo de Falsos Negativos recorrendo aos emails Spam considerados HAM (
	/**
	 * Calcular FN.
	 *
	 * @return the int
	 */
	// somat�rio < 5)
	public int calcularFN() {
		int falsosNegativos = 0;

		for (Email e : spam) {
			int somatorio = 0;

			for (int index : e.getRulesIndex()) {
				somatorio += rules.get(index).getPeso();
			}
			if (somatorio < 5)
				falsosNegativos++;
		}
		return falsosNegativos;
	}

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	public ArrayList<Rule> getRules() {
		return rules;
	}

	/**
	 * Pesos aleatorios.
	 */
	// Gerador de pesos aleat�rios entre -5 e 5 para cada regra
	public void pesosAleatorios() {
		for (Rule r : rules) {
			r.setPeso(ThreadLocalRandom.current().nextDouble(-5, 5));
		}
	}

	/**
	 * Pesos manuais.
	 *
	 * @param row
	 *            the row
	 * @param value
	 *            the value
	 */
	public void pesosManuais(int row, String value) {
		rules.get(row).setPeso(Double.valueOf(value));
	}

	/**
	 * Pesos algoritmo.
	 *
	 * @param pesos
	 *            the pesos
	 */
	// Atribui��o dos pesos provenientes do vetor criado pelo algoritmo a cada regra
	public void pesosAlgoritmo(double[] pesos) {
		for (int i = 0; i < pesos.length; i++) {
			rules.get(i).setPeso(pesos[i]);
		}
	}

	/**
	 * Ficheiros def.
	 *
	 * @return true, if successful
	 */
	// Verifica se os caminhos para os 3 ficheiros se encontra definido
	public boolean ficheirosDef() {
		if (rulesPath == null || hamPath == null || spamPath == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Count.
	 */
	// Conta o n�mero de vezes que o algoritmo testou uma configura��o diferente
	public void count() {
		count++;

	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	// Converte o n�mero total de testes em percentagem para apresenta��o na GUI
	public String getPercentage() {
		int a = count / 50;
		return a + "%";
	}

	/**
	 * Guardar pesos.
	 */
	public void guardarPesos() {
		try {
			PrintWriter writer = new PrintWriter(getRulesPath()+"rules.cf", "UTF-8");
			for (Rule r : rules) {
				writer.println(r.getName() + " " + r.getPeso());
			}
			
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Gets the ham.
	 *
	 * @return the ham
	 */
	public ArrayList<Email> getHam() {
		return ham;
	}

	/**
	 * Gets the spam.
	 *
	 * @return the spam
	 */
	public ArrayList<Email> getSpam() {
		return spam;
	}

	public int getCount() {
		return count;
	}

}
