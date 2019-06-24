package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.AlienTest;
import suite.tests.BunkerBulletsTest;
import suite.tests.BunkerTest;
import suite.tests.CanonBallTest;
import suite.tests.CollisionsTest;
import suite.tests.ConsoleContentClassTest;
import suite.tests.CrosshairTest;
import suite.tests.DragonTest;
import suite.tests.EvilHeadTest;
import suite.tests.FireBallTest;
import suite.tests.GoldTest;
import suite.tests.HealthPackTest;
import suite.tests.MainClassTest;
import suite.tests.MyShipTest;
import suite.tests.SaveSignTest;
import suite.tests.ShipMissileTest;
import suite.tests.ShipRocketTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AlienTest.class, DragonTest.class, BunkerBulletsTest.class, BunkerTest.class,
		CanonBallTest.class, CollisionsTest.class, ConsoleContentClassTest.class, CrosshairTest.class,
		DragonTest.class, EvilHeadTest.class, FireBallTest.class, GoldTest.class, HealthPackTest.class,
		MainClassTest.class, MyShipTest.class, SaveSignTest.class, ShipMissileTest.class, ShipRocketTest.class })

public class AllTests {
	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { AlienTest.class, DragonTest.class, BunkerBulletsTest.class, BunkerTest.class,
				CanonBallTest.class, CollisionsTest.class, ConsoleContentClassTest.class, CrosshairTest.class,
				DragonTest.class, EvilHeadTest.class, FireBallTest.class, GoldTest.class, HealthPackTest.class,
				MainClassTest.class, MyShipTest.class, SaveSignTest.class, ShipMissileTest.class,
				ShipRocketTest.class };
		return allClasses;
	}
}