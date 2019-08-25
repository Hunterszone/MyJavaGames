package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.AdvUfoTest;
import suite.tests.EffectsTest;
import suite.tests.GameEasyTest;
import suite.tests.GameEndTest;
import suite.tests.GameHardTest;
import suite.tests.GameMediumTest;
import suite.tests.GamePauseTest;
import suite.tests.LoadIconTest;
import suite.tests.PointsTest;
import suite.tests.ShotMoonTest;
import suite.tests.ShotShipTest;
import suite.tests.ShotUfoTest;
import suite.tests.SpaceshipTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AdvUfoTest.class, EffectsTest.class, GameEasyTest.class, GameEndTest.class, GameHardTest.class,
		GameMediumTest.class, GamePauseTest.class, LoadIconTest.class, PointsTest.class, ShotMoonTest.class,
		ShotShipTest.class, ShotUfoTest.class, SpaceshipTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { AdvUfoTest.class, EffectsTest.class, GameEasyTest.class, GameEndTest.class,
				GameHardTest.class, GameMediumTest.class, GamePauseTest.class, LoadIconTest.class, PointsTest.class,
				ShotMoonTest.class, ShotShipTest.class, ShotUfoTest.class, SpaceshipTest.class };
		return allClasses;
	}
}