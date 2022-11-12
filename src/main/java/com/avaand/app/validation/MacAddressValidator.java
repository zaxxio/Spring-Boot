package com.avaand.app.validation;

import com.avaand.app.utility.Utility;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MacAddressValidator implements ConstraintValidator<MacAddress,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;
        Pattern pattern = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
        return Utility.matchPattern(value, pattern);
    }
}
