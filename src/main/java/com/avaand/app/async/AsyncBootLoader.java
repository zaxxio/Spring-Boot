package com.avaand.app.async;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Log
@Component
public class AsyncBootLoader implements CommandLineRunner {

    private static int counter = 0;
    private static final ReentrantLock lock = new ReentrantLock(true);

    private static void computeTime1(){
        lock.lock();
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
        lock.unlock();
    }

    private static void computeTime2(){
        lock.lock();
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
        lock.unlock();
    }

    @Override
    public void run(String... args) throws Exception {

        Thread t1  = new Thread(AsyncBootLoader::computeTime1);
        Thread t2  = new Thread(AsyncBootLoader::computeTime2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        log.info("Counter : " + counter);
        Thread t3 = new Thread(AsyncBootLoader::computeTime1);
        Thread t4 = new Thread(AsyncBootLoader::computeTime2);
        t3.start();
        t4.start();

    }


}
