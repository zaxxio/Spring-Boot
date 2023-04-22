package com.avaand.app.criteria;

import java.util.function.Predicate;

public class PredicateFilter<T> implements Filter<T>{

    private final Predicate<T> predicate;

    public PredicateFilter(Predicate<T> predicate){
        this.predicate = predicate;
    }

    @Override
    public boolean match(T t) {
        try {
            return predicate.test(t);
        }catch (Exception ex){
            return false;
        }
    }
}
