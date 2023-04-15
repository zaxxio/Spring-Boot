package com.avaand.app.controller;

import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final RestartEndpoint restartEndpoint;

    public IndexController(RestartEndpoint restartEndpoint) {
        this.restartEndpoint = restartEndpoint;
    }

    @GetMapping(value = "/hi", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> sayHello(){
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    }

    @GetMapping("/restart")
    public String restart(){
        restartEndpoint.restart();
        return "DONE";
    }

}
