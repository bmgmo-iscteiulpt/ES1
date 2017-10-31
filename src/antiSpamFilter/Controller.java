package antiSpamFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

	private String rulesPath;
	private String[][] rules	;
	
	
	public Controller() {
		super();
		this.rulesPath= "null";
	}

	public void setRulesPath(String path) {
		this.rulesPath = path;
	}
	
	public void readRules() {
		try {
		      FileReader ficheiro = new FileReader(this.rulesPath);
		      BufferedReader leitor = new BufferedReader(ficheiro);
		      ArrayList<String> rulesAux = new ArrayList<>();
		      String linha = leitor.readLine();
		      while (linha != null) {
		    	  rulesAux.add(linha);
		    	  System.out.println(linha);
		    	  linha = leitor.readLine();
		      }
		      rules = new String [2][rulesAux.size()];
		      for(int i = 0; i<rulesAux.size();i++) {
		    	  rules[1][i]= rulesAux.get(i);
		      }
		      
		      ficheiro.close();
		    } catch (IOException e) {
		    	  System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
}
