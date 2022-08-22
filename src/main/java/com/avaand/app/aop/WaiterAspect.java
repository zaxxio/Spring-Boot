package com.avaand.app.aop;

import com.avaand.app.service.FoodType;
import com.avaand.app.service.Waiter;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log
@Aspect
@Component
public class WaiterAspect {

    @Before("execution(* com.avaand.app.service.Waiter.deliverFood(..)) && args(foodType)")
    public void beforeDeliverFood(JoinPoint point, FoodType foodType){
        log.info("==================================================");
        log.info("Before Method : " + point.getSignature());
        log.info("Food Type is : " + foodType);
        log.info("Before the Execution of Waiter deliverFood()");
    }

    @After("execution(* com.avaand.app.service.Waiter.deliverFood(..)) && args(foodType)")
    public void afterDeliverFood(JoinPoint point, FoodType foodType){
        log.info("==================================================");
        log.info("After Method : " + point.getSignature());
        log.info("Food Type is : " + foodType);
        log.info("After the Execution of Waiter deliverFood()");
    }


    @Around(value = "execution(* com.avaand.app.service.Waiter.deliverFood(..)) && args(foodType)")
    public Object aroundDeliverFood(ProceedingJoinPoint point, FoodType foodType){
        try{
            Object[] args = point.getArgs();
            args[0] = FoodType.PASTA;
            return point.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @AfterReturning("execution(* com.avaand.app.service.Waiter.deliverFood(..)) && args(foodType)")
    public void afterReturningDeliverFood(JoinPoint point, FoodType foodType){
        log.info("==================================================");
        log.info("After Returning : " + point.getSignature());
        log.info("Food Type is : " + ((Waiter) point.getTarget()).deliverFood(foodType));
    }

}
