//package net.therap.scholarsphere.controller;
//
//import net.therap.scholarsphere.editor.PaperEditor;
//import net.therap.scholarsphere.enums.Role;
//import net.therap.scholarsphere.model.Comment;
//import net.therap.scholarsphere.model.Paper;
//import net.therap.scholarsphere.service.CommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.StringTrimmerEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//
//import static net.therap.scholarsphere.utils.Paths.*;
//import static net.therap.scholarsphere.utils.RedirectPaths.REDIRECT_PAPER_DETAILS;
//import static net.therap.scholarsphere.utils.util.checkAccess;
//import static net.therap.scholarsphere.utils.util.getUserId;
//
///**
// * @author mehzabinaothoi
// * @since 1/12/24
// */
//@Controller
//@RequestMapping(COMMENT_PATH)
//public class CommentController {
//
//    @Autowired
//    private CommentService commentService;
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setAllowedFields("text", "paper.id");
//
//        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//        binder.registerCustomEditor(Paper.class, new PaperEditor());
//    }
//
//    @PostMapping
//    public String postComment(@ModelAttribute Comment comment, HttpSession session) {
//        checkAccess(session, Role.REGULAR);
//
//        commentService.save(comment, getUserId(session));
//
//        return REDIRECT_PAPER_DETAILS + comment.getPaper().getId();
//    }
//
//    @PostMapping(LIKE_PATH)
//    public String likeComment(@RequestParam long userId,
//                              @RequestParam long commentId,
//                              @RequestParam long paperId,
//                              HttpSession session) {
//
//        checkAccess(session, Role.REGULAR);
//
//        commentService.addLike(userId, commentId);
//
//        return REDIRECT_PAPER_DETAILS + paperId;
//    }
//
//    @PostMapping(UNLIKE_PATH)
//    public String unlikeComment(@RequestParam long userId,
//                                @RequestParam long commentId,
//                                @RequestParam long paperId,
//                                HttpSession session) {
//
//        checkAccess(session, Role.REGULAR);
//
//        commentService.unlike(userId, commentId);
//
//        return REDIRECT_PAPER_DETAILS + paperId;
//    }
//}
