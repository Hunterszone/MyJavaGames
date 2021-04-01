package suite.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import enums.Commands;

public class ConsoleContentClassTest {

	@Test
	public void testCommmands() {
		
		int counter = 0;
		for (Commands command : Commands.values()) {
			assertTrue(Commands.values()[counter].equals(command));
			counter++;
		}
	}
}