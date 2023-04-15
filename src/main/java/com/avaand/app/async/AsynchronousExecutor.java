package com.avaand.app.async;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Log
@Component
public class AsynchronousExecutor {

    @Async
    public void asyncExecution(){
        log.info("Asynchronous Execution without Return Type");
    }

    @Async
    public CompletableFuture<String> asyncExecutionWithReturnType(String name){
        return CompletableFuture.supplyAsync(() -> name);
    }

}
