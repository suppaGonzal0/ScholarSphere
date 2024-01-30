package net.therap.schoalrsphere.command;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import net.therap.schoalrsphere.model.User;

/**
 * @author mehzabinaothoi
 * @since 1/13/24
 */
@Getter
@Setter
public class RegisterCommand {

	@Valid
	private User user;
	private boolean isStepOneComplete;
	private boolean isStepTwoComplete;

	public RegisterCommand() {
		this.user = new User();
	}
}
