package antiSpamFilterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;

import antiSpamFilter.Rule;

class RuleTest {
	Rule r=new Rule("nome1");
	@Test
	void testRule() {
		assertNotNull("should not be null",r);
		assertEquals("failure - strings are not equal", "nome1", r.getName());
	}

	@Test
	void testGetPeso() {
		assertNotNull("should not be null", r.getPeso());
	}

	@Test
	void testSetPeso() {
		r.setPeso(2.4);
		assertEquals("failure - strings are not equal", 2.4, r.getPeso(), 0.1);
	}

	@Test
	void testGetName() {
		assertNotNull("should not be null", r.getName());
	}

}