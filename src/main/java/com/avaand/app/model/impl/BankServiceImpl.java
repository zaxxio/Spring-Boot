package com.avaand.app.model.impl;

import com.avaand.app.model.BankService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component("bankServiceImpl")
public class BankServiceImpl implements BankService {
    @Override
    public boolean deposit(int amount) {
        log.info("Before Deposit");
        return false;
    }

    @Override
    public boolean withdraw(int amount) {
        return false;
    }

    @Override
    public double balance() {
        return 0;
    }
}
