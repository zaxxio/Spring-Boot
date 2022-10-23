package com.avaand.app.event;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEvent;

@Log
@Getter
public class ApplicationEventManager<T> extends ApplicationEvent {
    private final Object target;
    private final boolean success;
    public ApplicationEventManager(Object source, T target, boolean success) {
        super(source);
        this.target = target;
        this.success = success;
    }
}
