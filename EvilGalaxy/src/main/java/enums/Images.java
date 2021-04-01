package enums;

public enum Images {
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
	BUNKERHIT {
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
	MYSHIPINIT {
		@Override
		public String getImg() {
			return "images/spaceship.gif";
		}
	},
	ASTRONAUTINIT {
		@Override
		public String getImg() {
			return "images/astronaut.png";
		}
	},
	SATELLITEINIT {
		@Override
		public String getImg() {
			return "images/star.png";
		}
	},
	ASTEROIDSINIT {
		@Override
		public String getImg() {
			return "images/asteroids.png";
		}
	},
	THEENDUPINIT {
		@Override
		public String getImg() {
			return "images/theEnd.png";
		}
	},
	THEENDDOWNINIT {
		@Override
		public String getImg() {
			return "images/theEndDown.png";
		}
	},
	MYSHIPONFIRE {
		@Override
		public String getImg() {
			return "images/newship.png";
		}
	},
	MYSHIPLIFEBAR {
		@Override
		public String getImg() {
			return "images/lifebar.png";
		}
	},
	MYSHIPGOD {
		@Override
		public String getImg() {
			return "images/alien2.gif";
		}
	},
	MYSHIPESCAPE {
		@Override
		public String getImg() {
			return "images/craft.png";
		}
	},
	MYSHIPDAMAGED {
		@Override
		public String getImg() {
			return "images/hitcraft.gif";
		}
	},
	MYSHIPPULLED {
		@Override
		public String getImg() {
			return "images/magnetic.png";
		}
	},
	BULLETINIT {
		@Override
		public String getImg() {
			return "images/bomber.png";
		}
	},
	CANONINIT {
		@Override
		public String getImg() {
			return "images/canon2.png";
		}
	},
	PLASMABALLINIT {
		@Override
		public String getImg() {
			return "images/fireball2.png";
		}
	},
	GOLDINIT {
		@Override
		public String getImg() {
			return "images/gold2.png";
		}
	},
	HEALTHINIT {
		@Override
		public String getImg() {
			return "images/health2.png";
		}
	},
	SAVESIGNINIT {
		@Override
		public String getImg() {
			return "images/saved.png";
		}
	},
	MISSILEINIT {
		@Override
		public String getImg() {
			return "images/missile.png";
		}
	},
	ROCKETINIT {
		@Override
		public String getImg() {
			return "images/rocket.png";
		}
	},
	VOLUMEINIT {
		@Override
		public String getImg() {
			return "images/volbutt.png";
		}
	},
	VOLUMEMUTE {
		@Override
		public String getImg() {
			return "images/mute.png";
		}
	};
	public abstract String getImg();
}