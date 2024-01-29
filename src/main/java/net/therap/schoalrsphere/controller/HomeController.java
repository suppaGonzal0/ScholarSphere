package net.therap.schoalrsphere.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static net.therap.schoalrsphere.util.ViewName.HOME_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/24/24
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        model.addAttribute("test", session.getAttribute("user"));

        return HOME_PAGE;
    }
}
