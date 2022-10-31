package com.avaand.app.lifecycle;

import lombok.extern.java.Log;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Log
@Component
public class SmartBeanLifecycle implements SmartLifecycle {
    @Override
    public void start() {
        log.info("Bean Creation Started");
    }

    @Override
    public void stop() {
        log.info("Bean Creation Finished");
    }

    @Override
    public boolean isRunning() {
        log.info("Bean Creation Status");
        return false;
    }
}
