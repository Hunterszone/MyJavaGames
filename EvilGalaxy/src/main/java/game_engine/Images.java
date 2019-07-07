package game_engine;

public enum Images {
	ALIEN {
		@Override
		public String getImg() {
			return "images/alien.png";
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
			return "images/boss.png";
		}
	},
	EVILHEAD {
		@Override
		public String getImg() {
			return "images/evilhead.png";
		}
	},
	MYSHIPINIT {
		@Override
		public String getImg() {
			return "images/craft.png";
		}
	},
	MYSHIPONFIRE {
		@Override
		public String getImg() {
			return "images/newship.png";
		}
	},
	MYSHIPUPDOWN {
		@Override
		public String getImg() {
			return "images/alien.gif";
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
			return "images/alien3.gif";
		}
	},
	MYSHIPDAMAGED {
		@Override
		public String getImg() {
			return "images/hitcraft.gif";
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
			return "images/canon.png";
		}
	},
	FIREBALLINIT {
		@Override
		public String getImg() {
			return "images/fireball.png";
		}
	},
	GOLDINIT {
		@Override
		public String getImg() {
			return "images/gold.png";
		}
	},
	HEALTHINIT {
		@Override
		public String getImg() {
			return "images/health.png";
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
	};
	public abstract String getImg();
}