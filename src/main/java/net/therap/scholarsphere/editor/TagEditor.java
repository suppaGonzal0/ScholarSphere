package net.therap.scholarsphere.editor;

import net.therap.scholarsphere.service.TagService;

import java.beans.PropertyEditorSupport;

/**
 * @author mehzabinaothoi
 * @since 1/13/24
 */
public class TagEditor extends PropertyEditorSupport {

    private final TagService tagService;

    public TagEditor(TagService tagService) {
        this.tagService = tagService;

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        long id = Long.parseLong(text);
        setValue(tagService.findById(id));
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
