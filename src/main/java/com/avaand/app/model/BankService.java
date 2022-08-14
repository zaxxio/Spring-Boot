package com.avaand.app.model;

public interface BankService {
    boolean deposit(int amount);
    boolean withdraw(int amount);
    double balance();
}
