package com.avaand.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private RestartEndpoint restartEndpoint;

    @GetMapping("/restart")
    public String restart(){
        restartEndpoint.restart();
        return "DONE";
    }

}
