package game_engine;

import java.util.List;

import enemy.Alien;
import enemy.Bunker;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import items.Angel;
import items.BunkerBullet;
import items.CanonBall;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import util.ActionPerformer;
import util.LivesAndCounts;
import util.LoadSounds;

public class UpdateObjects {

	static void updateDragons() {
		if (Alien.aliens.isEmpty()) {
			Dragon.dragons.stream().forEach(dragon -> {
				if (dragon != null && dragon.isVisible()) {
					dragon.move();
				}
			});
		}
	}

	static void inGame() {
		if (!ActionPerformer.isIngame()) {
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().stop();
		}
	}

	static void updateMyShip() {
		if (PlayerShip.playerShip.isVisible()) {
			PlayerShip.playerShip.move();
		}
	}

	static void updateMyCrosshair() {
		if (Crosshair.crosshair.isVisible()) {
			Crosshair.crosshair.move();
		}
	}

	protected static void updateExplosions() {
		Explosion.explosions.removeIf(explosion -> explosion != null && explosion.getR() == 80);
		Explosion.explosions.forEach(explosion -> explosion.update());
	}

	static void updateMyShipMissiles() {
		final List<ShipMissile> missiles = PlayerShip.playerShip.getMissiles();

		missiles.removeIf(missile -> missile != null && !missile.isVisible());

		missiles.stream().filter(missile -> missile.isVisible()).forEach(missile -> missile.moveMissile());
	}

	static void updateBullets() {
		BunkerBullet.bulletsLeft = Bunker.bunkerObj.getBulletsLeft();
		BunkerBullet.bulletsRight = Bunker.bunkerObj.getBulletsRight();

		if (BunkerBullet.bulletsLeft.removeIf(bullet -> bullet != null && !bullet.isVisible())
				|| BunkerBullet.bulletsRight.removeIf(bullet -> bullet != null && !bullet.isVisible())) {
			LoadSounds.HIT.stop();
		}

		BunkerBullet.bulletsLeft.stream().filter(bullet -> bullet.isVisible()).forEach(bullet -> {
			bullet.moveDiagLeft();
			if (PlayerShip.playerShip.x > 200) {
				bullet.moveDiagRight();
				bullet.moveRight();
			} else if (PlayerShip.playerShip.y > 300) {
				bullet.moveDown();
				bullet.moveLeft();
			}
		});

		BunkerBullet.bulletsRight.stream().filter(bullet -> bullet.isVisible()).forEach(bullet -> {
			bullet.moveDiagRight();
			if (PlayerShip.playerShip.x > 200) {
				bullet.moveDiagLeft();
				bullet.moveLeft();
			} else if (PlayerShip.playerShip.y > 300) {
				bullet.moveDown();
				bullet.moveLeft();
			}
		});
	}

	static void updateEHPlasmaBalls() {
		final List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls();

		plasmaBalls.removeIf(plasmaBall -> plasmaBall != null && !plasmaBall.isVisible());

		plasmaBalls.stream().filter(plasmaBall -> plasmaBall.isVisible()).forEach(plasmaBall -> {
			if (Dragon.dragons.isEmpty() && ActionPerformer.getTimerHard().isRunning()) {
				plasmaBall.movePlasmaBallDiagUp();
				plasmaBall.movePlasmaBallDiagDown();
				if (Gold.goldstack.isEmpty() && LivesAndCounts.getLifePlayerShip() > 0) {
					if (plasmaBall.y < 0) {
						plasmaBall.y = 0;
						plasmaBall.move();
					}
				}
				if (Gold.goldstack.size() > 0 && LivesAndCounts.getLifePlayerShip() <= 3) {
					if (plasmaBall.y > 768) {
						plasmaBall.y = 768;
						plasmaBall.move();
					}
				}
			} else {
				plasmaBall.move();
			}
		});
	}

	static void updateEHCanons() {
		final List<CanonBall> canonballs = EvilHead.evilHead.getCanons();

		canonballs.removeIf(canon -> canon != null && !canon.isVisible());

		canonballs.stream().filter(canon -> canon.isVisible()).forEach(canon -> canon.move());
	}

	static void updateRockets() {
		final List<ShipRocket> rocketStack = PlayerShip.playerShip.getRockets();

		rocketStack.removeIf(rocket -> rocket != null && !rocket.isVisible());

		rocketStack.stream().filter(rocket -> rocket.isVisible()).forEach(rocket -> rocket.moveRocket());
	}

	static void updateAliens() {
		Alien.aliens.removeIf(alien -> alien != null && !alien.isVisible());
		Alien.aliens.stream()
			.filter(alien -> alien.isVisible())
			.forEach(alien -> {
				if (ActionPerformer.getTimerHard().isRunning()) {
					alien.moveFaster();
				}
			alien.move();
		});
	}

	static void updateEvilHead() {
		assert Alien.aliens != null;
		assert Dragon.dragons != null;

		if (ActionPerformer.getTimerEasy().isRunning()) {
			AIEngine.aiEngine.evilHeadOnEasy();
		}

		if (ActionPerformer.getTimerMedium().isRunning()) {
			AIEngine.aiEngine.evilHeadOnMedium();
		}

		if (Alien.aliens.isEmpty() && ActionPerformer.getTimerHard().isRunning()) {
			if (!Dragon.dragons.isEmpty()) {
				PlayerShip.playerShip.shakeShip();
				Crosshair.crosshair.shakeCrosshair();
			}
			if (EvilHead.evilHead.isVisible()) {
				AIEngine.aiEngine.evilHeadOnHard();
			}
		}
	}

	static void updateGold() {
		Gold.goldstack.stream().filter(goldBar -> goldBar.isVisible()).forEach(goldBar -> goldBar.move());
		Gold.goldstack.removeIf(goldBar -> goldBar != null && !goldBar.isVisible());
	}

	static void updateHealth() {
		HealthPack.healthpacks.stream().filter(healthPack -> healthPack.isVisible())
				.forEach(healthPack -> healthPack.move());

		HealthPack.healthpacks.removeIf(healthPack -> healthPack != null && !healthPack.isVisible());
	}

	static void updateAngels() {
		Angel.angels.stream().forEach(angel -> {
			if (angel.isVisible()) {
				angel.move();
			}
		});

		Angel.angels.removeIf(angel -> angel != null && !angel.isVisible());
	}
}