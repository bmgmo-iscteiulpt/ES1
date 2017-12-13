package antiSpamFilterTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import antiSpamFilter.Controller;

public class ControllerTest {
	Controller c = Controller.getInstance();

	@Test
	public void testController() {
		assertNotNull("should not be null", c.getRules());
		assertNotNull("should not be null", c.getHam());
		assertNotNull("should not be null", c.getSpam());
		assertEquals("0%", c.getPercentage(), "failure - strings are not equal");
	}

	@Test
	public void testGetInstance() {
		assertNotNull("should not be null", c);
	}

	@Test
	public void testSetRulesPath() {
		c.setRulesPath("caminho");
		assertEquals("caminho", c.getRulesPath(), "failure - strings are not equal");
	}

	@Test
	public void testSetHamPath() {
		c.setHamPath("caminhoHAM");
		assertEquals("caminhoHAM", c.getHamPath(), "failure - strings are not equal");
	}

	@Test
	public void testSetSpamPath() {
		c.setSpamPath("caminhoSPAM");
		assertEquals("caminhoSPAM", c.getSpamPath(), "failure - strings are not equal");
	}

	@Test
	public void testReadRules() {
		c.setRulesPath("path");
		c.readRules("novoFicheiro");

		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		assertNotNull("should not be null", c.getRules().get(0));
		assertEquals("BAYES_00", c.getRules().get(0).getName(), "failure - strings are not equal");
		assertEquals("0.0", String.valueOf(c.getRules().get(0).getPeso()), "failure - strings are not equal");

		c.readRules("abrirFicheiro");
		assertNotNull("should not be null", c.getRules().get(0));

	}

	@Test
	public void testReadNSGAII() {
		c.readNSGAII("teste", "teste");
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		assertNotNull("should not be null", c.getRules().get(0));
		assertEquals( "BAYES_00", c.getRules().get(0).getName(),"failure - strings are not equal");
		assertEquals( "0.0", String.valueOf(c.getRules().get(0).getPeso()),"failure - strings are not equal");

		c.setRulesPath("rules.cf");
		c.readNSGAII("AntiSpamFilterProblem.NSGAII.rf", "AntiSpamFilterProblem.NSGAII.rs");
		assertNotNull(c.getRules());
	}

	@Test
	public void testReadHam() {
		c.setHamPath("path");
		c.readHam();

		c.setHamPath("ham.log");
		c.readHam();
		assertNotNull("should not be null", c.getHam());
		assertEquals("xval_initial/9/_ham_/00035.a0e0e8cdca0b8352a9e9c2c81e5d5cd7", c.getHam().get(0).getId(),
				"failure - strings are not equal");
	}

	@Test
	public void testReadSpam() {
		
		c.setSpamPath("path");
		c.readSpam();

		c.setSpamPath("spam.log");
		c.readSpam();
		assertNotNull("should not be null", c.getSpam());
		assertEquals("xval_initial/9/_spam_/00938.cdac5333fc78f7128fd8f2905fe4b89b", c.getSpam().get(0).getId(),
				"failure - strings are not equal");

	}

	@Test
	public void testGetDadosTabela() {
		c.clearRules();
		String[][] dados =c.getDadosTabela();
		assertNotNull("should not be null", dados);
	}

	@Test
	public void testCalcularFP() {
		
		c.setHamPath("ham.log");
		c.setSpamPath("spam.log");
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		c.readHam();
		c.readSpam();
		
		c.getRules().get(0).setPeso(5.0);
		int fp = c.calcularFP();
		assertEquals(692,fp);
	}

	@Test
	public void testCalcularFN() {
		c.setHamPath("ham.log");
		c.setSpamPath("spam.log");
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		c.readHam();
		c.readSpam();
		
		c.getRules().get(0).setPeso(-5.0);
		int fn = c.calcularFN();
		assertEquals(239,fn);
	}

	@Test
	public void testGetRules() {
		assertNotNull(c.getRules());
	}

	@Test
	public void testPesosAleatorios() {
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		c.pesosAleatorios();
		assertNotNull(c.getRules());
	}

	@Test
	public void testPesosManuais() {
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		c.pesosManuais(0, "5");
		String[][] dados = c.getDadosTabela();
		assertTrue(dados[0][1].equals("5"));
	}

	@Test
	public void testPesosAlgoritmo() {
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		double[] pesos = { 3.0, 2.0, 1.0 };
		c.pesosAlgoritmo(pesos);
		assertEquals(3.0, c.getRules().get(0).getPeso(), "failure - strings are not equal");

	}

	@Test
	public void testFicheirosDef() {
		c.ficheirosDef();
		assertNull(c.getRulesPath());
		assertNull(c.getHamPath());
		assertNull(c.getSpamPath());
		c.setHamPath("ham");
		c.setSpamPath("spam");
		c.setRulesPath("rules");
		c.ficheirosDef();
		assertNotNull(c.getRulesPath());
		assertNotNull(c.getHamPath());
		assertNotNull(c.getSpamPath());
	}

	@Test
	public void testgetCount() {
		c.count();
		assertEquals(1, c.getCount());
	}

	@Test
	public void testGetPercentage() {
		assertEquals("0%", c.getPercentage());

	}
	
	@Test
	public void testsetCount() {
		c.setCount(10);
		assertEquals(10, c.getCount());
	}
	
	@Test
	public void isRulesDef() {
		c.clearRules();
		assertFalse(c.isRulesdef());
		c.readRules("novoFicheiro");
		assertTrue(c.isRulesdef());
	}
	
	@Test
	public void guardarPesos() {
		c.getRules().get(0).setPeso(4.0);
		c.guardarPesos();
		c.readRules("abrirFicheiro");
		assertEquals(4,c.getRules().get(0).getPeso());
	}
}
