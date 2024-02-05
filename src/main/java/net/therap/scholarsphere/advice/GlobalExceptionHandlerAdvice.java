package net.therap.scholarsphere.advice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.therap.scholarsphere.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static net.therap.scholarsphere.util.Constants.KEY_ERROR;
import static net.therap.scholarsphere.util.ViewName.ERROR_PAGE;

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

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        log.error(e.getMessage());

        model.addAttribute(KEY_ERROR, new Error("403", "Forbidden", "You are not authorized to access this page."));

        return ERROR_PAGE;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e, Model model) {
        log.error(e.getMessage());

        model.addAttribute(KEY_ERROR, new Error("404", "Not Found", e.getMessage()));

        return ERROR_PAGE;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException e, Model model) {
        log.error(e.getMessage());

        model.addAttribute(KEY_ERROR, new Error("404", "Not Found", "Page Not Found"));

        return ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        log.error(e.getMessage());

        model.addAttribute(KEY_ERROR, new Error("", "", "Something Went Wrong"));

        return ERROR_PAGE;
    }
}
