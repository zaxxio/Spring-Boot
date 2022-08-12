package com.avaand.app.bootloader;

import com.avaand.app.service.FoodType;
import com.avaand.app.service.Waiter;
import com.avaand.app.system.props.ConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log
@Component
public class BootLoader implements CommandLineRunner {

    /*@Resource(name = "A")
    private A a;*/
    private final Waiter waiter;
    private final ConfigProperties configProperties;

    @PostConstruct
    public void onCreate(){
        log.info("On Create Method");
    }

    public BootLoader(Waiter waiter, ConfigProperties configProperties) {
        this.waiter = waiter;
        this.configProperties = configProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(waiter.deliverFood(FoodType.PIZZA));
        log.info(configProperties.getUsername());
    }

    @PreDestroy
    public void onDestroy(){
        log.info("On Destroy Method");
    }

}
