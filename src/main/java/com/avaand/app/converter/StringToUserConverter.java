package com.avaand.app.converter;

import com.avaand.app.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserConverter implements Converter<String, User> {
    @Override
    public User convert(String source) {
        String[] split = source.split(",");
        User user = new User(Double.parseDouble(split[0]), split[1], split[2]);
        return user;
    }
}
