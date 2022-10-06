package com.avaand.app.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Log
@Component
@Configurable(preConstruction = true)
public class ReadableService {

    private final AutoInjected autoInjected;

    public ReadableService(AutoInjected autoInjected) {
        this.autoInjected = autoInjected;
    }

    public String sayHello() {
        return autoInjected.sayHello();
    }

}
