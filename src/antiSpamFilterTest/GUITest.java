package antiSpamFilterTest;

import static org.junit.Assert.*;

import org.junit.Test;

import antiSpamFilter.Controller;
import antiSpamFilter.GUI;

public class GUITest {
	
	
	
	@Test
	public void testGUI() {
		GUI g = new GUI();
		assertNotNull(g);
	}

	@Test
	public void testSetTable() {
		Controller c = Controller.getInstance();
		GUI g = new GUI();
		g.setTable();
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		g.setTable();
		
	}
	
	@Test
	public void testcriarTabela() {
		Controller c = Controller.getInstance();
		GUI g = new GUI();
		g.criarTabela();
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		g.criarTabela();
		
	}

	@Test
	public void testAddinfo() {
		GUI g = new GUI();
		g.addinfo("Bem Vindo");
		g.addinfo("A gerar configuração ideal, aguarde...");
	}

	
	@Test
	public void testClassificar() {
		Controller c = Controller.getInstance();
		GUI g = new GUI();
		c.setRulesPath("rules.cf");
		c.setHamPath("ham.log.xt");
		c.setSpamPath("spam.log.txt");
		for(int i = 0 ; i< c.getRules().size(); i++) {
			c.getRules().get(i).setPeso(5);
		}	
		g.classificar();
		for(int i = 0 ; i< c.getRules().size(); i++) {
			c.getRules().get(i).setPeso(-5);
		}	
		g.classificar();
		c.readNSGAII("AntiSpamFilterProblem.NSGAII.rf",
				"AntiSpamFilterProblem.NSGAII.rs");
		g.classificar();
	}
	
	@Test
	public void testGetHam_log() {
		GUI g = new GUI();
		assertNotNull(g.getHam_log());
		g.getHam_log().doClick();
	}

	@Test
	public void testGetSpam_log() {
		GUI g = new GUI();
		assertNotNull(g.getSpam_log());
		g.getSpam_log().doClick();
	}
	
	@Test
	public void testGetRules_cf() {
		GUI g = new GUI();
		assertNotNull(g.getRules_cf());
		g.getRules_cf().doClick();
	}

	@Test
	public void testGetAbrir() {
		GUI g = new GUI();
		assertNotNull(g.getAbrir());
		g.getAbrir().doClick();
	}

	@Test
	public void testGetAlgoritmo() {
		GUI g = new GUI();
		assertNotNull(g.getAlgoritmo());
		g.getAlgoritmo().doClick();
	}
	
	@Test
	public void testGetGuardar() {
		GUI g = new GUI();
		assertNotNull(g.getGuardar());
		g.getGuardar().doClick();
	}
	
	@Test
	public void testGetRandom() {
		
		GUI g = new GUI();
		assertNotNull(g.getRandom());
		g.getRandom().doClick();
	}

	@Test
	public void testGetmanual() {
		GUI g = new GUI();
		assertNotNull(g.getmanual());
		g.getmanual().doClick();
	}
	
	@Test
	public void testGetLimpar() {
		GUI g = new GUI();
		assertNotNull(g.getLimpar());
		g.getLimpar().doClick();
	}
	
}
