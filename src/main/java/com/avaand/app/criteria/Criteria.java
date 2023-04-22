package com.avaand.app.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Criteria<T> {

    private final List<Filter<T>> filters;

    public Criteria() {
        this.filters = new ArrayList<>();
    }

    protected void addFilter(Predicate<T> predicate){
        this.filters.add(new PredicateFilter<T>(predicate));
    }

    protected void addFilter(Filter<T> filter){
        this.filters.add(filter);
    }

    public List<T> apply(List<T> items) {
        List<T> filtered = new ArrayList<>();
        for (T item : items) {
            boolean match = true;
            for (Filter<T> filter : filters) {
                if (!filter.match(item)){
                    match = false;
                    break;
                }
            }
            if (match){
                filtered.add(item);
            }
        }
        return filtered;
    }
}
