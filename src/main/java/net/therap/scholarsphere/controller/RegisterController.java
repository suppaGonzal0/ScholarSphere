package net.therap.scholarsphere.controller;

import net.therap.scholarsphere.command.RegisterCommand;
import net.therap.scholarsphere.service.UserService;
import net.therap.scholarsphere.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.therap.scholarsphere.enums.Action.CREATE;
import static net.therap.scholarsphere.util.ViewName.*;

/**
 * @author mehzabinaothoi
 * @since 1/30/24
 */
@Controller
@RequestMapping("/register")
@SessionAttributes("registerCommand")
public class RegisterController {

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(
				"user.username",
				"user.email",
				"user.password",
				"user.fullName.firstName",
				"user.fullName.lastName",
				"user.location.city",
				"user.location.country",
				"user.bio"
		);

		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	@GetMapping("/step01")
	public String showFirstRegistrationPage(@SessionAttribute(name = "registerCommand", required = false)
	                                        RegisterCommand registerCommand,
	                                        Model model) {

		if (isNull(registerCommand)) {
			registerCommand = new RegisterCommand();
		}

		model.addAttribute("registerCommand", registerCommand);

		return REGISTER_FIRST_PAGE;
	}

	@PostMapping("/step01")
	public String processFirstRegistration(@Validated(ValidationGroups.RegStepOne.class)
	                                       @ModelAttribute("registerCommand") RegisterCommand registerCommand,
	                                       BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			registerCommand.setStepOneComplete(false);

			return REGISTER_FIRST_PAGE;
		}

		registerCommand.setStepOneComplete(true);

		return "redirect:/register/step02";
	}

	@GetMapping("/step02")
	public String showSecondRegistrationPage(@SessionAttribute("registerCommand") RegisterCommand registerCommand) {
		if (!registerCommand.isStepOneComplete()) {
			return "redirect:/register/step01";
		}

		return REGISTER_SECOND_PAGE;
	}

	@PostMapping("/step02")
	public String processSecondRegistration(@Validated(ValidationGroups.RegStepTwo.class)
	                                        @ModelAttribute("registerCommand") RegisterCommand registerCommand,
	                                        BindingResult bindingResult) {

		if (!registerCommand.isStepOneComplete()) {
			return "redirect:/register/step01";
		}

		if (bindingResult.hasErrors()) {
			registerCommand.setStepTwoComplete(false);

			return REGISTER_SECOND_PAGE;
		}

		if (nonNull(userService.findByEmail(registerCommand.getUser().getEmail()))) {
			bindingResult.rejectValue("user.email", "error.duplicate.property");

			return REGISTER_SECOND_PAGE;
		}

		registerCommand.setStepTwoComplete(true);

		return "redirect:/register/preview";
	}

	@GetMapping("/preview")
	public String showFinalRegistrationPage(@SessionAttribute("registerCommand") RegisterCommand registerCommand) {
		if (!registerCommand.isStepOneComplete()) {
			return "redirect:/register/step01";
		}

		if (!registerCommand.isStepTwoComplete()) {
			return "redirect:/register/step02";
		}

		return REGISTER_FINAL_PAGE;
	}

	@PostMapping("/preview")
	public String processRegistration(@SessionAttribute("registerCommand") RegisterCommand registerCommand,
	                                  SessionStatus sessionStatus) {

		if (!registerCommand.isStepOneComplete()) {
			return "redirect:/register/step01";
		}

		if (!registerCommand.isStepTwoComplete()) {
			return "redirect:/register/step02";
		}

		userService.saveOrUpdate(registerCommand.getUser(), CREATE);

		sessionStatus.setComplete();

		return "redirect:/login";
	}

	@GetMapping("/cancel")
	public String redirectToLoginPage(SessionStatus sessionStatus) {
		sessionStatus.setComplete();

		return "redirect:/login";
	}
}

