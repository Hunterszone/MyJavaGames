package resources;

public enum Fonts {
	GAME_OVER {
		@Override
		public String getImg() {
			return "res/fonts/game_over_font.fnt";
		}
	},
	GAME_OVER_MINE {
		@Override
		public String getImg() {
			return "res/fonts/game_over_mine_2.png";
		}
	},
	SCORE_NUMBER{
		@Override
		public String getImg() {
			return "res/fonts/score_numer_font.fnt";
		}
	},
	SCORE_NUMBER_MINE {
		@Override
		public String getImg() {
			return "res/fonts/score_numer_mine.png";
		}
	};
	public abstract String getImg();
}