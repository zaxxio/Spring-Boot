package com.avaand.app.event;

import org.springframework.context.ApplicationEvent;

public class StartupEvent<T> extends ApplicationEvent {

    private final T ctx;

    public StartupEvent(Object source, T ctx) {
        super(source);
        this.ctx = ctx;
    }

    public T getCtx() {
        return ctx;
    }

}
