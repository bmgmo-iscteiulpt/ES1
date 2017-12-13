package antiSpamFilterTest;

import static org.junit.Assert.*;

import org.junit.Test;

import antiSpamFilter.Controller;
import antiSpamFilter.GUI;

public class GUITest {
	GUI g = new GUI();
	Controller c = Controller.getInstance();
	
	@Test
	public void testGUI() {
		GUI g = new GUI();
		assertNotNull(g);
	}

	@Test
	public void testSetTable() {
		g.setTable();
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		g.setTable();
		
	}

	@Test
	public void testAddinfo() {
		g.addinfo("Bem Vindo");
		g.addinfo("A gerar configuração ideal, aguarde...");
	}

	
	@Test
	public void testClassificar() {
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
		assertNotNull(g.getHam_log());
		g.getHam_log().doClick();
	}

	@Test
	public void testGetSpam_log() {
		assertNotNull(g.getSpam_log());
		g.getSpam_log().doClick();
	}
	
	@Test
	public void testGetRules_cf() {
		assertNotNull(g.getRules_cf());
		g.getRules_cf().doClick();
	}

	@Test
	public void testGetAbrir() {
		assertNotNull(g.getAbrir());
		g.getAbrir().doClick();
	}

	@Test
	public void testGetAlgoritmo() {
		assertNotNull(g.getAlgoritmo());
		g.getAlgoritmo().doClick();
	}
	
	@Test
	public void testGetGuardar() {
		assertNotNull(g.getGuardar());
		g.getGuardar().doClick();
	}
	
	@Test
	public void testGetRandom() {
		assertNotNull(g.getRandom());
		g.getRandom().doClick();
	}

	@Test
	public void testGetIniciar() {
		assertNotNull(g.getIniciar());
		g.getIniciar().doClick();
	}
	
	@Test
	public void testGetLimpar() {
		assertNotNull(g.getLimpar());
		g.getLimpar().doClick();
	}
	
}
