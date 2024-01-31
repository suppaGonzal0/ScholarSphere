package net.therap.scholarsphere.enums;

import lombok.Getter;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Getter
public enum SearchBy {

	AUTHOR("author"),
	CONFERENCE("conference"),
	TITLE("title"),
	TAG("tag");

	private final String value;

	SearchBy(String value) {
		this.value = value;
	}
}
