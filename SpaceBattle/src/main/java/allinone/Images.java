package allinone;

public enum Images {
	SPACESHIP {
		@Override
		public String getImg() {
			return "res/spaceship.png";
		}
	},
	ADVUFO {
		@Override
		public String getImg() {
			return "res/advufo.png";
		}
	};
	public abstract String getImg();
}