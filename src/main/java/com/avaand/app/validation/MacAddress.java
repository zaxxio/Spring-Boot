package com.avaand.app.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MacAddressValidator.class)
public @interface MacAddress {
}
