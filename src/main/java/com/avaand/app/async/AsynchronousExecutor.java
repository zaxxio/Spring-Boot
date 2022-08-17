package com.avaand.app.async;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Log
@Component
public class AsynchronousExecutor {

    @Async
    public void asyncExecution(){
        log.info("Asynchronous Execution without Return Type");
    }


    @Async
    public CompletableFuture<String> asyncExecutionWithReturnType(String name){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append(name+ " ");
        }
        return CompletableFuture.completedFuture(builder.toString());
    }
}
