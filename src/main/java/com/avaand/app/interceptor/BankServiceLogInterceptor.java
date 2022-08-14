package com.avaand.app.interceptor;

import lombok.extern.java.Log;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log
@Component("bankServiceLogInterceptor")
public class BankServiceLogInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("Before Advice");
        Method method = invocation.getMethod();
        log.info("After Advice");
        return invocation.proceed();
    }
}
