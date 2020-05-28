package sound_engine;

public enum SoundEffects {
	ATTACK {
		@Override
		public String getSound() {
			return "sounds/attack.wav";
		}
	},
	BGMUSIC {
		@Override
		public String getSound() {
			return "sounds/bgmusic.wav";
		}
	},
	MENUMUSIC {
		@Override
		public String getSound() {
			return "sounds/menusound.wav";
		}
	},
	BLOOP {
		@Override
		public String getSound() {
			return "sounds/bloop.wav";
		}
	},
	BOING {
		@Override
		public String getSound() {
			return "sounds/boing.wav";
		}
	},
	BURNED {
		@Override
		public String getSound() {
			return "sounds/burned.wav";
		}
	},
	COLLECT {
		@Override
		public String getSound() {
			return "sounds/collect.wav";
		}
	},
	DENIED {
		@Override
		public String getSound() {
			return "sounds/denied.wav";
		}
	},
	EXPLOSION {
		@Override
		public String getSound() {
			return "sounds/explosion.wav";
		}
	},
	FIN {
		@Override
		public String getSound() {
			return "sounds/fin.wav";
		}
	},
	FUSE {
		@Override
		public String getSound() {
			return "sounds/fuse.wav";
		}
	},
	HIGHSC {
		@Override
		public String getSound() {
			return "sounds/highsc.wav";
		}
	},
	LASER {
		@Override
		public String getSound() {
			return "sounds/laser.wav";
		}
	},
	CLICK {
		@Override
		public String getSound() {
			return "sounds/reload.wav";
		}
	},
	MAGIC {
		@Override
		public String getSound() {
			return "sounds/magic.wav";
		}
	},
	ROCKET {
		@Override
		public String getSound() {
			return "sounds/rocket.wav";
		}
	},
	SCREAM {
		@Override
		public String getSound() {
			return "sounds/scream.wav";
		}
	},
	SMASHED {
		@Override
		public String getSound() {
			return "sounds/smashed.wav";
		}
	},
	TAUNT {
		@Override
		public String getSound() {
			return "sounds/taunt.wav";
		}
	};
	public abstract String getSound();
}