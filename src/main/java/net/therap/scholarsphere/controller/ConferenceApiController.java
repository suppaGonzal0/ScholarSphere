package net.therap.scholarsphere.controller;

import net.therap.scholarsphere.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mehzabinaothoi
 * @since 1/30/24
 */
@RestController
@RequestMapping("/conference-search")
public class ConferenceApiController {

	@Autowired
	private ConferenceService conferenceService;

	@GetMapping
	@ResponseBody
	public List<Map<String, Object>> searchByTitle(@RequestParam String title) {
		return conferenceService.searchByTitle(title)
				.stream()
				.map(conference -> {
					Map<String, Object> papers = new HashMap<>();
					papers.put("id", conference.getId());
					papers.put("title", conference.getTitle());

					return papers;
				})
				.collect(Collectors.toList());
	}
}
