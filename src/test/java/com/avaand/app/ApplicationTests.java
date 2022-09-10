package com.avaand.app;

import com.avaand.app.proccesor.OperatingSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void contextLoads() {
        OperatingSystem.UnixOS unixOS = ctx.getBean(OperatingSystem.UnixOS.class);
        Assertions.assertInstanceOf(OperatingSystem.UnixOS.class, unixOS);
    }

}
