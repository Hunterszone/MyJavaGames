package enums;

public enum Sprite {
	BG1 {
		@Override
		public String getImg() {
			return "images/tenor.jpg";
		}
	},
	BG2 {
		@Override
		public String getImg() {
			return "images/galaxy2.jpg";
		}
	},
	BG3 {
		@Override
		public String getImg() {
			return "images/galaxy3.jpg";
		}
	},
	ALIEN {
		@Override
		public String getImg() {
			return "images/alien.gif";
		}
	},
	ALIEN_ICON {
		@Override
		public String getImg() {
			return "images/alienIcon.png";
		}
	},
	LASER_ICON {
		@Override
		public String getImg() {
			return "images/laserIcon.png";
		}
	},
	ROCKET_ICON {
		@Override
		public String getImg() {
			return "images/rocketIcon.png";
		}
	},
	DIFF_ICON {
		@Override
		public String getImg() {
			return "images/difficulty.png";
		}
	},
	LOCATION_ICON {
		@Override
		public String getImg() {
			return "images/location.png";
		}
	},
	DRAGON_ICON {
		@Override
		public String getImg() {
			return "images/dragonIcon.png";
		}
	},
	GOLD_ICON {
		@Override
		public String getImg() {
			return "images/gold.png";
		}
	},
	BUNKER {
		@Override
		public String getImg() {
			return "images/bunker.png";
		}
	},
	BUNKER_HIT {
		@Override
		public String getImg() {
			return "images/bunker_hit.png";
		}
	},
	CROSSHAIR {
		@Override
		public String getImg() {
			return "images/pointer.png";
		}
	},
	DRAGON {
		@Override
		public String getImg() {
			return "images/boss.gif";
		}
	},
	EVILHEAD {
		@Override
		public String getImg() {
			return "images/evilhead.gif";
		}
	},
	STRIKEHEAD {
		@Override
		public String getImg() {
			return "images/strikehead.png";
		}
	},
	MYSHIP_INIT {
		@Override
		public String getImg() {
			return "images/spaceship.gif";
		}
	},
	ASTRONAUT_INIT {
		@Override
		public String getImg() {
			return "images/astronaut.png";
		}
	},
	SATELLITE_INIT {
		@Override
		public String getImg() {
			return "images/star.png";
		}
	},
	ASTEROIDS_INIT {
		@Override
		public String getImg() {
			return "images/asteroids.png";
		}
	},
	THEENDUP_INIT {
		@Override
		public String getImg() {
			return "images/theEnd.png";
		}
	},
	THEENDDOWN_INIT {
		@Override
		public String getImg() {
			return "images/theEndDown.png";
		}
	},
	MYSHIP_ON_FIRE {
		@Override
		public String getImg() {
			return "images/newship.png";
		}
	},
	BORDER_IMAGE {
		@Override
		public String getImg() {
			return "images/shadow1.png";
		}
	},
	MYSHIP_LIFEBAR {
		@Override
		public String getImg() {
			return "images/lifebar.png";
		}
	},
	MYSHIP_GOD {
		@Override
		public String getImg() {
			return "images/spaceshipGod.gif";
		}
	},
	MYSHIP_ESCAPE {
		@Override
		public String getImg() {
			return "images/craft.png";
		}
	},
	MYSHIP_DAMAGED {
		@Override
		public String getImg() {
			return "images/hitcraft.gif";
		}
	},
	MYSHIP_PULLED {
		@Override
		public String getImg() {
			return "images/magnetic.png";
		}
	},
	BULLET_INIT {
		@Override
		public String getImg() {
			return "images/bomber.png";
		}
	},
	CANON_INIT {
		@Override
		public String getImg() {
			return "images/canon2.png";
		}
	},
	PLASMABALL_INIT {
		@Override
		public String getImg() {
			return "images/fireball2.png";
		}
	},
	GOLD_INIT {
		@Override
		public String getImg() {
			return "images/gold2.png";
		}
	},
	HEALTH_INIT {
		@Override
		public String getImg() {
			return "images/health2.png";
		}
	},
	SAVESIGN_INIT {
		@Override
		public String getImg() {
			return "images/saved.png";
		}
	},
	MISSILE_INIT {
		@Override
		public String getImg() {
			return "images/missile.png";
		}
	},
	ROCKET_INIT {
		@Override
		public String getImg() {
			return "images/rocket.png";
		}
	},
	VOLUME_INIT {
		@Override
		public String getImg() {
			return "images/volbutt.png";
		}
	},
	VOLUME_MUTE {
		@Override
		public String getImg() {
			return "images/mute.png";
		}
	},
	TEN_POINTS {
		@Override
		public String getImg() {
			return "images/balloon.png";
		}
	},
	ANGEL {
		@Override
		public String getImg() {
			return "images/angel.gif";
		}
	};

	public abstract String getImg();
}