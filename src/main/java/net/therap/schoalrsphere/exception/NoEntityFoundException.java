package net.therap.schoalrsphere.exception;

/**
 * @author mehzabinaothoi
 * @since 1/15/24
 */
public class NoEntityFoundException extends RuntimeException {

	public NoEntityFoundException() {
		super();
	}

	public NoEntityFoundException(String message) {
		super(message);
	}

	public NoEntityFoundException(Throwable cause) {
		super(cause);
	}

	public NoEntityFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
