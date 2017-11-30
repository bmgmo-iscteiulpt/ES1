package antiSpamFilterTest;

import org.junit.Test;

import antiSpamFilter.Main;

public class MainTest {

	@Test
	public void testMain() {
		Main m = new Main();
		String[] args = new String[1];
		Main.main(args);
	}

}
