package game_engine;

public enum Images {
	ADVUFO {
		@Override
		public String getImg() {
			return "res/advufo.png";
		}
	},
	SPACESHIP {
		@Override
		public String getImg() {
			return "res/spaceship.png";
		}
	};
	public abstract String getImg();
}