package com.authid.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.Instant;
import java.util.Map;

/**
 * @author Chris Campo
 */
@Controller
public class RootController {

    @RequestMapping("/")
    public String home(final Map<String, Object> model) {
        model.put("message", "Welcome to AuthID");
        model.put("timestamp", Instant.now());
        return "home";
    }

    @RequestMapping("/login")
    public String login(final Principal principal) {
        if (principal != null && ((Authentication) principal).isAuthenticated()) {
            return "forward:/";
        }
        return "login";
    }
}
