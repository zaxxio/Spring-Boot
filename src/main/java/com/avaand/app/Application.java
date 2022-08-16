package com.avaand.app;

import com.avaand.app.shutdown.ShutdownCallbackListener;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log
@SpringBootApplication
public class Application extends ShutdownCallbackListener {


    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }

    @Override
    protected void shutdown(long time) {
        log.info("Shutdown Callback");
    }
}
