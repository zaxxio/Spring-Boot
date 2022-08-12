package com.avaand.app.event;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEvent;

@Log
@Getter
public class BoomEvent extends ApplicationEvent {

    private final String message;

    public BoomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
