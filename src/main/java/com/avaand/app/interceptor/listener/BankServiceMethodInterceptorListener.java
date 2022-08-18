package com.avaand.app.interceptor.listener;

import lombok.extern.java.Log;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisedSupportListener;
import org.springframework.stereotype.Component;

@Log
@Component
public class BankServiceMethodInterceptorListener implements AdvisedSupportListener {
    @Override
    public void activated(AdvisedSupport advised) {
        log.info("Bank Advise Activated.");
    }

    @Override
    public void adviceChanged(AdvisedSupport advised) {
        log.info("Bank Advise Changed.");
    }
}
