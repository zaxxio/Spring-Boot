package com.avaand.app.processor;

import lombok.extern.java.Log;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Log
@Component
public class Monitor implements SmartLifecycle, LifecycleProcessor {
    @Override
    public void start() {
        log.info("SmartLifeCycle ON");
    }

    @Override
    public void stop() {

        // Trigger when the shutdownHook is called.

    }

    @Override
    public boolean isRunning() {
        // Todo : context or object reference check output
        return true;
    }

    @Override
    public void onRefresh() {
        // on refreshed container event
    }

    @Override
    public void onClose() {
        // closed container.
    }
}
