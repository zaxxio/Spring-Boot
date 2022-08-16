package com.avaand.app.shutdown;

public abstract class ShutdownCallbackListener {

    public ShutdownCallbackListener(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(System.currentTimeMillis())));
    }


    protected abstract void shutdown(long time);
}
