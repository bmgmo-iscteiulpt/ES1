package antiSpamFilterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

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
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSetRulesPath() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSetHamPath() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSetSpamPath() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testReadRules() {
		fail("Not yet implemented"); // TODO
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
