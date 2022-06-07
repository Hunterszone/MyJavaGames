package enums;

public enum Entity {

	ALIEN("Killed by an alien!"), DRAGON("Killed by a dragon!"), BUNKER("Killed by the bunker!"),
	EVILHEAD("Killed by the Evil Head!");

	private String msg;

	private Entity(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
