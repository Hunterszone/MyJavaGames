package game_engine;

import enemy.EvilHead;

public class AIEngine extends SpritePattern {

	private static final long serialVersionUID = 4187180717457228164L;

	public AIEngine(int x, int y) {
		super(x, y);
	}

	public static AIEngine aiEngine;
	
	// Simulated Artificial Intelligence on EASY
	public void AIOnEasy() {

		EvilHead.evilHead.x += EvilHead.evilHead.speedX;
		EvilHead.evilHead.y += EvilHead.evilHead.speedY;

		if (EvilHead.evilHead.x < 1) {
			EvilHead.evilHead.x = 1;
		}

		if (EvilHead.evilHead.y < 1) {
			EvilHead.evilHead.y = 1;
		}

		EvilHead.evilHead.x -= 1;

		if (EvilHead.evilHead.x < 500) {
			EvilHead.evilHead.speedX += 1.2;
			EvilHead.evilHead.drawHead();
		}

		EvilHead.evilHead.y -= 1;
		if (EvilHead.evilHead.y == 0) {
			EvilHead.evilHead.x += 1.2;
			EvilHead.evilHead.drawHead();
		}

		if (EvilHead.evilHead.x > 800) {

			EvilHead.evilHead.speedX -= 1.2;
			EvilHead.evilHead.speedY += 1.2;
			EvilHead.evilHead.drawHead();
		}

		if (EvilHead.evilHead.y > 500) {
			EvilHead.evilHead.speedY -= 1;
			EvilHead.evilHead.drawHead();
		}

	}

	// Simulated Artificial Intelligence on MEDIUM
	public void AIOnMedium() {

		EvilHead.evilHead.x += EvilHead.evilHead.speedX;
		EvilHead.evilHead.y += EvilHead.evilHead.speedY;

		if (EvilHead.evilHead.x < 1) {
			EvilHead.evilHead.x = 1;
		}

		if (EvilHead.evilHead.y < 1) {
			EvilHead.evilHead.y = 1;
		}

		EvilHead.evilHead.x -= 1;
		if (EvilHead.evilHead.y == 250 
				|| EvilHead.evilHead.y == 350 
				|| EvilHead.evilHead.y == 450) {
			EvilHead.evilHead.loadCanon();
			EvilHead.evilHead.strikeHead();
		}

		if (EvilHead.evilHead.x < 500) {
			EvilHead.evilHead.speedX += 1.2;
			EvilHead.evilHead.drawHead();
		}

		EvilHead.evilHead.y -= 1;
		if (EvilHead.evilHead.y == 0) {
			EvilHead.evilHead.x += 1.2;
			EvilHead.evilHead.drawHead();
		}

		if (EvilHead.evilHead.x > 800) {
			EvilHead.evilHead.speedX -= 1.2;
			EvilHead.evilHead.speedY += 1.2;
			EvilHead.evilHead.drawHead();

		}

		if (EvilHead.evilHead.y > 500) {
			EvilHead.evilHead.speedY -= 1;
			EvilHead.evilHead.drawHead();
		}

	}

	// Simulated Artificial Intelligence on HARD
	public void AIOnHard() {

		EvilHead.evilHead.x += EvilHead.evilHead.speedX;
		EvilHead.evilHead.y += EvilHead.evilHead.speedY;

		if (EvilHead.evilHead.x < 1) {
			EvilHead.evilHead.x = 1;
		}

		if (EvilHead.evilHead.y < 1) {
			EvilHead.evilHead.y = 1;
		}

		EvilHead.evilHead.x -= 1;
		if (EvilHead.evilHead.y == 250 
				|| EvilHead.evilHead.y == 350 
				|| EvilHead.evilHead.y == 450 
				|| EvilHead.evilHead.y == 550 
				|| EvilHead.evilHead.y == 650) {
			EvilHead.evilHead.loadCanon();
			EvilHead.evilHead.strikeHead();
		}

		if (EvilHead.evilHead.x < 500) {
			EvilHead.evilHead.speedX += 1.2;
			EvilHead.evilHead.drawHead();
		}

		EvilHead.evilHead.y -= 1;
		if (EvilHead.evilHead.y == 0) {
			EvilHead.evilHead.x += 1.2;
			EvilHead.evilHead.drawHead();
		}

		if (EvilHead.evilHead.x > 800) {
			EvilHead.evilHead.speedX -= 1.2;
			EvilHead.evilHead.speedY += 1.2;
			EvilHead.evilHead.drawHead();
		}

		if (EvilHead.evilHead.y > 500) {
			EvilHead.evilHead.speedY -= 1;
			EvilHead.evilHead.drawHead();
		}

	}
}
