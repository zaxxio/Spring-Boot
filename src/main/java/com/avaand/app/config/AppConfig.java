package com.avaand.app.config;

import com.avaand.app.event.ApplicationEventManager;
import com.avaand.app.event.BoomEvent;
import lombok.extern.java.Log;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Log
@EnableAsync
@Configuration
@EnableScheduling
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConfigurationPropertiesScan("com.avaand.app")
@PropertySource("classpath:application.properties")
public class AppConfig {

    private final ApplicationEventPublisher eventPublisher;

    public AppConfig(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Bean("applicationEventMulticaster")
    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setBasename("/i18n/message");
        return messageSource;
    }

    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("TaskScheduler");
        return threadPoolTaskScheduler;
    }

    @EventListener(condition = "#event.success == true")
    public void eventListener(ApplicationEventManager<String> event){
        log.info("Application Event is a success !!");
    }

    //@Scheduled(fixedDelay = 1000)
    public void fixedDelayScheduling(){
        log.info("Fixed Delay : " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "30/5 * * * * *")
    public void cronScheduling(){
        log.info("Cron Triggered");
        eventPublisher.publishEvent(new BoomEvent(this,"I am triggered to listen from Boom Event"));
        eventPublisher.publishEvent(new ApplicationEventManager<String>(this,"A", true));
    }

    //@Scheduled(fixedRate = 1000)
    public void fixedRateScheduling(){
        log.info("Fixed Rate : " + System.currentTimeMillis() / 1000);
    }

}
