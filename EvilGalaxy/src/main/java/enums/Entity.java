package enums;

public enum Entity {

	ALIEN("What is an UFO?"), DRAGON("What is a dragon?"), BUNKER("What is a bunker?"),
	EVILHEAD("What is the devil?");

	private String msg;

	private Entity(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
