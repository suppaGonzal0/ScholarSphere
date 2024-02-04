package net.therap.scholarsphere.controller;

/*
 * @author mehzabinaothoi
 * @since 1/30/24
 */

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.therap.scholarsphere.editor.ConferenceEditor;
import net.therap.scholarsphere.editor.TagEditor;
import net.therap.scholarsphere.editor.UserEditor;
import net.therap.scholarsphere.model.*;
import net.therap.scholarsphere.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;
import static net.therap.scholarsphere.util.ViewName.PAPER_DETAILS_PAGE;
import static net.therap.scholarsphere.util.ViewName.PAPER_UPLOAD_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/8/24
 */
@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RatingService ratingService;

    @InitBinder("paper")
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "title",
                "summary",
                "publicationDate",
                "pdf",
                "conference",
                "authors",
                "tags"
        );

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Conference.class, new ConferenceEditor(conferenceService));
        binder.registerCustomEditor(Tag.class, new TagEditor(tagService));
        binder.registerCustomEditor(User.class, new UserEditor(userService));
    }

    @InitBinder("rating")
    public void RatingBinder(WebDataBinder binder) {
        binder.setAllowedFields("rating");
    }

    @GetMapping("/upload")
    public String showPaperUploadPage(Model model) {

        model.addAttribute("paper", new Paper());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("authors", userService.findAllUsers());

        return PAPER_UPLOAD_PAGE;
    }

    @PostMapping("/upload")
    public String uploadPaper(@Valid @ModelAttribute Paper paper,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tags", tagService.findAll());
            model.addAttribute("authors", userService.findAllUsers());

            return PAPER_UPLOAD_PAGE;
        }

        paperService.create(paper);

        return "redirect:/paper/upload";
    }

    @GetMapping("/{id}")
    public String showPaperDetailsPage(Model model,
                                       HttpSession session,
                                       @PathVariable(name = "id") long id,
                                       @RequestParam(name = "sort", defaultValue = "newest") String sort) {

        Paper paper = paperService.findById(id);

        if (isNull(paper) || !paper.isApproved()) {
            throw new EntityNotFoundException("No paper found with id: " + id);
        }

        UserDetails sessionUser = (UserDetails) session.getAttribute("user");
        User user = userService.findByEmail(sessionUser.getUsername());

//		Rating rating = ratingService.findRating(user, paper);

//		model.addAttribute("userRating", isNull(rating) ? 0 : rating.getRating());
        model.addAttribute("isSaved", paperService.isSaved(user.getId(), paper.getId()));
        model.addAttribute("paper", paper);
        model.addAttribute("user", user);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentService.findAll(paper.getId(), sort));

        return PAPER_DETAILS_PAGE;
    }

//	@PostMapping(RATE_PATH)
//	public String processRating(@PathVariable long paperId,
//	                            @RequestParam int rate,
//	                            HttpSession session) {
//
//		checkAccess(session, Role.REGULAR);
//
//		long userId = (long) session.getAttribute(ID_ATTR);
//
//		ratingService.save(rate, userId, paperId);
//
//		return REDIRECT_PAPER_DETAILS + paperId;
//	}
//
//	@PostMapping(UNDO_RATE_PATH)
//	public String processUndoRating(@RequestParam long paperId, HttpSession session) {
//
//		checkAccess(session, Role.REGULAR);
//
//		long userId = (long) session.getAttribute(ID_ATTR);
//
//		ratingService.delete(userId, paperId);
//
//		return REDIRECT_PAPER_DETAILS + paperId;
//	}
//
//	@PostMapping(CHANGE_RATE_PATH)
//	public String processChangeRating(@RequestParam long paperId,
//	                                  @RequestParam int rate,
//	                                  HttpSession session) {
//
//		long userId = (long) session.getAttribute(ID_ATTR);
//
//		ratingService.update(rate, userId, paperId);
//
//		return REDIRECT_PAPER_DETAILS + paperId;
//	}
//
//	@PostMapping("save-or-unsave")
//	public String processSaveOrUnsave(@RequestParam long paperId,
//	                                  @RequestParam String action,
//	                                  HttpSession session) {
//
//		long userId = (long) session.getAttribute(ID_ATTR);
//
//		paperService.saveOrUnsavePaper(userId, paperId, action);
//
//		return REDIRECT_PAPER_DETAILS + paperId;
//	}

    @GetMapping("/search")
    @ResponseBody
    public List<Paper> searchByTitle(@RequestParam(required = false) String title) {
        return paperService.searchByTitle(title);
    }
}

