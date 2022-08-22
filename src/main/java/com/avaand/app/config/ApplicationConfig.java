package com.avaand.app.config;

import com.avaand.app.event.ApplicationEventManager;
import com.avaand.app.event.BoomEvent;
import lombok.extern.java.Log;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Locale;

@Log
@EnableAsync
@Configuration
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.avaand.app")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:application.properties")
@PropertySource("classpath:/i18n/message_en.properties")
public class ApplicationConfig {

    private final ApplicationEventPublisher eventPublisher;

    public ApplicationConfig(ApplicationEventPublisher eventPublisher) {
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
        messageSource.setDefaultLocale(Locale.FRENCH);
        messageSource.setBasename("classpath:/i18n/message");
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

    //@Scheduled(cron = "30/5 * * * * *")
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
