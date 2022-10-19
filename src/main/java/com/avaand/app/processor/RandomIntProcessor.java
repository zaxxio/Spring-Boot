package com.avaand.app.processor;

import com.avaand.app.processor.tag.RandomInt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Component
public class RandomIntProcessor implements BeanPostProcessor {

    private final RandomGenerator randomGenerator;

    @Lazy
    private RandomIntProcessor(RandomGenerator randomGenerator){
        this.randomGenerator = randomGenerator;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            RandomInt randomInt = field.getAnnotation(RandomInt.class);
            if (randomInt != null){
                int max = randomInt.max();
                int min = randomInt.min();
                int value = randomGenerator.generate(min, max);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, value);
            }
        }
        return bean;
    }
}
