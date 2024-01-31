package net.therap.scholarsphere.enums;

import lombok.Getter;

/**
 * @author mehzabinaothoi
 * @since 1/4/24
 */
@Getter
public enum Role {

	ADMIN("ROLE_ADMIN"),
	REGULAR("ROLE_REGULAR");

	final String value;

	Role(String role) {
		this.value = role;
	}
}
