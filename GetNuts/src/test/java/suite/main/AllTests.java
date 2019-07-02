package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.ActorTest;
import suite.tests.AreaTest;
import suite.tests.BridgeTest;
import suite.tests.LevelsAndBgsTest;
import suite.tests.NutTest;
import suite.tests.PlayerTest;
import suite.tests.WallTest;
import suite.tests.WaterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ActorTest.class, AreaTest.class, BridgeTest.class, LevelsAndBgsTest.class, NutTest.class,
		PlayerTest.class, WallTest.class, WaterTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { ActorTest.class, AreaTest.class, BridgeTest.class, LevelsAndBgsTest.class,
				NutTest.class, PlayerTest.class, WallTest.class, WaterTest.class };
		return allClasses;
	}
}