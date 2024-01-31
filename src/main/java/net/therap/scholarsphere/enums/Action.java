package net.therap.scholarsphere.enums;

import lombok.Getter;

/**
 * @author mehzabinaothoi
 * @since 1/15/24
 */
@Getter
public enum Action {

	CREATE("create"),
	UPDATE("update"),
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

}
