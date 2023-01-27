package com.avaand.app.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class AppRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        final List<Person> people = List.of(
                new Person("Partha", Gender.MALE),
                new Person("John", Gender.MALE),
                new Person("Miya", Gender.FEMALE)
        );

        Predicate<Person> predicate = person -> Gender.FEMALE.equals(person.gender);

        people.stream()
                .filter(predicate)
                .toList()
                .forEach(System.out::println);

        Integer apply = incrementOneByFunction.apply(100);
        Function<Integer, Integer> integerIntegerFunction = incrementOneByFunction.andThen(incrementOneByFunction);
        Integer apply1 = integerIntegerFunction.apply(100);
        System.out.println(apply1);
        System.out.println(apply);

        System.out.println(incrementByOne(1000));

        System.out.println(multiply.apply(10,20));
    }

    public BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;

    public Function<Integer, Integer> incrementOneByFunction = number -> number + 1;

    public int incrementByOne(int x){
        return x + 1;
    }

    static class Person{
        private String name;
        private Gender gender;

        public Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", gender=" + gender +
                    '}';
        }
    }

    public enum Gender{
        MALE, FEMALE
    }
}
