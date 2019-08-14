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
import suite.tests.LoadIconTest;
import suite.tests.PointsTest;
import suite.tests.TimerTest;
import suite.tests.YouWonTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BombTest.class, EffectsTest.class, Gift1Test.class, Gift2Test.class, LepriconTest.class,
		LivesTest.class, LoadIconTest.class, PointsTest.class, GameOverTest.class, GamePauseTest.class, TimerTest.class,
		YouWonTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { BombTest.class, EffectsTest.class, Gift1Test.class, Gift2Test.class,
				LepriconTest.class, LivesTest.class, LoadIconTest.class, PointsTest.class, GameOverTest.class,
				GamePauseTest.class, TimerTest.class, YouWonTest.class };
		return allClasses;
	}
}