package antiSpamFilter;

public class Main {

	public static void main(String[] args) {
		Controller c = new Controller();
		c.setHamPath("ham.log.txt");
		c.readHam();

	}

}
