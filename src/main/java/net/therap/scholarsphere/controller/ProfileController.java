package net.therap.scholarsphere.controller;

import jakarta.servlet.http.HttpSession;
import net.therap.scholarsphere.model.User;
import net.therap.scholarsphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;
import static net.therap.scholarsphere.util.ViewName.*;

/**
 * @author mehzabinaothoi
 * @since 1/13/24
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "id",
                "username",
                "email",
                "password",
                "fullName.firstName",
                "fullName.lastName",
                "location.city",
                "location.country",
                "bio"
        );

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showProfilePage(@RequestParam(required = false) Long id,
                                  Model model,
                                  HttpSession session) {

        UserDetails sessionUser = (UserDetails) session.getAttribute("user");
        User loggedInUser = userService.findByEmail(sessionUser.getUsername());

        System.out.println(loggedInUser.getPapers());

        if (nonNull(id) && !loggedInUser.getId().equals(id)) {
            User user = userService.findById(id);

            model.addAttribute("isMyProfile", false);
            model.addAttribute("user", user);
            model.addAttribute("isFollowed", loggedInUser.getFollowings().contains(user));

        } else {
            model.addAttribute("isMyProfile", true);
            model.addAttribute("user", loggedInUser);
        }

        return PROFILE_PAGE;
    }

    @GetMapping("/follower/{id}")
    public String showFollowersPage(@PathVariable long id, Model model) {
        userService.findById(id);

        model.addAttribute("userList", userService.getFollowers(id));

        return FOLLOWERS_PAGE;
    }

    @GetMapping("/following/{id}")
    public String showFollowingsPage(@PathVariable long id, Model model) {
        userService.findById(id);

        model.addAttribute("userList", userService.getFollowings(id));

        return FOLLOWINGS_PAGE;
    }

//    @GetMapping(EDIT_PATH)
//    public String showProfileEditPage(Model model, HttpSession session) {
//        checkAccess(session, Role.REGULAR);
//
//        long id = getUserId(session);
//        User user = userService.findById(id);
//
//        model.addAttribute("user", user);
//
//        return PROFILE_EDIT_PAGE;
//    }
//
//    @PostMapping(EDIT_PATH)
//    public String editProfile(@Valid @ModelAttribute User user,
//                              BindingResult bindingResult,
//                              HttpSession session) {
//
//        checkAccess(session, Role.REGULAR);
//
//        if (bindingResult.hasErrors()) {
//            return PROFILE_EDIT_PAGE;
//        }
//
//        userService.update(user);
//
//        return REDIRECT_PROFILE_EDIT;
//    }

    @PostMapping("/follow_or_unfollow")
    public String processFollowOrUnfollow(@RequestParam long followingId,
                                          @RequestParam String action,
                                          HttpSession session) {

        UserDetails sessionUser = (UserDetails) session.getAttribute("user");
        User loggedInUser = userService.findByEmail(sessionUser.getUsername());

        userService.followOrUnfollow(loggedInUser.getId(), followingId, action);

        return "redirect:/profile?id=" + followingId;
    }

//    @GetMapping("/saved")
//    public String showSavedPapersPage(HttpSession session, Model model) {
//        checkAccess(session, Role.REGULAR);
//
//        long id = getUserId(session);
//
//        model.addAttribute("savedPapers", paperService.findSavedPapers(id));
//
//        return SAVED_PAPERS_PAGE;
//    }
}
