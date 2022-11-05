package com.avaand.app.converter;

import com.avaand.app.converter.tag.ConverterService;
import com.avaand.app.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@ConverterService
public class StringToUserConverter implements Converter<String, User> {
    @Override
    public User convert(String source) {
        String[] split = source.split(",");
        return new User(UUID.fromString(split[0]).toString(), split[1], split[2], null, null);
    }
}

