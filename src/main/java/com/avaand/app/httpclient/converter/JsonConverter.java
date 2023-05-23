package com.avaand.app.httpclient.converter;

import java.lang.reflect.Type;

public interface JsonConverter<T> {
    String toJson(T value);
    T fromJson(String jsonString, Type type);
}
