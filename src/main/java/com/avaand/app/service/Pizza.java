package com.avaand.app.service;

import lombok.RequiredArgsConstructor;

public class Pizza implements Food{

    private String prepareFood;

    @Override
    public void prepareFood(String itemsRequired) {
        prepareFood = "The Pizza is prepared with :" + itemsRequired;
    }

    @Override
    public String deliverFood() {
        return prepareFood;
    }
}
