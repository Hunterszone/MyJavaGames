package shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.Walker;
import city.cs.engine.World;
import levelsEngine.LevelBuilder;
import levelsEngine.LevelsInitializer;
import main.HighScore;

/**
 * the player class is a subtype of walker, and has its own images fields and
 * methods
 */
public class Player extends Walker implements CollisionListener {

	/**
	 * a static image to show when the player is standing still
	 */
	private static final BodyImage IMAGE = new BodyImage("data/shroom.png", 2);
	/**
	 * a static image to show when the player is jumping
	 */
	private static final BodyImage JUMP_1 = new BodyImage("data/shroom.png", 2);
	/**
	 * a static image to show when the player is falling
	 */
	private static final BodyImage JUMP_2 = new BodyImage("data/shroom.png", 2);

	/**
	 * a static image to represent a full heart
	 */
	private static final ImageIcon FH = new ImageIcon(
			new ImageIcon("data/appleImageFull.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	/**
	 * a static image to represent a half heart
	 */
	private static final ImageIcon HH = new ImageIcon(
			new ImageIcon("data/appleImageHalf.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
	/**
	 * a static image to represent an empty heart
	 */
	private static final ImageIcon EH = new ImageIcon(
			new ImageIcon("data/appleImageEmpty.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

	/**
	 * a scaled image to represent a projectile in the projectile panel
	 */
	private static final ImageIcon PROJECTILE_ICON = new ImageIcon(
			new ImageIcon("data/pinecone.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
	/**
	 * a static image to represent a projectile shot from the player
	 */
	private static final BodyImage PROJECTILE_IMAGE = new BodyImage("data/pinecone.png", 2);

	/**
	 * player fixtures
	 */
	public SolidFixture leftFootFixture, rightFootFixture;
	/**
	 * attribute relevant to player stats
	 */
	private int hearts, shots, shotSpeed;
	/**
	 * attribute to show the health of the player
	 */
	private String defaultHealth, health;
	/**
	 * the moving speed of the player
	 */
	private float moveSpeed;
	/**
	 * the force of a jump from the player
	 */
	private Vec2 jump;
	/**
	 * show if a player has the ability to jump
	 */
	private boolean canJump;
	/**
	 * swing panel to show player attributes to the player
	 */
	private JPanel healthPanel, projectilePanel, scorePanel;

	/**
	 * levels object that the player has been instantiated in
	 */
	private final LevelsInitializer levels;
	/**
	 * the fixture that the player last collided with
	 */
	private SolidFixture prevCollision;

	/**
	 * list of all projectiles shot from the player
	 */
	private final List<Projectile> projectiles;
	/**
	 * shape of the projectile to be shot from the player
	 */
	private static final Shape PROJECTILE = new PolygonShape(-0.059f, 0.659f, 0.114f, 0.281f, 0.207f, -0.405f, 0.053f,
			-0.64f, -0.368f, -0.64f, -0.535f, -0.386f, -0.362f, 0.281f, -0.189f, 0.652f);

	private final JLabel fullHeart, halfHeart, emptyHeart;
	private final JLabel[] drawHearts, heartTypes;

	/**
	 * this will initialize the player to have three hearts, and initialize all the
	 * panels
	 *
	 * @param world  the world the player is in
	 * @param levels the levels object that the world is in
	 */
	public Player(World world, LevelsInitializer levels) {
		super(world);
		this.levels = levels;
		drawPlayer();
		hearts = 3;
		defaultHealth = "";
		for (int i = 0; i < hearts; i++) {
			// health is stored as a string where each heart has three states
			// empty(0), half(1), full(2)
			defaultHealth += "2";
		}
//        System.out.println(defaultHealth);
		health = defaultHealth;
		shots = 10;
		shotSpeed = 750;
		moveSpeed = 10;
		jump = new Vec2(0, 15000);
		canJump = false;
		projectilePanel = new JPanel();
		projectilePanel.setLayout(new BoxLayout(projectilePanel, BoxLayout.LINE_AXIS));
		projectilePanel.setBackground(new Color(0, 0, 0, 0));
		healthPanel = new JPanel();
		healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.LINE_AXIS));
		healthPanel.setBackground(new Color(0, 0, 0, 0));
		scorePanel = new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.LINE_AXIS));
		scorePanel.setBackground(new Color(0, 0, 0, 0));
		projectiles = new ArrayList<>();

		fullHeart = new JLabel(FH);
		halfHeart = new JLabel(HH);
		emptyHeart = new JLabel(EH);

		heartTypes = new JLabel[] { emptyHeart, halfHeart, fullHeart };
		drawHearts = new JLabel[hearts];
	}

	/**
	 * initialize the player fixtures, and add an image
	 */
	private void drawPlayer() {
		// create fixtures to model player and set the correct physical properties
		Shape playerShape = new PolygonShape(-0.065f, 1.007f, -0.653f, 0.744f, -0.889f, 0.303f, -0.489f, -0.689f,
				-0.014f, -0.826f, 0.544f, -0.651f, 0.885f, 0.316f, 0.632f, 0.774f);
		SolidFixture bodyFixture = new SolidFixture(this, playerShape);
		bodyFixture.setDensity(10);
		changeFriction(bodyFixture);

		Shape rightWing = new PolygonShape(0.885f, 0.303f, 0.985f, 0.221f, 1.015f, 0.125f, 1.012f, -0.121f, 0.875f,
				-0.426f, 0.708f, -0.569f, 0.537f, -0.621f);
		SolidFixture rightWingFixture = new SolidFixture(this, rightWing);
		changeFriction(rightWingFixture);
		rightWingFixture.setRestitution(0.6f); // give slight restitution to wings to allow player to bounce off wall
												// and not slide up it

		Shape leftWing = new PolygonShape(-0.879f, 0.309f, -0.961f, 0.251f, -1.022f, 0.142f, -1.029f, 0.005f, -0.968f,
				-0.248f, -0.875f, -0.415f, -0.718f, -0.566f, -0.523f, -0.621f);
		SolidFixture leftWingFixture = new SolidFixture(this, leftWing);
		changeFriction(leftWingFixture);
		leftWingFixture.setRestitution(0.4f);

		Shape leftFoot = new PolygonShape(-0.356f, -0.764f, -0.376f, -0.874f, -0.342f, -0.976f, -0.294f, -0.997f,
				-0.106f, -0.997f, -0.038f, -0.949f, -0.031f, -0.815f);
		leftFootFixture = new SolidFixture(this, leftFoot);
		changeFriction(leftFootFixture);
		leftFootFixture.setRestitution(0.05f);

		Shape rightFoot = new PolygonShape(0.352f, -0.75f, 0.397f, -0.832f, 0.386f, -0.935f, 0.325f, -0.997f, 0.116f,
				-0.997f, 0.051f, -0.935f, 0.048f, -0.819f);
		rightFootFixture = new SolidFixture(this, rightFoot);
		changeFriction(rightFootFixture);
		rightFootFixture.setRestitution(0.05f);

		addImage(IMAGE);
		this.setName("player");
		this.addCollisionListener(this);
	}

	/**
	 * remove references to jpanels from this class, to allow a new instance to be
	 * made
	 */
	public void cleanup() {
		levels.getLayeredPane().remove(healthPanel);
		levels.getLayeredPane().remove(projectilePanel);
		levels.getLayeredPane().remove(scorePanel);
	}

	/**
	 * set the friction coefficient for the input object.
	 *
	 * @param sf a SolidFixture object
	 */
	private void changeFriction(SolidFixture sf) {
		sf.setFriction(0.9f);
	}

	/**
	 * reduce the current player health by half a heart. If the health value is
	 * already on half a heart, the player will die
	 */
	public void decrementHalfHeart() {
		for (int i = 0; i < health.length(); i++) {
			// convert each character of health string to an integer
			int temp = Integer.parseInt(Character.toString(health.charAt(i)));
			if (temp > 0) {
				temp--;
				if (i == health.length() - 1) {
					health = health.substring(0, i) + temp;
					levels.getHighScore().deductLife();
					if (temp == 0) {
						// fire endgame event as player is on 0 health
						levels.getLevel().endGame();
					}
				} else {
					// have to cast health to a string as it is not stored as an
					// int
					levels.getHighScore().deductHealth();
					health = health.substring(0, i) + temp + health.substring(i + 1);
				}
				break;
			}
		}
	}

	/**
	 * will add a half a heart to the players current heart value
	 */
	public void incrementHalfHeart() {
		for (int i = health.length() - 1; i >= 0; i--) {
			int temp = Integer.parseInt(Character.toString(health.charAt(i)));
			if (temp < 2) {
				temp++;
				System.out.println("1health" + temp);
				if (i == 0) {
					health = temp + health.substring(i + 1);
				} else {
					health = health.substring(0, i) + temp + health.substring(i + 1);
				}
				System.out.println(health);
				break;
			}

		}

	}

	/**
	 * sets the amount of hearts of the player
	 *
	 * @param hearts the hearts amount to set to
	 */
	public void setHearts(int hearts) {
		this.hearts = hearts;
	}

	/**
	 * get the amount of player hearts
	 *
	 * @return the amount of hearts the player has
	 */
	public int getHearts() {
		return hearts;
	}

	/**
	 * get the explicit value of the default player health
	 *
	 * @return the player's default health
	 */
	public String getDefaultHealth() {
		return defaultHealth;
	}

	/**
	 * get the explicit value of the default player health
	 *
	 * @return the players health
	 */
	public String getHealth() {
		return health;
	}

	/**
	 * the length of the input should be the same length as player hearts. The input
	 * should also show the heart value at each position, meaning it will look
	 * something like this: "221". This input shows full heart, full heart and half
	 * heart.
	 *
	 * @param health the value to set the health to.
	 */
	public void setHealth(String health) {
		this.health = health;
	}

	/**
	 * set the JPanel that is used for the player
	 *
	 * @param healthPanel the panel to use for the player
	 */
	public void setHealthPanel(JPanel healthPanel) {
		this.healthPanel = healthPanel;
	}

	/**
	 * get the JPanel object that is being used as the players healthPanel
	 *
	 * @return the players healthPanel
	 */
	public JPanel getHealthPanel() {
		return healthPanel;
	}

	/**
	 * set the JPanel that is used for the player
	 *
	 * @param scorePanel the panel to use for the player
	 */
	public void setScorePanel(JPanel scorePanel) {
		this.scorePanel = scorePanel;
	}

	/**
	 * get the JPanel object that is being used as the players healthPanel
	 *
	 * @return the players scorePanel
	 */
	public JPanel getScorePanel() {
		return scorePanel;
	}

	/**
	 * draws the correct combinations of half, empty and full hearts on the JPanel
	 * component
	 */
	public void drawPlayerHealth() {
		healthPanel.removeAll();

		for (int i = 0; i < this.getHearts(); i++) {
			// place the correct label at each position of the array
			drawHearts[(this.getHearts() - 1) - i] = heartTypes[Integer.parseInt(this.health.substring(i, i + 1))];
		}

		for (JLabel drawHeart : drawHearts) {
			healthPanel.add(new JLabel(drawHeart.getIcon()));
			// add gap between hearts
			healthPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		}
	}

	/**
	 * works out the high score when the player shoots a small enemy
	 */
	public void shotSnake() {
		levels.getHighScore().shotSmallEnemy();
	}

	/**
	 * works out the high score when the player shoots a small enemy
	 */
	public void shotSawBlade() {
		levels.getHighScore().shotLargeEnemy();
	}

	/**
	 * get the shots of the player
	 * 
	 * @return the amount of shots the player has
	 */
	public int getShots() {
		return shots;
	}

	/**
	 * decrease the amount of shots by 1
	 */
	public void decrementShots() {
		shots--;
	}

	/**
	 * gets the magnitude of the players shot speed
	 * 
	 * @return the shot speed of the player
	 */
	public int getShotSpeed() {
		return shotSpeed;
	}

	/**
	 * set the speed of the players shots
	 * 
	 * @param shotSpeed the value to assign to shotSpeed
	 */
	public void setShotSpeed(int shotSpeed) {
		this.shotSpeed = shotSpeed;
	}

	/**
	 * gets the panel used to display the players shots
	 * 
	 * @return JPanel object that is used for projectilePanel
	 */
	public JPanel getProjectilePanel() {
		return projectilePanel;
	}

	/**
	 * adds the correct amount of icons to the projectilePanel
	 */
	public void drawPlayerShots() {
		projectilePanel.removeAll();
		for (int i = 0; i < shots; i++) {
			projectilePanel.add(new JLabel(PROJECTILE_ICON));
		}
	}

	/**
	 * assigns a new move speed
	 * 
	 * @param moveSpeed the value to assign to the players move speed
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	/**
	 * get the speed that the player is will use when walking
	 * 
	 * @return the players move speed
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * make the player start moving to the left
	 */
	public void moveLeft() {
		this.startWalking(-moveSpeed);
	}

	/**
	 * make the player start moving to the right
	 */
	public void moveRight() {
		this.startWalking(moveSpeed);
	}

	/**
	 * make the player stop walking
	 */
	public void setStopWalking() {
		this.stopWalking();
	}

	/**
	 * sets the jump height of the player
	 *
	 * @param jumpHeight the jump height to use (scale is grid units of the view)
	 */
	public void setJumpHeight(float jumpHeight) {
		jump = new Vec2(jump.x, jumpHeight);
	}

	/**
	 * get the players jump height
	 * 
	 * @return the height of the players jump
	 */
	public float getJumpHeight() {
		return jump.y;
	}

	/**
	 * set animations to show when the player is rising from a jump
	 */
	public void jump() {
		this.applyForce(jump);
		this.removeAllImages();
		this.addImage(JUMP_1);
	}

	/**
	 * set animations to show when the player is falling from a jump
	 */
	public void jumpDown() {
		this.removeAllImages();
		this.addImage(JUMP_2);
	}

	/**
	 * see if the player can jump
	 * 
	 * @return true if the player can jump
	 */
	public boolean canJump() {
		return canJump;
	}

	/**
	 * set whether the player can jump
	 * 
	 * @param canJump true if the player has the ability to jump
	 */
	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	/**
	 * sets the default image of the player
	 */
	public void setDefaultImage() {
		this.removeAllImages();
		this.addImage(IMAGE);
	}

	/**
	 * calculates the angle between the horizontal plane and the mouse position. the
	 * angle is then shifted along the cosine plane to produce a vector in that
	 * direction. A projectile is then instantiated, and added to a list of
	 * projectiles shot from the player.
	 * 
	 * @param mouseLocation the location of the mouse on the world
	 */
	public void shootProjectile(Vec2 mouseLocation) {

		Vec2 playerLocation = getPosition();
		Vec2 force = mouseLocation.sub(playerLocation); // force to apply to bullet
		float magnitude = (float) Math.sqrt(force.x * force.x + force.y * force.y);

		// work out where the bullet starts
		// starts at a position between the player and the mouse
		Vec2 bulletStart = playerLocation.add(force.mul(2 / magnitude));

		Vec2 horizontalVector = new Vec2(mouseLocation.x, playerLocation.y);
		horizontalVector.sub(playerLocation);

		// needed to work out the angle between the mouse and the horizontal
		// to work out how much to rotate the image
		float theta = (float) Math.acos(Math.abs(mouseLocation.x - playerLocation.x) / force.length());
		float pi = (float) Math.PI;

		// shift theta along cosine graph to get correct rotation
		if (mouseLocation.y < playerLocation.y) {
			if (mouseLocation.x > playerLocation.x) {
				theta = pi + theta;
			} else {
				theta = 0 - theta;
			}
		} else if (mouseLocation.x > playerLocation.x) {
			theta = pi - theta;
		}

		Projectile tempProjectile = new Projectile(getWorld(), PROJECTILE, force.mul(getShotSpeed() / magnitude),
				bulletStart, pi / 2 - theta);
		tempProjectile.setName("playerProjectile");
		tempProjectile.addImage(PROJECTILE_IMAGE);
		decrementShots();
		tempProjectile.setShootingBody(this);
		projectiles.add(tempProjectile);

	}

	/**
	 * works out what to do when there is a collision between the player and another
	 * body.
	 * 
	 * @param e a collision event; is raised when a body collides with the player
	 */
	@Override
	public void collide(CollisionEvent e) {
		// player should only be able to be able to jump when its feet is on a
		// platform. Or when the player is touching a platform with its head and
		// the last collision was with its feet. This effectively allows the
		// player to wall jump.

		if ((e.getReportingFixture().equals(leftFootFixture) || e.getReportingFixture().equals(rightFootFixture))
				|| (prevCollision.equals(leftFootFixture) || prevCollision.equals(rightFootFixture))) {
//            System.out.println(e.getReportingFixture());
			if (!(e.getOtherBody() instanceof Projectile || e.getOtherBody() instanceof SpikedBarrel
					|| e.getOtherBody() instanceof Snake)) {
				canJump = true;
			}

		}
		if (e.getOtherBody() instanceof Snake) {
			Vec2 temp1 = this.getPosition();
			Vec2 temp2 = e.getOtherBody().getPosition();
			Vec2 difference = temp2.sub(temp1);
			difference.normalize();
			this.applyImpulse(difference.mul(-250f));
			decrementHalfHeart();

		} else if (e.getOtherBody() instanceof SawBlade) {
			SawBlade saw = (SawBlade) e.getOtherBody();
			Vec2 temp1 = this.getPosition();
			Vec2 temp2 = e.getOtherBody().getPosition();
			Vec2 difference = temp2.sub(temp1);
			difference.normalize();
			saw.applyImpulse(difference.mul(5f));
			decrementHalfHeart();

		} else if (e.getOtherBody().getName() != null) {
			switch (e.getOtherBody().getName()) {
			case "standardEnemy":
				decrementHalfHeart();
				e.getOtherBody().destroy();
				break;
			case "1health":
				e.getOtherBody().destroy();
				for (int i = 0; i < 2; i++) {
					this.incrementHalfHeart();
				}
				break;
			case "end":
				e.getOtherBody().destroy();
				LevelBuilder tempLevel = (LevelBuilder) getWorld();
				tempLevel.fireChangeLevelEvent();
				break;
			default:
				// handle this
				break;
			}

		}

		prevCollision = e.getReportingFixture();

	}

	public void drawPlayerScore() {
		// TODO Auto-generated method stub
		scorePanel.removeAll();
		JLabel scLabel = new JLabel("Score: " + String.valueOf(HighScore.getScore()));
		Font font = new Font("Arial", Font.BOLD,16);
		scLabel.setFont(font);
		for (int i = 0; i < 1; i++) {
			scorePanel.add(scLabel);
		}
	}
}
