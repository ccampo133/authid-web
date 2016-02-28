package com.authid.web.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Chris Campo
 */
@RestController
public class VersionController {

    public static final String DEFAULT_VERSION = "0.0.0.dev";

    private final String version;

    public VersionController() {
        this.version = Optional.ofNullable(getClass().getPackage().getImplementationVersion()).orElse(DEFAULT_VERSION);
    }

    @NotNull
    @RequestMapping("/version")
    public String getVersion() {
        return version;
    }
}