package net.therap.schoalrsphere.controller;

import jakarta.validation.Valid;
import net.therap.schoalrsphere.command.LoginCommand;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static net.therap.schoalrsphere.util.ViewName.LOGIN_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/28/24
 */
@Controller
@RequestMapping("/login")
public class LoginController {

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
        return LOGIN_PAGE;
    }
}
