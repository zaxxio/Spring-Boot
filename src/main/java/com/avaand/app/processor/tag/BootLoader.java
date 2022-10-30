package com.avaand.app.processor.tag;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Bean
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BootLoader {
    boolean value() default false;
}
