package com.avaand.app;

import com.avaand.app.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest
public class ConversionServiceTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    void conversionService(){

    }

}
