package com.avaand.app.bootloader;

import com.avaand.app.async.AsynchronousExecutor;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Validator;
import java.util.Locale;

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
        var lifecycleA = context.getBean(LifeCycle.class); // calls new object prototype
        var lifeCycleB = context.getBean(LifeCycle.class); // calls new object prototype
        log.info(String.valueOf(lifecycleA.equals(lifeCycleB)));

        // Using Loop to demonstrate CacheManager
        var trackerService = context.getBean(TrackerServiceImpl.class);
        for (int i = 0; i < 10; i++) {
            trackerService.getTrackers();
            log.info("Loop Index -> " + i);
        }

        // Todo: Cache Manager will call the data from the cached data source. Makes it more efficient
        var tracker = new Tracker(1, null);
        for (int i = 0; i < 1000; i++) {
            trackerService.findTracker(tracker);
        }
        trackerService.findTracker(tracker);

        BankService bankService = context.getBean(BankServiceImpl.class);
        bankService.deposit(100);

        var asynchronousExecutor = context.getBean(AsynchronousExecutor.class);
        var result = asynchronousExecutor.asyncExecutionWithReturnType("Hello World");
        log.info(result.get());
        asynchronousExecutor.asyncExecution();

        var user = conversionService.convert("1,partha.com,12345", User.class);
        if (user != null){
            log.info(user.toString());
        }

        var violations = validator.validate(user);
        violations.iterator().forEachRemaining(violation -> {
            log.info(violation.getMessage());
        });

        Machine machine = new Machine();
        machine.setMachineName("OPTIMUS");
        machineService.start(machine);

        machine.setMachineId(1L);
        machineService.stop(machine);
        machineService.start(machine);

        var readableService = context.getBean(ReadableService.class);
        String value = readableService.sayHello();
        log.info("ReadableService : " + value);

        DirectChannel channel = (DirectChannel) context.getBean("inputChannel");

        DirectChannel outputChannel = (DirectChannel) context.getBean("outputChannel");
        outputChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.info("Message : " + message.getPayload());
            }
        });

        channel.send(MessageBuilder.withPayload("Mike").build());
        channel.send(MessageBuilder.withPayload("Mike").build());

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
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
