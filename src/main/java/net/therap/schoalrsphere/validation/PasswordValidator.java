package net.therap.schoalrsphere.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.therap.schoalrsphere.annotation.Password;

import static java.util.Objects.isNull;

/**
 * @author mehzabinaothoi
 * @since 12/21/23
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

		return !isNull(password) && password.length() >= 4;
	}
}
