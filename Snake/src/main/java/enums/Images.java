package enums;

public enum Images {
	APPLE {
		@Override
		public String getImg() {
			return "images/apple.png";
		}
	},
	HEAD {
		@Override
		public String getImg() {
			return "images/head.png";
		}
	},
	DOT {
		@Override
		public String getImg() {
			return "images/dot.png";
		}
	},
	CURSOR {
		@Override
		public String getImg() {
			return "images/cursor.png";
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