package enums;

public enum Images {
	ICON {
		@Override
		public String getImg() {
			return "images/apple.png";
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