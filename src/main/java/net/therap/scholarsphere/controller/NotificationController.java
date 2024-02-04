package net.therap.scholarsphere.controller;

import jakarta.servlet.http.HttpSession;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.service.NotificationService;
import net.therap.scholarsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static net.therap.scholarsphere.util.ViewName.NOTIFICATION_PAGE;

/**
 * @author mehzabinaothoi
 * @since 2/4/24
 */
@Controller
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@GetMapping
	public String showAllNotifications(Model model, HttpSession session) {
		UserDetails sessionUser = (UserDetails) session.getAttribute("user");
		User loggedInUser = userService.findByEmail(sessionUser.getUsername());

		model.addAttribute("notifications", notificationService.findAll(loggedInUser));

		return NOTIFICATION_PAGE;
	}

	@PostMapping
	public String markAsRead(HttpSession session) {
		UserDetails sessionUser = (UserDetails) session.getAttribute("user");
		User loggedInUser = userService.findByEmail(sessionUser.getUsername());

		notificationService.markAsRead(loggedInUser);

		return "redirect:/notification";
	}
}
