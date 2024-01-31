package net.therap.scholarsphere.converter;

import lombok.extern.slf4j.Slf4j;
import net.therap.scholarsphere.model.embedded.Pdf;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author mehzabinaothoi
 * @since 1/8/24
 */
@Slf4j
@Component
public class PdfConverter implements Converter<MultipartFile, Pdf> {

    @Override
    public Pdf convert(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            Pdf pdf = new Pdf();
            pdf.setName(file.getOriginalFilename());
            pdf.setFile(file.getBytes());

            return pdf;

        } catch (IOException e) {
            log.error(e.getMessage());

            return null;
        }
    }
}
