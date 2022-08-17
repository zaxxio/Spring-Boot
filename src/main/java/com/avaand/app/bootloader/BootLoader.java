package com.avaand.app.bootloader;

import com.avaand.app.async.AsynchronousExecutor;
import com.avaand.app.cache.impl.TrackerServiceImpl;
import com.avaand.app.cache.model.Tracker;
import com.avaand.app.lifecycle.LifeCycle;
import com.avaand.app.model.BankService;
import com.avaand.app.model.impl.BankServiceImpl;
import com.avaand.app.service.FoodType;
import com.avaand.app.service.Waiter;
import com.avaand.app.system.props.ConfigProperties;
import lombok.extern.java.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Log
@Component
public class BootLoader implements CommandLineRunner, ApplicationContextAware {

    /*@Resource(name = "A")
    private A a;*/
    private final Waiter waiter;
    private final ConfigProperties configProperties;

    private ApplicationContext context;

    @Autowired
    private MessageSource messageSource;


    @PostConstruct
    public void onCreate(){
        log.info("On Create Method");
    }

    public BootLoader(Waiter waiter, ConfigProperties configProperties) {
        this.waiter = waiter;
        this.configProperties = configProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(messageSource.getMessage("welcome",null, Locale.FRENCH));
        log.info(waiter.deliverFood(FoodType.PIZZA));
        log.info(configProperties.getUsername());
        LifeCycle lifecycleA = context.getBean(LifeCycle.class); // calls new object prototype
        LifeCycle lifeCycleB = context.getBean(LifeCycle.class); // calls new object prototype
        log.info(String.valueOf(lifecycleA.equals(lifeCycleB)));

        // Using Loop to demonstrate CacheManager
        TrackerServiceImpl trackerService = context.getBean(TrackerServiceImpl.class);
        for (int i = 0; i < 10; i++) {
            trackerService.getTrackers();
            log.info("Loop Index : " + i);
        }
        // Cache Manager will call the data from the cached data source. Makes it more efficient
        trackerService.findTracker(new Tracker(1, null));
        trackerService.findTracker(new Tracker(1, null));


        BankService bankService = context.getBean(BankServiceImpl.class);
        bankService.deposit(100);


        AsynchronousExecutor asynchronousExecutor = context.getBean(AsynchronousExecutor.class);
        CompletableFuture<String> result = asynchronousExecutor.asyncExecutionWithReturnType("Hello World");
        log.info(result.get());
        asynchronousExecutor.asyncExecution();

    }

    @PreDestroy
    public void onDestroy(){
        log.info("On Destroy Method");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
