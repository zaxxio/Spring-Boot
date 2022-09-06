package com.avaand.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IpAddressValidator.class)
public @interface IpAddress {
    String message() default "Ip address is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
