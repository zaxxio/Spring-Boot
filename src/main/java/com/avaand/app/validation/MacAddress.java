package com.avaand.app.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MacAddressValidator.class)
public @interface MacAddress {
    String message() default "Mac address is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
