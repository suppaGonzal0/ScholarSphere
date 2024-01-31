package net.therap.scholarsphere.controller;

import jakarta.validation.Valid;
import net.therap.scholarsphere.command.LoginCommand;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static net.therap.scholarsphere.util.Util.checkPassword;
import static net.therap.scholarsphere.util.ViewName.LOGIN_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/28/24
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showLoginPage(Model model) {
        model.addAttribute("loginCommand", new LoginCommand());

        return LOGIN_PAGE;
    }

    @PostMapping
    public String processLogin(@Valid @ModelAttribute LoginCommand loginCommand, BindingResult bindingResult) {
        User user = userService.findByEmail(loginCommand.getEmail());

        if (isNull(user)) {
            bindingResult.rejectValue("email", "error.not.found");

        } else if (!checkPassword(loginCommand.getPassword(), user.getPassword())) {
            bindingResult.rejectValue("password", "error.invalid.credentials");
        }

        return LOGIN_PAGE;
    }
}
