package com.avaand.app.config;

import com.avaand.app.machine.Event;
import com.avaand.app.machine.State;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

@Log
@Configuration
@EnableStateMachine(name = "stateMachine")
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states.withStates()
                .states(EnumSet.allOf(State.class))
                .initial(State.AVAILABLE)
                .state(State.AVAILABLE, entryAction(), exitAction())
                .state(State.BORROWED, entryAction(), exitAction())
                .state(State.MAINTENANCE, entryAction(), exitAction());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
                .withExternal().source(State.AVAILABLE).target(State.BORROWED).event(Event.BORROW)
                .and()
                .withExternal().source(State.BORROWED).target(State.AVAILABLE).event(Event.RETURN)
                .and()
                .withExternal().source(State.AVAILABLE).target(State.MAINTENANCE).event(Event.START_MAINTENANCE)
                .and()
                .withExternal().source(State.MAINTENANCE).target(State.AVAILABLE).event(Event.STOP_MAINTENANCE);
    }

    private Guard<State, Event> failGuard() {
        return ctx -> {
            throw new RuntimeException("fail in guard");
        };
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Bean
    public Action<State,Event> entryAction(){
        return ctx -> log.info(String.format("Entry action is %s to get from %s to %s", ctx.getEvent(), ctx.getSource(), ctx.getTarget()));
    }

    @Bean
    public Action<State,Event> exitAction(){
        return ctx -> log.info(String.format("Exit action is %s to get from %s to %s", ctx.getEvent(), ctx.getSource(), ctx.getTarget()));
    }

}
