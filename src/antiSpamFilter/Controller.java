package antiSpamFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

	private String rulesPath;
	private ArrayList<String> rules	;
	
	
	public Controller() {
		super();
		this.rulesPath= "null";
		this.rules = new ArrayList<String>();
	}

	public void setRulesPath(String path) {
		this.rulesPath = path;
	}
	
	public void readRules() {
		try {
		      FileReader ficheiro = new FileReader(this.rulesPath);
		      BufferedReader leitor = new BufferedReader(ficheiro);
		 
		      String linha = leitor.readLine();
		      while (linha != null) {
		    	  rules.add(linha);
		    	  System.out.println(linha);
		    	  linha = leitor.readLine();
		      }
		 
		      ficheiro.close();
		    } catch (IOException e) {
		    	  System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
}
