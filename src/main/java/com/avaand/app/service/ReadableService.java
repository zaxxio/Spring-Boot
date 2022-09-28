package com.avaand.app.service;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable(preConstruction = true)
public class ReadableService {

    private final AutoInjected autoInjected;

    public ReadableService(AutoInjected autoInjected) {
        this.autoInjected = autoInjected;
    }

    public String sayHello() {
        System.out.println("Hello World");
       return autoInjected.sayHello();
    }
}
