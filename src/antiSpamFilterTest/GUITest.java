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
		g.getRules_cf().doClick();
		g.getHam_log().doClick();
		g.getSpam_log().doClick();
		g.getAbrir().doClick();
		g.getGuardar().doClick();
		g.getLimpar().doClick();
		g.getAlgoritmo().doClick();
		g.getRandom().doClick();
		g.getIniciar().doClick();
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
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRules_cf() {
		assertNotNull(g.getRules_cf());
	}

	@Test
	public void testGetHam_txt() {
		assertNotNull(g.getHam_log());
	}

	@Test
	public void testGetSpam_txt() {
		assertNotNull(g.getSpam_log());
	}

	@Test
	public void testGetGuardar() {
		assertNotNull(g.getRules_cf());
	}

	@Test
	public void testGetAbrir() {
		assertNotNull(g.getRules_cf());
	}

	@Test
	public void testGetLimpar() {
		assertNotNull(g.getRules_cf());
	}

	@Test
	public void testClassificar() {
		g.classificar();
	}
	
	@Test
	public void testgetAlgoritmo() {
		assertNotNull(g.getAlgoritmo());
	}
	
	@Test
	public void testgetRandom() {
		assertNotNull(g.getRandom());
	}
	
	@Test
	public void testgetIniciar() {
		assertNotNull(g.getIniciar());
	}
}
