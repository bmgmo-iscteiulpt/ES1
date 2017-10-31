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
		super();
		this.rulesPath= "null";
		this.ham = new ArrayList<>();
	}

	public void setRulesPath(String path) {
		this.rulesPath = path;
	}
	
	public void setHamPath(String path) {
		this.hamPath = path;
	}
	
	public void setSpamPath(String path) {
		this.spamPath = path;
	}
	
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
}
