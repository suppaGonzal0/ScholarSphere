package net.therap.scholarsphere.editor;

import net.therap.scholarsphere.service.ConferenceService;

import java.beans.PropertyEditorSupport;

/**
 * @author mehzabinaothoi
 * @since 1/9/24
 */
public class ConferenceEditor extends PropertyEditorSupport {

    private final ConferenceService conferenceService;

    public ConferenceEditor(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            long id = Long.parseLong(text);
            setValue(conferenceService.findById(id));

        } catch (NumberFormatException e) {
            setValue(null);
        }
    }
}
