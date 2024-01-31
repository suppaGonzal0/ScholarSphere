package net.therap.scholarsphere.editor;

import net.therap.scholarsphere.service.UserService;

import java.beans.PropertyEditorSupport;

/**
 * @author mehzabinaothoi
 * @since 1/15/24
 */
public class UserEditor extends PropertyEditorSupport {

    private final UserService userService;

    public UserEditor(UserService userService) {
        this.userService = userService;

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        long id = Long.parseLong(text);
        setValue(userService.findById(id));
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
