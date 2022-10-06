package com.avaand.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Waiter {

    public String deliverFood(FoodType foodType) throws Exception{
        Ingredient ingredient = new Ingredient();
        switch (foodType){
            case PASTA -> {
                Pasta pasta = new Pasta();
                pasta.prepareFood(ingredient.getPastaIngredient());
                return pasta.deliverFood();
            }
            case PIZZA -> {
                Pizza pizza = new Pizza();
                pizza.prepareFood(ingredient.getPizzaIngredient());
                return pizza.deliverFood();
            }
            default -> {
                throw new Exception("Food Type Not Found");
            }
        }
    }

}
