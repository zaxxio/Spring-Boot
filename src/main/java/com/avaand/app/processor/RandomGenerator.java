package com.avaand.app.processor;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomGenerator {

    private int min;
    private int max;
    private final Random random = new Random();

    public int generate(int min, int max){
        return random.nextInt(max - min ) + min;
    }

}
