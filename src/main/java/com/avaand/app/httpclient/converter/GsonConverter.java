package com.avaand.app.httpclient.converter;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonConverter implements JsonConverter<Object> {
    private final Gson gson = new Gson();

    @Override
    public String toJson(Object value) {
        return gson.toJson(value);
    }

    @Override
    public Object fromJson(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }
}
