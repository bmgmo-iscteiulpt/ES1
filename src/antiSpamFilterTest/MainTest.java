package antiSpamFilterTest;

import org.junit.Test;

import antiSpamFilter.Main;

public class MainTest {

	@Test
	public void testMain() {
		new Main();
		String[] args = new String[1];
		Main.main(args);
	}

}
