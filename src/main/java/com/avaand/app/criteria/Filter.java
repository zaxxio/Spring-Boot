package com.avaand.app.criteria;

public interface Filter<T> {
    boolean match(T t);
}
