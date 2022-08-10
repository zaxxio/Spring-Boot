package com.avaand.app.event;

import lombok.extern.java.Log;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Log
@Component
public class TickEvent extends ApplicationEvent {

    
    public TickEvent(Object source) {
        super(source);
    }
}
