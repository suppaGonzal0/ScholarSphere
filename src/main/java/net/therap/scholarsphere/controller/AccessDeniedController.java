package net.therap.scholarsphere.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access-denied")
public class AccessDeniedController {

    @RequestMapping
    public String showAccessDeniedPage() {
        throw new AccessDeniedException("Access Denied");
    }
}
