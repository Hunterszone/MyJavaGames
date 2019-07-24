package gameDevelopment;

public enum Images {
	BIRD {
		@Override
		public String getImg() {
			return "res/images/bird.png";
		}
	},
	CHEST {
		@Override
		public String getImg() {
			return "res/images/chest.png";
		}
	},
	GUNMAN {
		@Override
		public String getImg() {
			return "res/images/gunman.png";
		}
	},
	HEALTH {
		@Override
		public String getImg() {
			return "res/images/health.png";
		}
	},
	MINE {
		@Override
		public String getImg() {
			return "res/images/mine.png";
		}
	},
	MOUSEPOINTER {
		@Override
		public String getImg() {
			return "res/images/mouse_pointer.png";
		}
	},
	POINTER {
		@Override
		public String getImg() {
			return "res/images/pointer.png";
		}
	},
	TILE1 {
		@Override
		public String getImg() {
			return "res/images/tile_1.png";
		}
	},
	TILE2 {
		@Override
		public String getImg() {
			return "res/images/tile_2.png";
		}
	},
	TILE3 {
		@Override
		public String getImg() {
			return "res/images/tile_3.png";
		}
	},
	TILE4 {
		@Override
		public String getImg() {
			return "res/images/tile_4.png";
		}
	},
	TILE5 {
		@Override
		public String getImg() {
			return "res/images/tile_5.png";
		}
	};
	public abstract String getImg();
}