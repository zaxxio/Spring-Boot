package com.avaand.app.service;

public class Pasta implements Food{

    private String preparedFood;

    @Override
    public void prepareFood(String itemsRequired) {
        this.preparedFood = "The Food is prepared with " + itemsRequired;
    }

    @Override
    public String deliverFood() {
        return preparedFood;
    }
}
