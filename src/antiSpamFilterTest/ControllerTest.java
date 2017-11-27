package antiSpamFilterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import antiSpamFilter.Controller;

class ControllerTest {
	Controller c = Controller.getInstance();
	@Test
	void testController() {
		assertNotNull("should not be null",c.getRules());
		assertNotNull("should not be null",c.getHam());
		assertNotNull("should not be null",c.getSpam());
		assertEquals("failure - strings are not equal","0%",c.getCount());
	}

	@Test
	void testGetInstance() {
		assertNotNull("should not be null",c);
	}

	@Test
	void testSetRulesPath() {
		c.setRulesPath("caminho");
		assertEquals("failure - strings are not equal","caminho",c.getRulesPath());
	}

	@Test
	void testSetHamPath() {
		c.setHamPath("caminhoHAM");
		assertEquals("failure - strings are not equal","caminhoHAM",c.getHamPath());
	}

	@Test
	void testSetSpamPath() {
		c.setSpamPath("caminhoSPAM");
		assertEquals("failure - strings are not equal","caminhoSPAM",c.getSpamPath());
	}

	@Test
	void testReadRules() {
		try{
			c.readRules();
		}
		catch (Exception e) {
		
		}
		try{
			c.setRulesPath("rules.cf");
			c.readRules();
			assertNotNull("should not be null",c.getRules().get(0));
			assertEquals("failure - strings are not equal","BAYES_00",c.getRules().get(0).getName());
			assertEquals("failure - strings are not equal","0.0",String.valueOf(c.getRules().get(0).getPeso()));
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Test
	void testReadNSGAII() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testReadHam() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testReadSpam() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetDadosTabela() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testCalcularFP() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testCalcularFN() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetRules() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testPesosAleatorios() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testPesosManuais() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testPesosAlgoritmo() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testFicheirosDef() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testCount() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetCount() {
		fail("Not yet implemented"); // TODO
	}

}
