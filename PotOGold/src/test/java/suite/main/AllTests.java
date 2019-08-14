package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.BombTest;
import suite.tests.EffectsTest;
import suite.tests.GameOverTest;
import suite.tests.GamePauseTest;
import suite.tests.Gift1Test;
import suite.tests.Gift2Test;
import suite.tests.LepriconTest;
import suite.tests.LivesTest;
import suite.tests.PointsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BombTest.class, EffectsTest.class, Gift1Test.class, Gift2Test.class, LepriconTest.class,
		LivesTest.class, PointsTest.class, GameOverTest.class, GamePauseTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { BombTest.class, EffectsTest.class, Gift1Test.class, Gift2Test.class,
				LepriconTest.class, LivesTest.class, PointsTest.class, GameOverTest.class, GamePauseTest.class };
		return allClasses;
	}
}