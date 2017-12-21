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

// TODO: Auto-generated Javadoc
/**
 * A classe Controller.
 */
public class Controller {

	/** A instância da classe. */
	private static Controller instance = null;

	/** O caminho para o ficheiro rules. */
	private String rulesPath;

	/** O caminho para o ficheiro ham. */
	private String hamPath;

	/** O caminho para o ficheiro Spam. */
	private String spamPath;

	/** Lista de regras */
	private ArrayList<Rule> rules;

	/** Lista de emails ham */
	private ArrayList<Email> ham;

	/** Lista de emails spam. */
	private ArrayList<Email> spam;

	/** Contador de iterações do algoritmo. */
	private int count;

	/**
	 *Instancia um controlador
	 */
	protected Controller() {
		this.rules = new ArrayList<>();
		this.ham = new ArrayList<>();
		this.spam = new ArrayList<>();
		this.count = 0;
	}

	/**
	 * Controller definido como singleton
	 *
	 * @return Instancia unica do controlador
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	// Definição caminho do ficheiro rules.cf
	/**
	 * Define o caminho para o ficheiro rules.cf.
	 *
	 * @param Caminho para o ficheiro rules.cf
	 * 
	 */
	public void setRulesPath(String path) {
		this.rulesPath = path;
	}

	/**
	 * Define o caminho para o ficheiro ham.log.
	 *
	 * @param Caminho para o ficheiro ham.log
	 */
	public void setHamPath(String path) {
		this.hamPath = path;
	}

	/**
	 * Define o caminho para o ficheiro spam.log
	 *
	 * @param Caminho para o ficheiro spam.log
	 */
	public void setSpamPath(String path) {
		this.spamPath = path;
	}
 
	/**
	 *Leitura do ficheiro rules.cf, criação de Rules e adição ao vetor
	 *
	 * @param tipo de leitura (novo ficheiro ou ficheiro já existente)
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
			
				else {
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
	 * Altera o contador .
	 *
	 * @param define o contador
	 */
	public void setCount(int count) {
		this.count = count;
	}

	// Leitura dos pesos gerados pelo algoritmo
	/**
	  Lê o ficheiros gerados pelo algoritmo NSGAII.
	 *
	 * @param filePesos the file pesos
	 * @param fileResultados the file resultados
	 */
	public void readNSGAII(String filePesos,String fileResultados) {
		try {
			ArrayList<Double> FPs = new ArrayList<Double>();

			String currentDirectory = new File("").getAbsolutePath();
			FileReader ficheiro = new FileReader(currentDirectory+"/experimentBaseDirectory/referenceFronts/"+filePesos);
			System.out.println(currentDirectory+"/experimentBaseDirectory/referenceFronts/"+fileResultados);
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

			ficheiro = new FileReader(currentDirectory+"/experimentBaseDirectory/referenceFronts/"+fileResultados);
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
	 * Leitura do ficheiro ham.log, criação de Emails Ham e adição ao vetor
	 */
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
	 * Devolve o caminho do ficheiro rules.cf
	 *
	 * @return the rules path
	 */
	public String getRulesPath() {
		return rulesPath;
	}

	/**
	 * Devolve o caminho do ficheiro ham.log
	 *
	 * @return the ham path
	 */
	public String getHamPath() {
		return hamPath;
	}

	/**
	 * Devolve o caminho do ficheiro spam.log
	 *
	 * @return the spam path
	 */
	public String getSpamPath() {
		return spamPath;
	}

	/**
	 * Leitura do ficheiro spam.txt, criação de Emails Spam e adição ao vetor.
	 */
	
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
	 * Devole os dados da tabela.
	 *
	 * @return dados tabela
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

	// Cálculo de Falsos Positivos recorrendo aos emails Ham considerados SPAM (
	/**
	 * Calcular FP.
	 *
	 * @return the int
	 */
	// somatório > 5)
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

	// Cálculo de Falsos Negativos recorrendo aos emails Spam considerados HAM (
	/**
	 * Calcular FN.
	 *
	 * @return the int
	 */
	// somatório < 5)
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
	 * Devolve a lista das regras
	 *
	 * @return the rules
	 */
	public ArrayList<Rule> getRules() {
		return rules;
	}

	/**
	 * Pesos aleatorios.
	 */
	// Gerador de pesos aleatórios entre -5 e 5 para cada regra
	public void pesosAleatorios() {
		for (Rule r : rules) {
			r.setPeso(ThreadLocalRandom.current().nextDouble(-5, 5));
		}
	}

	/**
	 * Pesos manuais.
	 *
	 * @param row the row
	 * @param value the value
	 */
	public void pesosManuais(int row, String value) {
		rules.get(row).setPeso(Double.valueOf(value));
	}

	/**
	 * Pesos algoritmo.
	 *
	 * @param pesos the pesos
	 */
	// Atribuição dos pesos provenientes do vetor criado pelo algoritmo a cada regra
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
	// Conta o número de vezes que o algoritmo testou uma configuração diferente
	public void count() {
		count++;

	}

	/**
	 * Devolve a percentagem de conclusão calculada
	 *
	 * @return the percentage
	 */
	// Converte o número total de testes em percentagem para apresentação na GUI
	public String getPercentage() {
		int a = count / 50;
		return a + "%";
	}

	/**
	 * Guardar pesos.
	 */
	public void guardarPesos() {
		try {
			PrintWriter writer = new PrintWriter(getRulesPath(), "UTF-8");
			for (Rule r : rules) {
				writer.println(r.getName() + " " + r.getPeso());
			}
			
			writer.close();
		} catch (Exception e) {
			System.out.println("Erro ao guardar os pesos");
		}
	}

	/**
	 * Devolve a lista de emails ham
	 *
	 * @return the ham
	 */
	public ArrayList<Email> getHam() {
		return ham;
	}

	/**
	 * Devolve a lista de emails spam
	 *
	 * @return the spam
	 */
	public ArrayList<Email> getSpam() {
		return spam;
	}

	/**
	 * Devolve o valor atual do contador
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Limpa a lista de regras
	 */
	public void clearRules() {
		rules.clear();
		
	}

	/**
	 * Verifica se a lista de regras está preenchida.
	 *
	 * @return true, if is rulesdef
	 */
	public boolean isRulesdef() {
		if(!rules.isEmpty())
			return true;
		else
			return false;
	}
}

