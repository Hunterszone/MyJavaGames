package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.EffectsTest;
import suite.tests.LoadIconTest;
import suite.tests.ShipCollisionTest;
import suite.tests.UfoCollisionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UfoCollisionTest.class, EffectsTest.class, LoadIconTest.class, ShipCollisionTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { UfoCollisionTest.class, EffectsTest.class, LoadIconTest.class,
				ShipCollisionTest.class };
		return allClasses;
	}
}