package game_engine;

import java.awt.event.ActionEvent;
import java.util.List;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.PlayerShip;
import items.Angel;
import items.BunkerBullet;
import items.CanonBall;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import util.Flags;
import util.LoadSounds;

public abstract class UpdateObjects extends InitObjects {

	public static int lifeEvilHead = 3;
	public static int lifePlayerShip = 3;
	public static int lifeBunker = 3;
	public static int goldCount = 12;
	public static int alienKilled = 0;
	public static int dragonKilled = 0;

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateMyShip();
		updateMyCrosshair();
		updateMyShipMissiles();
		updateEHPlasmaBalls();
		updateEHCanons();
		updateRockets();
		updateAliens();
		updateEvilHead();
		updateGold();
		updateAngels();
		updateHealth();
		updateBullets();

		Collisions.checkAlienCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkEvilHeadCollision(PlayerShip.playerShip.getBounds(), EvilHead.evilHead.getBounds());
		Collisions.checkBunkerCollision(PlayerShip.playerShip.getBounds(), Bunker.bunkerObj.getBounds());
		Collisions.checkGoldCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());
		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());
		Collisions.checkPlasmaCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkCanonCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkBulletCollision(BunkerBullet.bulletsLeft);
		Collisions.checkBulletCollision(BunkerBullet.bulletsRight);

		repaint();
	}

	static void updateDragons() {
		Dragon.dragons.stream().forEach(dragon -> {
			if (dragon != null) {
				Collisions.checkDragonCollision(dragon);
				if (dragon.isVisible()) {
					dragon.move();
				} else {
					InitObjects.angels.add(new Angel(dragon.getX(), dragon.getY()));
				}
			}
		});

		Dragon.dragons.removeIf(dragon -> dragon != null && !dragon.isVisible());
	}

	private void inGame() {
		if (!ingame) {
			timerEasy.stop();
			timerMedium.stop();
			timerHard.stop();
		}
	}

	private void updateMyShip() {
		if (PlayerShip.playerShip.isVisible()) {
			PlayerShip.playerShip.move();
		}
	}

	private void updateMyCrosshair() {
		if (Crosshair.crosshair.isVisible()) {
			Crosshair.crosshair.move();
		}
	}

	protected static void updateExplosions() {
		explosions.removeIf(explosion -> explosion != null && explosion.getR() == 80);
		explosions.forEach(explosion -> explosion.update());
	}

	private void updateMyShipMissiles() {
		final List<ShipMissile> missiles = PlayerShip.playerShip.getMissiles();

		missiles.removeIf(missile -> missile != null && !missile.isVisible());

		missiles.stream().filter(missile -> missile.isVisible()).forEach(missile -> missile.moveMissile());
	}

	private void updateBullets() {
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

	private void updateEHPlasmaBalls() {
		final List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls();

		plasmaBalls.removeIf(plasmaBall -> plasmaBall != null && !plasmaBall.isVisible());

		plasmaBalls.stream().filter(plasmaBall -> plasmaBall.isVisible()).forEach(plasmaBall -> {
			if (Dragon.dragons.isEmpty() && timerHard.isRunning()) {
				plasmaBall.movePlasmaBallDiagUp();
				plasmaBall.movePlasmaBallDiagDown();
				if (Gold.goldstack.isEmpty() && lifePlayerShip > 0) {
					if (plasmaBall.y < 0) {
						plasmaBall.y = 0;
						plasmaBall.movePlasmaBallRight();
					}
				}
				if (Gold.goldstack.size() > 0 && lifePlayerShip <= 3) {
					if (plasmaBall.y > 768) {
						plasmaBall.y = 768;
						plasmaBall.movePlasmaBallRight();
					}
				}
			} else {
				plasmaBall.movePlasmaBallRight();
			}
		});
	}

	private void updateEHCanons() {
		final List<CanonBall> canonballs = EvilHead.evilHead.getCanons();

		canonballs.removeIf(canon -> canon != null && !canon.isVisible());

		canonballs.stream().filter(canon -> canon.isVisible()).forEach(canon -> {
			if (EvilHead.evilHead.x - PlayerShip.playerShip.x > 0) {
				canon.moveCanonLeft();
			} else {
				canon.moveCanonRight();
			}
		});
	}

	private void updateRockets() {
		final List<ShipRocket> rocketStack = PlayerShip.playerShip.getRockets();

		rocketStack.removeIf(rocket -> rocket != null && !rocket.isVisible());

		rocketStack.stream().filter(rocket -> rocket.isVisible()).forEach(rocket -> rocket.moveRocket());
	}

	private void updateAliens() {
		Alien.aliens.removeIf(alien -> alien != null && !alien.isVisible());
		Alien.aliens.stream().filter(alien -> alien.isVisible()).forEach(alien -> {
			if (timerHard.isRunning()) {
				alien.moveFaster();
			}
			alien.move();
		});
	}

	private void updateEvilHead() {
		assert Alien.aliens != null;
		assert Dragon.dragons != null;

		if (EvilHead.evilHead.isVisible() && timerEasy.isRunning()) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnEasy();
			}
			if (Dragon.dragons.isEmpty()) {
				EvilHead.evilHead.AIOnEasy();
			}
		}

		if (EvilHead.evilHead.isVisible() && timerMedium.isRunning() == true) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnMedium();
			}
			if (Dragon.dragons.isEmpty()) {
				EvilHead.evilHead.AIOnMedium();
			}
		}

		if (EvilHead.evilHead.isVisible() && timerHard.isRunning() == true) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnHard();
			}
			if (Dragon.dragons.isEmpty()) {
				EvilHead.evilHead.AIOnHard();
			}
		}
	}

	private void updateGold() {
		Gold.goldstack.removeIf(goldBar -> goldBar != null && !goldBar.isVisible());
		Gold.goldstack.stream().filter(goldBar -> goldBar.isVisible()).forEach(goldBar -> goldBar.move());
	}

	private void updateAngels() {
		angels.removeIf(angel -> angel != null && !angel.isVisible());
	}

	private void updateHealth() {
		if (HealthPack.healthpacks.size() < 5 && Alien.aliens.isEmpty() && 
				Dragon.dragons.isEmpty() && lifePlayerShip <= 3 && !Flags.hpIsEmpty) {
			HealthPack.healthpacks
					.add(new HealthPack((int) Math.ceil(Math.random() * 1200), (int) Math.floor(Math.random() * 1200)));
		}

		if (HealthPack.healthpacks.isEmpty()) {
			Flags.hpIsEmpty = true;
		}

		HealthPack.healthpacks.stream().filter(healthPack -> healthPack.isVisible())
				.forEach(healthPack -> healthPack.move());

		HealthPack.healthpacks.removeIf(healthPack -> healthPack != null && !healthPack.isVisible());
	}
}