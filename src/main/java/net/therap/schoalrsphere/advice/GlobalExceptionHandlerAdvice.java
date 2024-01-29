package net.therap.schoalrsphere.advice;

import lombok.extern.slf4j.Slf4j;
import net.therap.schoalrsphere.exception.InsufficientPrivilegeException;
import net.therap.schoalrsphere.exception.NoEntityFoundException;
import net.therap.schoalrsphere.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static net.therap.schoalrsphere.util.Constants.KEY_ERROR;
import static net.therap.schoalrsphere.util.ViewName.ERROR_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/25/24
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Model model) {
		log.error(e.getMessage());

		model.addAttribute(KEY_ERROR, new Error("400", "Bad Request", "Invalid value provided."));

		return ERROR_PAGE;
	}

	@ExceptionHandler(InsufficientPrivilegeException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String handleInsufficientPrivilegeException(InsufficientPrivilegeException e, Model model) {
		log.error(e.getMessage());

		model.addAttribute(KEY_ERROR, new Error("401", "Unauthorized", "You are not authorized to access this page."));

		return ERROR_PAGE;
	}

	@ExceptionHandler(NoEntityFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoUserFoundException(NoEntityFoundException e, Model model) {
		log.error(e.getMessage());

		model.addAttribute(KEY_ERROR, new Error("404", "Not Found", e.getMessage()));

		return ERROR_PAGE;
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public String handleNoResourceFoundException(NoResourceFoundException e, Model model) {
		log.error(e.getMessage());

		model.addAttribute(KEY_ERROR, new Error("404", "Not Found", "Sorry this page is not available"));

		return ERROR_PAGE;
	}

	@ExceptionHandler(Exception.class)
	public void handleException(Exception e) {
		e.printStackTrace();

		log.error(e.getMessage());
	}
}
