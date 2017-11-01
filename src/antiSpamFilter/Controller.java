package antiSpamFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

	private String rulesPath;
	private String hamPath;
	private String spamPath;
	private ArrayList <Rule> rules;
	private ArrayList <Email> ham;
	private ArrayList <Email> spam;
	
	public Controller() {
		this.rulesPath= "null";
		this.rules = new ArrayList<>();
		this.ham = new ArrayList<>();
		this.spam = new ArrayList<>();
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
		    	  System.out.println(linha);
		    	  linha = leitor.readLine();
		      }
		     	      
		      ficheiro.close();
		    } catch (IOException e) {
		    	  System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
	
	// Leitura do ficheiro ham.txt, cria��o de Emails Ham e adi��o ao vetor
	
	public void readHam() {
		try {
		      FileReader ficheiro = new FileReader(this.hamPath);
		      BufferedReader leitor = new BufferedReader(ficheiro);
		      String linha = leitor.readLine();
		      while (linha != null) {
		    	  System.out.println(linha);
		    	  String[] tokens = linha.split("\\s+");
		    	  Email e = new Email(tokens[0]);
		    	  for(int i = 1 ; i<tokens.length;i++) {
		    		  e.addRule(tokens[i]);
		    		  System.out.println(tokens[i]);
		    	  }
		    	  ham.add(e);
		    	  linha = leitor.readLine();
		      }
		      
		      ficheiro.close();
		    } catch (IOException e) {
		    	  System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
	
	// Leitura do ficheiro spam.txt, cria��o de Emails Spam e adi��o ao vetor
	
	public void readSpam() {
		try {
		      FileReader ficheiro = new FileReader(this.spamPath);
		      BufferedReader leitor = new BufferedReader(ficheiro);
		      String linha = leitor.readLine();
		      while (linha != null) {
		    	  System.out.println(linha);
		    	  String[] tokens = linha.split("\\s+");
		    	  Email e = new Email(tokens[0]);
		    	  for(int i = 1 ; i<tokens.length;i++) {
		    		  e.addRule(tokens[i]);
		    	  }
		    	  spam.add(e);
		    	  linha = leitor.readLine();
		      }
		      
		      ficheiro.close();
		    } catch (IOException e) {
		    	  System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
	
	// Consulta do peso de uma determinada regra
	
	public double getPeso(String rule) {
		for(Rule r : rules) {
			if(r.getName().equals(rule))
				return r.getPeso();
		}
		return 0;
	}
	
	// C�lculo de Falsos Positivos recorrendo aos emails Ham considerados SPAM ( somat�rio > 5)
	
	public int calcularFP() {
		int falsosPositivos = 0;
		
		for(Email e : ham) {
			int somat�rio = 0;
			
			for(String r :e.getRules()) {
				somat�rio += getPeso(r);
			}
			if(somat�rio>5)
				falsosPositivos++;
		}
		return falsosPositivos;
		
	}
		
	// C�lculo de Falsos Negativos recorrendo aos emails Spam considerados HAM ( somat�rio < 5)
	
	public int calcularFN() {
		int falsosNegativos = 0;
		
		for(Email e : spam) {
			int somat�rio = 0;
			
			for(String r :e.getRules()) {
				somat�rio += getPeso(r);
			}
			if(somat�rio<=5)
				falsosNegativos++;
		}
		return falsosNegativos;
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
