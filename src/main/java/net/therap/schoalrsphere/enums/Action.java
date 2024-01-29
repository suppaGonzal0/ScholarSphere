package net.therap.schoalrsphere.enums;

/**
 * @author mehzabinaothoi
 * @since 1/15/24
 */
public enum Action {

	SAVE("save"),
	UNSAVE("unsave"),
	FOLLOW("follow"),
	UNFOLLOW("unfollow"),
	RATE("rate"),
	COMMENT("comment"),
	UNAPPROVE("unapprove"),
	APPROVE("approve");


	private final String value;

	Action(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
