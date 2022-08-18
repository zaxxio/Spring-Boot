package com.avaand.app.config;

import com.avaand.app.interceptor.listener.BankServiceMethodInterceptorListener;
import com.avaand.app.model.BankService;
import lombok.extern.java.Log;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

@Log
@Configuration
@EnableAspectJAutoProxy
public class Config {

    private final BankService bankService;
    private final BankServiceMethodInterceptorListener bankServiceMethodInterceptorListener;


    public Config(BankService bankService, BankServiceMethodInterceptorListener bankServiceMethodInterceptorListener) {
        this.bankService = bankService;
        this.bankServiceMethodInterceptorListener = bankServiceMethodInterceptorListener;
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


}
