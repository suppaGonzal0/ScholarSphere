package net.therap.scholarsphere.editor;

import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * @author mehzabinaothoi
 * @since 1/12/24
 */
@Component
public class PaperEditor extends PropertyEditorSupport {

    @Autowired
    private PaperService paperService;

    @Override
    public void setAsText(String text) {
        long paperId = Long.parseLong(text);
        Paper paper = paperService.findById(paperId);

        setValue(paper);
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}