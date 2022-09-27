package com.avaand.app.async;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log
@Component
public class AsyncBootLoader implements CommandLineRunner {

    private static int counter = 0;
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void compute1(){
        synchronized (lock1){
            for (int i = 0; i < 10000; i++) {
                counter++;
                log.info("Counter : "+counter+ " -> Thread : "+ Thread.currentThread().getName());
            }
        }
    }

    public static void compute2(){
        synchronized (lock2){
            for (int i = 0; i < 10000; i++) {
                counter++;
                log.info("Counter : "+counter+ " -> Thread : "+ Thread.currentThread().getName());
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        Thread t1  = new Thread(AsyncBootLoader::compute1);
        Thread t2  = new Thread(AsyncBootLoader::compute2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Counter : " + counter);
    }


}
