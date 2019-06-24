package sound_engine;

public enum SoundEffects {

	BLOOP {
		@Override
		public String getSound() {
			return "sounds/bloop.wav";
		}
	},
	SCREAM {
		@Override
		public String getSound() {
			return "sounds/scream.wav";
		}
	},
	EXPLOSION {
		@Override
		public String getSound() {
			return "sounds/explosion.wav";
		}
	},
	BURNED {
		@Override
		public String getSound() {
			return "sounds/burned.wav";
		}
	};

	public abstract String getSound();
}