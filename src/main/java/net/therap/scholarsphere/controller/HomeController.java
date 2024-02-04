package net.therap.scholarsphere.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.therap.scholarsphere.command.SearchCommand;
import net.therap.scholarsphere.enums.SearchBy;
import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.service.PaperService;
import net.therap.scholarsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;
import static net.therap.scholarsphere.enums.Role.ADMIN;
import static net.therap.scholarsphere.util.ViewName.*;

/**
 * @author mehzabinaothoi
 * @since 1/24/24
 */
@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	PaperService paperService;

	@GetMapping
	public String showHomePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (nonNull(user)) {
			boolean isAdmin = user.getAuthorities()
					.stream()
					.anyMatch(authority -> ADMIN.getValue().equals(authority.getAuthority()));

			if (isAdmin) {
				return "redirect:/pending-approvals";

			} else {
				model.addAttribute("searchCommand", new SearchCommand());

				return HOME_PAGE;
			}
		}

		return LANDING_PAGE;
	}

	@GetMapping("/sort")
	public String showHomeResultPage(@RequestParam(name = "sortBy", defaultValue = "publish_date") String sort,
	                                 Model model) {

		model.addAttribute("papers", paperService.findAll(sort));
		model.addAttribute("searchCommand", new SearchCommand());

		return HOME_RESULT_PAGE;
	}

	@GetMapping("/search")
	public String processSearch(@ModelAttribute SearchCommand searchCommand, Model model) {
		List<Paper> papers = Collections.emptyList();

		try {
			SearchBy searchBy = SearchBy.valueOf(searchCommand.getSearchBy().toUpperCase());

			papers = switch (searchBy) {
				case AUTHOR -> paperService.searchByAuthorEmail(searchCommand.getSearchTerm());
				case CONFERENCE -> paperService.searchByConference(searchCommand.getSearchTerm());
				case TITLE -> paperService.searchByTitle(searchCommand.getSearchTerm());
				case TAG -> paperService.searchByTag(searchCommand.getSearchTerm());
			};

		} catch (IllegalArgumentException | NullPointerException e) {
			log.error(e.getMessage());
		}

		model.addAttribute("papers", papers);

		return HOME_RESULT_PAGE;
	}
}
