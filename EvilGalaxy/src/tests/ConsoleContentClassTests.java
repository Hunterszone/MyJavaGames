package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frames.ConsoleContent;

public class ConsoleContentClassTests {

	@Test
	public void testCommmands() {
		final String[] commands = { "help", "cls", "refresh", "pause", "easy", "med", "hard", "exit", "voloff", "volon",
				"god", "dog", "stats", "restart", "level2", "level3", "level4" };
		int counter = 0;
		for (String command : commands) {
			assertTrue(ConsoleContent.COMMANDS[counter].equals(command));
			counter++;
		}
	}
}