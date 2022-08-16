package com.avaand.app.config;

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

    public Config(BankService bankService) {
        this.bankService = bankService;
    }

    @Bean
    @Primary
    public ProxyFactoryBean bankServiceMethodInterceptor() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(bankService);
        proxyFactoryBean.setInterceptorNames("bankServiceLogInterceptor");
        /*proxyFactoryBean.addListener(new AdvisedSupportListener() {
            @Override
            public void activated(AdvisedSupport advised) {
                System.out.println("A");
            }

            @Override
            public void adviceChanged(AdvisedSupport advised) {
                System.out.println("B");
            }
        });*/
        return proxyFactoryBean;
    }


}
