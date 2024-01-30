package net.therap.schoalrsphere.controller;

import jakarta.servlet.http.HttpSession;
import net.therap.schoalrsphere.command.SearchCommand;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.nonNull;
import static net.therap.schoalrsphere.enums.Role.ADMIN;
import static net.therap.schoalrsphere.util.ViewName.*;

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

		User user = (User) session.getAttribute("user");

		if (nonNull(user)) {
			boolean isAdmin = user.getAuthorities()
					.stream()
					.anyMatch(authority -> ADMIN.getValue().equals(authority.getAuthority()));

			if (isAdmin) {
				return PENDING_APPROVALS_PAGE;

			} else {
				model.addAttribute("searchCommand", new SearchCommand());

				return HOME_PAGE;
			}
		}

		return LANDING_PAGE;
	}
}
