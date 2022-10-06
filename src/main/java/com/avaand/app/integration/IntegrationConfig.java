package com.avaand.app.integration;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Log
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public MessageChannel inputChannel(){
        return new DirectChannel();
    }

    @Bean
    public PollableChannel outputChannel(){
        return new QueueChannel(10);
    }

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public String sayHello(String name){
        return "Hello, " + name;
    }

}
