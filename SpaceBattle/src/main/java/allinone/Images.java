package allinone;

public enum Images {
	SPACESHIP {
		@Override
		public String getImg() {
			return "res/spaceship.png";
		}
	};
	public abstract String getImg();
}