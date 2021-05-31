package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.AlienTest;
import suite.tests.BunkerTest;
import suite.tests.CollisionsTest;
import suite.tests.ConsoleContentClassTest;
import suite.tests.DragonTest;
import suite.tests.EvilHeadTest;
import suite.tests.ItemsTest;
import suite.tests.LoadResourcesTest;
import suite.tests.MyShipTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AlienTest.class, DragonTest.class, ItemsTest.class, BunkerTest.class, CollisionsTest.class,
		ConsoleContentClassTest.class, EvilHeadTest.class, LoadResourcesTest.class,
		MyShipTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { AlienTest.class, DragonTest.class, ItemsTest.class, BunkerTest.class,
				CollisionsTest.class, ConsoleContentClassTest.class, DragonTest.class, EvilHeadTest.class,
				LoadResourcesTest.class, MyShipTest.class };
		return allClasses;
	}
}