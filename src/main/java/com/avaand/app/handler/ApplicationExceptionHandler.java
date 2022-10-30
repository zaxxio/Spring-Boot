package com.avaand.app.handler;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log
@Configuration
public class ApplicationExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public void handle(){
        log.info("Exception Handler");
    }

}
