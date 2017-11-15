package antiSpamFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

	private String rulesPath;
	private String hamPath;
	private String spamPath;
	private ArrayList<Rule> rules;
	private ArrayList<Email> ham;
	private ArrayList<Email> spam;
	private static Controller instance = null;
	
	protected Controller() {
		this.rules = new ArrayList<>();
		this.ham = new ArrayList<>();
		this.spam = new ArrayList<>();
	   }
	   public static Controller getInstance() {
	      if(instance == null) {
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
			FileReader ficheiro = new FileReader(this.rulesPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				Rule r = new Rule(linha);
				rules.add(r);
				linha = leitor.readLine();
			}
			System.out.println("Regras lidas"+ rules.size());
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	public void readNSGAII() {
		try {
			FileReader ficheiro = new FileReader("/experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				Rule r = new Rule(linha);
				rules.add(r);
				linha = leitor.readLine();
			}
			System.out.println("Regras lidas"+ rules.size());
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
	
	// Leitura do ficheiro ham.txt, cria��o de Emails Ham e adi��o ao vetor

	public void readHam() {
		try {
			FileReader ficheiro = new FileReader(this.hamPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				String[] tokens = linha.split("\\s+");
				Email e = new Email(tokens[0]);
				for (int i = 1; i < tokens.length; i++) {
					e.addRule(tokens[i]);
				}
				ham.add(e);
				linha = leitor.readLine();
			}
			System.out.println("Ham lido");
			ficheiro.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

	// Leitura do ficheiro spam.txt, cria��o de Emails Spam e adi��o ao vetor

	public void readSpam() {
		try {
			FileReader ficheiro = new FileReader(this.spamPath);
			BufferedReader leitor = new BufferedReader(ficheiro);
			String linha = leitor.readLine();
			while (linha != null) {
				String[] tokens = linha.split("\\s+");
				Email e = new Email(tokens[0]);
				for (int i = 1; i < tokens.length; i++) {
					e.addRule(tokens[i]);
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

	public double getPeso(String rule) {
		for (Rule r : rules) {
			if (r.getName().equals(rule))
				return r.getPeso();
		}
		return 0;
	}

	// C�lculo de Falsos Positivos recorrendo aos emails Ham considerados SPAM (
	// somat�rio > 5)

	public int calcularFP() {
		int falsosPositivos = 0;

		for (Email e : ham) {
			int somat�rio = 0;

			for (String r : e.getRules()) {
				somat�rio += getPeso(r);
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

			for (String r : e.getRules()) {
				somat�rio += getPeso(r);
			}
			if (somat�rio <= 5)
				falsosNegativos++;
		}
		return falsosNegativos;
	}

	public void pesosAleatorios() {
		for(Rule r : rules) {
			r.setPeso(ThreadLocalRandom.current().nextDouble(-5,5));
		}
	}
	public void pesosAlgoritmo(double[] pesos) {
		for(int i = 0 ; i < pesos.length ; i++) {
			rules.get(i).setPeso(pesos[i]);
		}
	}
	
	public boolean ficheirosDef() {
		if(rulesPath==null || hamPath==null || spamPath==null) {
			return false;
		}
		else {
			return true;
		}
	}
}
