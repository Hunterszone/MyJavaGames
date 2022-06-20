package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.AlienTest;
import suite.tests.BunkerBulletTest;
import suite.tests.BunkerTest;
import suite.tests.CanonBallTest;
import suite.tests.CollisionsTest;
import suite.tests.DragonTest;
import suite.tests.DrawSceneTest;
import suite.tests.EvilHeadTest;
import suite.tests.GoldTest;
import suite.tests.HealthPackTest;
import suite.tests.HighScoreToDbTest;
import suite.tests.LoadResourcesTest;
import suite.tests.PlasmaBallTest;
import suite.tests.PlayerShipTest;
import suite.tests.ShipMissileTest;
import suite.tests.ShipRocketTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AlienTest.class, BunkerBulletTest.class, CanonBallTest.class, CollisionsTest.class, DragonTest.class, BunkerTest.class, CollisionsTest.class,
		EvilHeadTest.class, LoadResourcesTest.class, DragonTest.class, DrawSceneTest.class, GoldTest.class, HealthPackTest.class, HighScoreToDbTest.class, PlasmaBallTest.class,
		PlayerShipTest.class, ShipMissileTest.class, ShipRocketTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { AlienTest.class, BunkerBulletTest.class, CanonBallTest.class, CollisionsTest.class, DragonTest.class, BunkerTest.class, CollisionsTest.class,
				EvilHeadTest.class, LoadResourcesTest.class, DragonTest.class, DrawSceneTest.class, GoldTest.class, HealthPackTest.class, HighScoreToDbTest.class, PlasmaBallTest.class,
				PlayerShipTest.class, ShipMissileTest.class, ShipRocketTest.class };
		return allClasses;
	}
}