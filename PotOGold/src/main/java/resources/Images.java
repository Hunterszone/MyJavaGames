package resources;

public enum Images {
	BACKGROUND {
		@Override
		public String getImg() {
			return "res/background.png";
		}
	},
	GAME_ICON {
		@Override
		public String getImg() {
			return "res/gameico.png";
		}
	},
	BOMB {
		@Override
		public String getImg() {
			return "res/mine.png";
		}
	},
	TIMER {
		@Override
		public String getImg() {
			return "res/timer.png";
		}
	},
	LEPRICON {
		@Override
		public String getImg() {
			return "res/santa.png";
		}
	},
	LEPRIHEAD {
		@Override
		public String getImg() {
			return "res/santa-head.png";
		}
	},
	GIFT1 {
		@Override
		public String getImg() {
			return "res/gift1.png";
		}
	},
	GIFT2 {
		@Override
		public String getImg() {
			return "res/gift2.png";
		}
	};
	public abstract String getImg();
}