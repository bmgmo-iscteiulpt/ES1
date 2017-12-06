package antiSpamFilterTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

	}

	@Test
	public void testReadNSGAII() {
		c.readNSGAII("teste","teste");
		try{
			c.setRulesPath("rules.cf");
			c.readRules("novoFicheiro");
			assertNotNull("should not be null",c.getRules().get(0));
			assertEquals("failure - strings are not equal","BAYES_00",c.getRules().get(0).getName());
			assertEquals("failure - strings are not equal","0.0",String.valueOf(c.getRules().get(0).getPeso()));
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		c.setRulesPath("rules.cf");
		c.readNSGAII("AntiSpamFilterProblem.NSGAII.rf","AntiSpamFilterProblem.NSGAII.rs");
		assertNotNull(c.getRules());
	}

	@Test
	public void testReadHam() {
		c.setHamPath("path");
		c.readHam();

		c.setHamPath("ham.log.txt");
		c.readHam();
		assertNotNull("should not be null", c.getHam());
		assertEquals("xval_initial/9/_ham_/00035.a0e0e8cdca0b8352a9e9c2c81e5d5cd7", c.getHam().get(0).getId(), "failure - strings are not equal");
	}

	@Test
	public void testReadSpam() {
		c.setSpamPath("path");
		c.readSpam();

		c.setSpamPath("spam.log.txt");
		c.readSpam();
		assertNotNull("should not be null", c.getSpam());
		assertEquals("xval_initial/9/_spam_/00938.cdac5333fc78f7128fd8f2905fe4b89b", c.getSpam().get(0).getId(), "failure - strings are not equal");
	
	}

	@Test
	public void testGetDadosTabela() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCalcularFP() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCalcularFN() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRules() {
		 // TODO
	}

	@Test
	public void testPesosAleatorios() {
		c.setRulesPath("rules.cf");
		c.readRules("novoFicheiro");
		c.pesosAleatorios();
		assertNotNull(c.getRules());
		assertTrue(c.getRules().get(0).getPeso()<5 && c.getRules().get(0).getPeso() >-5);
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
		double[] pesos= {3.0,2.0,1.0};
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

}
