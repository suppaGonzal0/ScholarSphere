package net.therap.scholarsphere.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import net.therap.scholarsphere.validation.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mehzabinaothoi
 * @since 1/14/24
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

	String message() default "min 4 characters required";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
