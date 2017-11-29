package antiSpamFilterTest;

import org.junit.jupiter.api.Test;

import antiSpamFilter.GUI;

class MainTest {

	@Test
	void testMain() {
		org.junit.runner.JUnitCore.runClasses(GUI.class);
	}
	

}