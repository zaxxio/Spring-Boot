package com.avaand.app.listener;

import com.avaand.app.event.BoomEvent;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Log
@Component
public class BoomEventListener implements ApplicationListener<BoomEvent> {
    @Override
    public void onApplicationEvent(BoomEvent event) {
        log.info("Boom Event Listener : " + event.getMessage());
    }

}
