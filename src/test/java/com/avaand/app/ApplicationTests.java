package com.avaand.app;

import com.avaand.app.processor.OperatingSystem;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Log
@SpringBootTest
@EnableJpaRepositories
class ApplicationTests {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void contextLoads() {
        OperatingSystem.UnixOS unixOS = ctx.getBean(OperatingSystem.UnixOS.class);
        Assertions.assertInstanceOf(OperatingSystem.UnixOS.class, unixOS);
    }

}
