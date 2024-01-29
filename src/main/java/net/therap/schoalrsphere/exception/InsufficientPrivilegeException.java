package net.therap.schoalrsphere.exception;

/**
 * @author mehzabinaothoi
 * @since 12/20/23
 */
public class InsufficientPrivilegeException extends RuntimeException {

	public InsufficientPrivilegeException() {
		super();
	}

	public InsufficientPrivilegeException(String message) {
		super(message);
	}

	public InsufficientPrivilegeException(Throwable cause) {
		super(cause);
	}

	public InsufficientPrivilegeException(String message, Throwable cause) {
		super(message, cause);
	}
}
