package net.therap.scholarsphere.controller;

import jakarta.servlet.http.HttpSession;
import net.therap.scholarsphere.editor.PaperEditor;
import net.therap.scholarsphere.model.Comment;
import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.service.CommentService;
import net.therap.scholarsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("text", "paper.id");

		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Paper.class, new PaperEditor());
	}

	@PostMapping
	public String postComment(@ModelAttribute Comment comment, HttpSession session) {
		UserDetails sessionUser = (UserDetails) session.getAttribute("user");
		User user = userService.findByEmail(sessionUser.getUsername());

		commentService.save(comment, user);

		return "redirect:/paper/" + comment.getPaper().getId();
	}

	@PostMapping("/like")
	public String likeComment(@RequestParam(name = "userId") long userId,
	                          @RequestParam(name = "commentId") long commentId,
	                          @RequestParam(name = "paperId") long paperId) {

		commentService.addLike(userId, commentId);

		return "redirect:/paper/" + paperId;
	}

	@PostMapping("/unlike")
	public String unlikeComment(@RequestParam(name = "userId") long userId,
	                            @RequestParam(name = "commentId") long commentId,
	                            @RequestParam(name = "paperId") long paperId){

		commentService.unlike(userId, commentId);

		return "redirect:/paper/" + paperId;
	}
}
