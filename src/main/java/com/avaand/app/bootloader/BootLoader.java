package com.avaand.app.bootloader;

import com.avaand.app.async.AsynchronousExecutor;
import com.avaand.app.cache.TrackerService;
import com.avaand.app.cache.impl.TrackerServiceImpl;
import com.avaand.app.cache.model.Tracker;
import com.avaand.app.domain.User;
import com.avaand.app.lifecycle.LifeCycle;
import com.avaand.app.machine.domain.Machine;
import com.avaand.app.machine.service.MachineService;
import com.avaand.app.model.BankService;
import com.avaand.app.model.impl.BankServiceImpl;
import com.avaand.app.service.FoodType;
import com.avaand.app.service.ReadableService;
import com.avaand.app.service.Waiter;
import com.avaand.app.system.props.ConfigProperties;
import lombok.extern.java.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Log
@Component
public class BootLoader implements CommandLineRunner, ApplicationContextAware {

    private final Waiter waiter;
    private final ConfigProperties configProperties;
    private ApplicationContext context;
    private final MessageSource messageSource;
    private final ConversionService conversionService;
    private final Validator validator;

    private final MachineService machineService;

    @PostConstruct
    public void onCreate(){
        log.info("On Create Method");
    }

    public BootLoader(Waiter waiter,
                      ConfigProperties configProperties,
                      ApplicationContext context,
                      MessageSource messageSource,
                      ConversionService conversionService,
                      Validator validator,
                      MachineService machineService) {
        this.waiter = waiter;
        this.configProperties = configProperties;
        this.context = context;
        this.messageSource = messageSource;
        this.conversionService = conversionService;
        this.validator = validator;
        this.machineService = machineService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(messageSource.getMessage("welcome",null, Locale.US));

        try {
            log.info(waiter.deliverFood(FoodType.PIZZA));
        }catch (Exception e){
            log.info(e.getMessage());
        }

        log.info(configProperties.getUsername());
        LifeCycle lifecycleA = context.getBean(LifeCycle.class); // calls new object prototype
        LifeCycle lifeCycleB = context.getBean(LifeCycle.class); // calls new object prototype
        log.info(String.valueOf(lifecycleA.equals(lifeCycleB)));

        // Using Loop to demonstrate CacheManager
        TrackerService trackerService = context.getBean(TrackerServiceImpl.class);
        for (int i = 0; i < 10; i++) {
            trackerService.getTrackers();
            log.info("Loop Index -> " + i);
        }

        // Todo: Cache Manager will call the data from the cached data source. Makes it more efficient
        Tracker tracker = new Tracker(1, null);
        for (int i = 0; i < 1000; i++) {
            trackerService.findTracker(tracker);
        }
        trackerService.findTracker(tracker);

        BankService bankService = context.getBean(BankServiceImpl.class);
        bankService.deposit(100);

        AsynchronousExecutor asynchronousExecutor = context.getBean(AsynchronousExecutor.class);
        CompletableFuture<String> result = asynchronousExecutor.asyncExecutionWithReturnType("Hello World");
        log.info(result.get());
        asynchronousExecutor.asyncExecution();

        User user = conversionService.convert("1,partha.com,12345", User.class);
        if (user != null){
            log.info(user.toString());
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        violations.iterator().forEachRemaining(violation -> {
            log.info(violation.getMessage());
        });

        Machine machine = new Machine();
        machine.setMachineName("OPTIMUS");
        machineService.start(machine);

        machine.setMachineId(1L);
        machineService.stop(machine);
        machineService.start(machine);

        ReadableService readableService = context.getBean(ReadableService.class);
        readableService.sayHello();

    }

    @Bean
    public AnnotationBeanConfigurerAspect annotationBeanConfigurerAspect(){
        return AnnotationBeanConfigurerAspect.aspectOf();
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
