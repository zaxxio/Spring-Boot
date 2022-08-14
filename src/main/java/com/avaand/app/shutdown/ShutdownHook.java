package com.avaand.app.shutdown;

public abstract class ShutdownHook {

    public ShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                shutdown(System.currentTimeMillis());
            }
        });
    }


    protected abstract void shutdown(long time);
}
