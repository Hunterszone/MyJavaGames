package suite.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suite.tests.AlienTests;
import suite.tests.BunkerBulletsTests;
import suite.tests.BunkerTests;
import suite.tests.CanonBallTests;
import suite.tests.CollisionsTests;
import suite.tests.ConsoleContentClassTests;
import suite.tests.CrosshairTests;
import suite.tests.DragonTests;
import suite.tests.EvilHeadTests;
import suite.tests.FireBallTests;
import suite.tests.GoldTests;
import suite.tests.HealthPackTests;
import suite.tests.MainClassTests;
import suite.tests.MyShipTests;
import suite.tests.SaveSignTests;
import suite.tests.ShipMissileTests;
import suite.tests.ShipRocketTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AlienTests.class, DragonTests.class, BunkerBulletsTests.class, BunkerTests.class,
		CanonBallTests.class, CollisionsTests.class, ConsoleContentClassTests.class, CrosshairTests.class,
		DragonTests.class, EvilHeadTests.class, FireBallTests.class, GoldTests.class, HealthPackTests.class,
		MainClassTests.class, MyShipTests.class, SaveSignTests.class, ShipMissileTests.class, ShipRocketTests.class })

public class SuiteTests {

	public static Class<?>[] getClasses() {
		Class<?>[] allClasses = { AlienTests.class, DragonTests.class, BunkerBulletsTests.class, BunkerTests.class,
				CanonBallTests.class, CollisionsTests.class, ConsoleContentClassTests.class, CrosshairTests.class,
				DragonTests.class, EvilHeadTests.class, FireBallTests.class, GoldTests.class, HealthPackTests.class,
				MainClassTests.class, MyShipTests.class, SaveSignTests.class, ShipMissileTests.class,
				ShipRocketTests.class };
		return allClasses;
	}
}