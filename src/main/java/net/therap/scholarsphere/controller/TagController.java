package net.therap.scholarsphere.controller;

import jakarta.validation.Valid;
import net.therap.scholarsphere.model.Tag;
import net.therap.scholarsphere.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;
import static net.therap.scholarsphere.util.ViewName.CREATE_TAG_PAGE;
import static net.therap.scholarsphere.util.ViewName.TAG_PAGE;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Controller
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("name");

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showTagCreatePage(Model model) {
        model.addAttribute("tag", new Tag());
        model.addAttribute("tagList", tagService.findAll());

        return CREATE_TAG_PAGE;
    }

    @PostMapping
    public String createTag(@Valid @ModelAttribute Tag tag,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {
            return CREATE_TAG_PAGE;
        }

        if (nonNull(tagService.findByName(tag.getName()))) {
            bindingResult.rejectValue("name", "error.duplicate.property");

            model.addAttribute("tagList", tagService.findAll());

            return CREATE_TAG_PAGE;
        }

        tagService.save(tag);

        return "redirect:/tag";
    }

    @GetMapping("/{id}")
    public String showTagPage(@PathVariable Long id, Model model) {
        Tag tag = tagService.findById(id);

        model.addAttribute("tag", tag);

        return TAG_PAGE;
    }
}
