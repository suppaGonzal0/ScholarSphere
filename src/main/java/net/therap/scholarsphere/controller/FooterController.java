package net.therap.scholarsphere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static net.therap.scholarsphere.util.ViewName.*;

/**
 * @author mehzabinaothoi
 * @since 1/30/24
 */
@Controller
public class FooterController {

	@GetMapping("/terms")
	public String showTermsOfUsagePage() {
		return TERMS_PAGE;
	}

	@GetMapping("/privacy")
	public String showTPrivacyPolicyPage() {
		return PRIVACY_PAGE;
	}

	@GetMapping("/ethics")
	public String showCodeOfEthicsPage() {
		return ETHICS_PAGE;
	}
}
