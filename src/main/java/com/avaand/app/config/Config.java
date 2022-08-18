package com.avaand.app.config;

import com.avaand.app.converter.StringToUserConverter;
import com.avaand.app.interceptor.listener.BankServiceMethodInterceptorListener;
import com.avaand.app.model.BankService;
import lombok.extern.java.Log;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

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

    @Bean("conversionService")
    @Primary
    public ConversionService conversionService(ApplicationContext ctx){
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set<Converter<?,?>> converters = new HashSet<>();
        converters.add(ctx.getBean(StringToUserConverter.class));
        conversionServiceFactoryBean.setConverters(converters);
        conversionServiceFactoryBean.afterPropertiesSet(); // Important afterPropertiesSet
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


}
