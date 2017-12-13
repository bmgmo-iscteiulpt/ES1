package antiSpamFilterTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import antiSpamFilter.Email;

public class EmailTest {

	Email e = new Email("id1");

	@Test
	public void testEmail() {
		assertNotNull("should not be null", e);
		assertNotNull("should not be null", e.getRulesIndex());
		assertEquals("failure - strings are not equal", "id1", e.getId());

	}

	@Test
	public void testGetRulesIndex() {
		assertNotNull("should not be null", e.getRulesIndex());
	}

	@Test
	public void testSetId() {
		e.setId("id2");
		assertEquals("failure - strings are not equal", "id2", e.getId());
	}

	@Test
	public void testAddRuleIndex() {
		e.addRuleIndex(15);
		assertEquals("failure - strings are not equal", Integer.valueOf(15), e.getRulesIndex().get(0));
	}

}
