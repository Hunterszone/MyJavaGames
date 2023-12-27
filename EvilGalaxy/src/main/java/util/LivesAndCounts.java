package util;

public class LivesAndCounts {

	private static int lifeEvilHead = 3;
	private static int lifePlayerShip = 3;
	private static int lifeBunker = 3;
	private static int goldCount = 12;
	private static int alienKilled = 0;
	private static int dragonKilled = 0;

	public static int getDragonKilled() {
		return dragonKilled;
	}

	public static void setDragonKilled(int dragonKilled) {
		LivesAndCounts.dragonKilled = dragonKilled;
	}

	public static int getAlienKilled() {
		return alienKilled;
	}

	public static void setAlienKilled(int alienKilled) {
		LivesAndCounts.alienKilled = alienKilled;
	}

	public static int getLifeEvilHead() {
		return lifeEvilHead;
	}

	public static void setLifeEvilHead(int lifeEvilHead) {
		LivesAndCounts.lifeEvilHead = lifeEvilHead;
	}

	public static int getLifeBunker() {
		return lifeBunker;
	}

	public static void setLifeBunker(int lifeBunker) {
		LivesAndCounts.lifeBunker = lifeBunker;
	}

	public static int getGoldCount() {
		return goldCount;
	}

	public static void setGoldCount(int goldCount) {
		LivesAndCounts.goldCount = goldCount;
	}

	public static int getLifePlayerShip() {
		return lifePlayerShip;
	}

	public static void setLifePlayerShip(int lifePlayerShip) {
		LivesAndCounts.lifePlayerShip = lifePlayerShip;
	}
	
	// Method to increase the player ship life
    public static void increasePlayerShipLife(int amount) {
        int currentLife = getLifePlayerShip();
        int newLife = Math.max(Constants.MIN_LIFE_PLAYER_SHIP, currentLife + amount);
        setLifePlayerShip(newLife);
    }
	
	// Method to decrease the player ship life
    public static void decreasePlayerShipLife(int amount) {
        int currentLife = getLifePlayerShip();
        int newLife = Math.max(Constants.MIN_LIFE_PLAYER_SHIP, currentLife - amount);
        setLifePlayerShip(newLife);
    }
    
    // Method to decrease the bunker life
    public static void decreaseBunkerLife(int amount) {
        int currentLife = getLifeBunker();
        int newLife = Math.max(Constants.MIN_LIFE_BUNKER, currentLife + amount);
        setLifeBunker(newLife);
    }
    
    // Method to decrease the evilhead life
    public static void decreaseEvilHeadLife(int amount) {
        int currentLife = getLifeEvilHead();
        int newLife = Math.max(Constants.MIN_LIFE_EVIL_HEAD, currentLife + amount);
        setLifeEvilHead(newLife);
    }

    // Method to increase the count of killed aliens
    public static void increaseAlienKilledCount(int amount) {
        int currentCount = getAlienKilled();
        int newCount = Math.max(Constants.MIN_ALIEN_KILLED, currentCount + amount);
        setAlienKilled(newCount);
    }
    
    // Method to increase the count of killed dragons
    public static void increaseDragonKilledCount(int amount) {
        int currentCount = getDragonKilled();
        int newCount = Math.max(Constants.MIN_DRAGON_KILLED, currentCount + amount);
        setDragonKilled(newCount);
    }

}
