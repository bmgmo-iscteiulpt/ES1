package antiSpamFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

	private String rulesPath;
	private String hamPath;
	private String spamPath;
	private ArrayList<Rule> rules;
	private ArrayList<Email> ham;
	private ArrayList<Email> spam;
	private static Controller instance = null;
	private int count;

	protected Controller() {
		this.rules = new ArrayList<>();
		this.ham = new ArrayList<>();
		this.spam = new ArrayList<>();
		this.count=0;
	}

	// Controller definido como singleton
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();

		}
		return instance;
	}

	// Defini��o caminho do ficheiro rules.cf
	public void setRulesPath(String path) {
		this.rulesPath = path;
	}

	// Defini��o caminho do ficheiro ham.txt
	public void setHamPath(String path) {
		this.hamPath = path;
	}

	// Defini��o caminho do ficheiro spam.txt
	public void setSpamPath(String path) {
		this.spamPath = path;
	}

	// Leitura do ficheiro rules.cf, cria��o de Rules e adi��o ao vetor
	public void readRules() {
		try {
			rules.clear();
			FileReader ficheiro = new FileReader(this.rulesPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				Rule r = new Rule(linha);
				rules.add(r);
				linha = leitor.readLine();
			}
			System.out.println("Regras lidas" + rules.size());
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	//Leitura dos pesos gerados pelo algoritmo
	public void readNSGAII() {
		try {
			String currentDirectory = new File("").getAbsolutePath();
			FileReader ficheiro = new FileReader(currentDirectory+"/experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			String[] tokens = linha.split(" ");
			for(int i = 0 ;i< tokens.length;i++) {
				rules.get(i).setPeso(Double.valueOf(tokens[i]));
			}
			System.out.println("Regras lidas" + rules.size());
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

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
//				for (int i = 1; i < tokens.length; i++) {
//					e.addRule(tokens[i]);
//				}
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
//				for (int i = 1; i < tokens.length; i++) {
//					e.addRule(tokens[i]);
//				}
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

	// Preenchimento da tabela das regras
	public String[][] preencherTabela() {
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

	// Consulta do peso de uma determinada regra
	
//	public double getPeso(String rule) {
//		a++;
//		for (Rule r : rules) {
//			if (r.getName().equals(rule))
//				return r.getPeso();
//		}
//		return 0;
//	}

	// C�lculo de Falsos Positivos recorrendo aos emails Ham considerados SPAM (
	// somat�rio > 5)
	public int calcularFP() {
		int falsosPositivos = 0;

		for (Email e : ham) {
			int somat�rio = 0;

//			for (String regra : e.getRules()) {
//				somat�rio += getPeso(regra);
//			}
			
			for (int index : e.getRulesIndex()) {
				somat�rio += rules.get(index).getPeso();
			}
			if (somat�rio > 5)
				falsosPositivos++;
		}
		return falsosPositivos;

	}

	// C�lculo de Falsos Negativos recorrendo aos emails Spam considerados HAM (
	// somat�rio < 5)
	public int calcularFN() {
		int falsosNegativos = 0;

		for (Email e : spam) {
			int somat�rio = 0;

//			for (String regra : e.getRules()) {
//				somat�rio += getPeso(regra);
//			}
			for (int index : e.getRulesIndex()) {
				somat�rio += rules.get(index).getPeso();
			}
			if (somat�rio <= 5)
				falsosNegativos++;
		}
		return falsosNegativos;
	}

	//Gerador de pesos aleat�rios entre -5 e 5 para cada regra
	public void pesosAleatorios() {
		for (Rule r : rules) {
			r.setPeso(ThreadLocalRandom.current().nextDouble(-5, 5));
		}
	}

	// Atribui��o dos pesos provenientes do vetor criado pelo algoritmo a cada regra
	public void pesosAlgoritmo(double[] pesos) {
		for (int i = 0; i < pesos.length; i++) {
			rules.get(i).setPeso(pesos[i]);
		}
	}

	//Verifica se os caminhos para os 3 ficheiros se encontra definido
	public boolean ficheirosDef() {
		if (rulesPath == null || hamPath == null || spamPath == null) {
			return false;
		} else {
			return true;
		}
	}

	//Conta o n�mero de vezes que o algoritmo testou uma configura��o diferente
	public void count() {
		count++;
		
	}

	//Converte o n�mero total de testes em percentagem para apresenta��o na GUI
	public String getCount() {
		int a = count/1250;
		return a+"%";
	}
	
}
