package net.therap.schoalrsphere.enums;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
public enum SearchBy {

	AUTHOR("author"),
	CONFERENCE("conference"),
	TITLE("title"),
	TAG("tag");

	private final String value;

	SearchBy(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
