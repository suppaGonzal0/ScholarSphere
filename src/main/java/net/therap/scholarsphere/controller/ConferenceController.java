package net.therap.scholarsphere.controller;

import jakarta.validation.Valid;
import net.therap.scholarsphere.model.Conference;
import net.therap.scholarsphere.service.ConferenceService;
import net.therap.scholarsphere.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;
import static net.therap.scholarsphere.util.ViewName.CONFERENCE_PAGE;
import static net.therap.scholarsphere.util.ViewName.CREATE_CONFERENCE_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/9/24
 */
@Controller
@RequestMapping("/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private PaperService paperService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("title", "location.country", "location.city");

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showConferenceCreatePage(Model model) {
        model.addAttribute("conference", new Conference());
        model.addAttribute("conferenceList", conferenceService.findAll());

        return CREATE_CONFERENCE_PAGE;
    }

    @PostMapping
    public String createConference(@Valid @ModelAttribute Conference conference,
                                   BindingResult bindingResult,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("conferenceList", conferenceService.findAll());

            return CREATE_CONFERENCE_PAGE;
        }

        if (nonNull(conferenceService.findByTitle(conference.getTitle()))) {
            model.addAttribute("conferenceList", conferenceService.findAll());

            bindingResult.rejectValue("title", "error.duplicate.property");

            return CREATE_CONFERENCE_PAGE;
        }

        conferenceService.save(conference);

        return "redirect:/conference";
    }

    @GetMapping("/{id}")
    public String showConferencePage(@PathVariable Long id, Model model) {
        Conference conference = conferenceService.findById(id);

        model.addAttribute("conference", conference);
        model.addAttribute("papers", paperService.findAllByConference(conference.getId()));

        return CONFERENCE_PAGE;
    }
}
