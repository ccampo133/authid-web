package com.authid.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.Map;

/**
 * @author Chris Campo
 */
@Controller
public class RootController {

    @RequestMapping("/")
    public String home(final Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("timestamp", Instant.now());
        return "home";
    }
}
