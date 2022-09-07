package com.avaand.app.config;

import com.avaand.app.condition.IfOnUnixBuildSystem;
import com.avaand.app.converter.tag.ConverterService;
import com.avaand.app.event.ApplicationEventManager;
import com.avaand.app.event.BoomEvent;
import com.avaand.app.interceptor.listener.BankServiceMethodInterceptorListener;
import com.avaand.app.model.BankService;
import com.avaand.app.proccesor.OperatingSystem;
import lombok.extern.java.Log;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
public class Config {

    private final ApplicationEventPublisher eventPublisher;
    private final BankService bankService;
    private final BankServiceMethodInterceptorListener bankServiceMethodInterceptorListener;

    public Config(ApplicationEventPublisher eventPublisher,
                  BankService bankService,
                  BankServiceMethodInterceptorListener bankServiceMethodInterceptorListener) {
        this.eventPublisher = eventPublisher;
        this.bankService = bankService;
        this.bankServiceMethodInterceptorListener = bankServiceMethodInterceptorListener;
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

    @EventListener(condition = "#eventManager.success")
    public void eventListener(ApplicationEventManager<String> eventManager){
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
        eventPublisher.publishEvent(new ApplicationEventManager<>(this, "A", true));
    }

    //@Scheduled(fixedRate = 1000)
    public void fixedRateScheduling(){
        log.info("Fixed Rate : " + System.currentTimeMillis() / 1000);
    }

    @Bean("conversionService")
    @Primary
    public ConversionService conversionService(ApplicationContext ctx){
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set<Converter<?,?>> converters = new HashSet<>();
        Map<String, Object> ctxMap = ctx.getBeansWithAnnotation(ConverterService.class);
        for (Map.Entry<String, Object> entry : ctxMap.entrySet()) {
            converters.add((Converter<?, ?>) entry.getValue());
        }
        conversionServiceFactoryBean.setConverters(converters);
        conversionServiceFactoryBean.afterPropertiesSet(); // important afterPropertiesSet
        return conversionServiceFactoryBean.getObject();
    }

    @Bean
    @Primary
    public ProxyFactoryBean bankServiceMethodInterceptor() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(bankService);
        proxyFactoryBean.setInterceptorNames("bankServiceLogInterceptor");
        proxyFactoryBean.addListener(bankServiceMethodInterceptorListener);
        return proxyFactoryBean;
    }

    @Bean
    @Conditional(IfOnUnixBuildSystem.class)
    public OperatingSystem.UnixOS unixOS(){
        return new OperatingSystem.UnixOS();
    }

    @Bean
    @ConditionalOnProperty(value = "operatingSystem.linux", havingValue = "true")
    public OperatingSystem.LinuxOS linuxOS(){
        log.info("I am from linux");
        return new OperatingSystem.LinuxOS();
    }

}
