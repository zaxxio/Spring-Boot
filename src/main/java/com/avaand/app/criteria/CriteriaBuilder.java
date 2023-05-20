package com.avaand.app.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CriteriaBuilder<T> {
    private final Criteria<T> criteria;
    private final List<T> filteredItems = new ArrayList<>();

    public CriteriaBuilder(){
        this.criteria = new Criteria<>();
    }

    public CriteriaBuilder<T> where(Predicate<T> predicate){
        this.criteria.addFilter(predicate);
        return this;
    }

    public CriteriaBuilder<T> filter(Filter<T> filter){
        this.criteria.addFilter(filter);
        return this;
    }

    public Criteria<T> build(){
        return criteria;
    }

}
