package antiSpamFilterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import antiSpamFilter.Email;

class EmailTest {
	Email e = new Email("id1");
	@Test
	void testEmail() {
		assertNotNull("should not be null",e);
		assertNotNull("should not be null",e.getRules());
		assertNotNull("should not be null",e.getRulesIndex());
		assertEquals("failure - strings are not equal", "id1", e.getId());
		
	}

	@Test
	void testGetRules() {
		assertNotNull("should not be null",e.getRules());
	}

	@Test
	void testGetRulesIndex() {
		assertNotNull("should not be null",e.getRulesIndex());
	}

	@Test
	void testSetId() {
		e.setId("id2");
		assertEquals("failure - strings are not equal", "id2", e.getId());
	}

	@Test
	void testAddRule() {
		e.addRule("Regra");
		assertEquals("failure - strings are not equal", "Regra", e.getRules().get(0));
	}

	@Test
	void testAddRuleIndex() {
		e.addRuleIndex(15);
		assertEquals("failure - strings are not equal",Integer.valueOf(15), e.getRulesIndex().get(0));
	}

}
