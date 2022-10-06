package com.avaand.app.processor.config;

import com.avaand.app.service.ReadableService;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@Log
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class ProcessConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = ReadableService.class)
    public void sayHello(){
        log.info("Readable Service is found.");
    }

}
