package net.therap.scholarsphere.controller;

import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;
import static net.therap.scholarsphere.util.ViewName.PENDING_APPROVALS_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/15/24
 */
@Controller
@RequestMapping("/pending-approvals")
public class PendingApprovalController {

	@Autowired
	private PaperService paperService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("*");
	}

	@GetMapping
	public String showPendingApprovals(Model model) {
		List<Paper> papers = paperService.findAllUnapproved();

		model.addAttribute("papers", papers);

		return PENDING_APPROVALS_PAGE;
	}

	@PostMapping
	public String approvePapers(@RequestParam(name = "approvedPapers", required = false) String[] approvedPapers) {

		if (nonNull(approvedPapers) && approvedPapers.length > 0) {
			paperService.approvePaper(approvedPapers);
		}

		return "redirect:/pending-approvals";
	}

	@GetMapping("/unapprove")
	public String approvePapers(@RequestParam String id) {
		paperService.unapprovePaper(id);

		return "redirect:/pending-approvals";
	}
}