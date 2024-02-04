package net.therap.scholarsphere.controller;

import net.therap.scholarsphere.model.Paper;
import net.therap.scholarsphere.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mehzabinaothoi
 * @since 1/30/24
 */
@RestController
@RequestMapping("/paper-download")
public class PaperApiController {

	@Autowired
	private PaperService paperService;

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
		Paper paper = paperService.findById(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.builder("attachment")
				.filename(paper.getPdf().getName())
				.build());

		paperService.incrementDownloadCount(paper);

		return new ResponseEntity<>(paper.getPdf().getFile(), headers, HttpStatus.OK);
	}
}
