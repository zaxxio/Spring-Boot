package com.avaand.app;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Log
@SpringBootApplication
@ComponentScan(basePackages = "com.avaand")
public class Application {

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner commandLineRunner2(){
        return args -> {
            log.info("Command Line Runner 2");
        };
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner commandLineRunner1(){
        return args -> {
            log.info("Command Line Runner 1");
        };
    }

}
