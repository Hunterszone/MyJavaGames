package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.CrosshairTest;
import suite.tests.EnemyEntityTest;
import suite.tests.HealthEntityTest;
import suite.tests.HeroEntityTest;
import suite.tests.LoadIconTest;
import suite.tests.LoadResourcesTest;
import suite.tests.TreasureEntityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CrosshairTest.class, EnemyEntityTest.class, HealthEntityTest.class, HeroEntityTest.class,
		LoadIconTest.class, LoadResourcesTest.class, TreasureEntityTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { CrosshairTest.class, EnemyEntityTest.class, HealthEntityTest.class,
				HeroEntityTest.class, LoadIconTest.class, LoadResourcesTest.class, TreasureEntityTest.class };
		return allClasses;
	}
}