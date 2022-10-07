package com.avaand.app.integration;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;


@Log
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public DirectChannel inputChannel(){
        return new DirectChannel();
    }

    @Bean
    public DirectChannel outputChannel(){
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public Message<String> build(Message<String> message){
        return MessageBuilder.withPayload("Hello, " + message.getPayload()).build();
    }

}
