package com.avaand.app.config;

import com.avaand.app.model.BankService;
import jdk.jfr.MemoryAddress;
import lombok.extern.java.Log;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisedSupportListener;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@Log
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=false)
public class Config {


    @Autowired
    private BankService bankService;

    @EventListener(classes = ContextClosedEvent.class)
    public void closedEventListener(){
        log.info("Application Exit");
    }

    @Bean
    @Primary
    public ProxyFactoryBean bankServiceMethodInterceptor() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(bankService);
        proxyFactoryBean.setInterceptorNames("bankServiceLogInterceptor");
        proxyFactoryBean.addListener(new AdvisedSupportListener() {
            @Override
            public void activated(AdvisedSupport advised) {
                System.out.println("A");
            }

            @Override
            public void adviceChanged(AdvisedSupport advised) {
                System.out.println("B");
            }
        });
        return proxyFactoryBean;
    }


}
