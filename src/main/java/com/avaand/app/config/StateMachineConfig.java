package com.avaand.app.config;

import com.avaand.app.machine.Event;
import com.avaand.app.machine.State;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;

@Log
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states.withStates()
                .states(EnumSet.allOf(State.class))
                .initial(State.ONLINE)
                .state(State.ONLINE)
                .state(State.OFFLINE)
                .state(State.MAINTENANCE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
                .withExternal().source(State.ONLINE).target(State.OFFLINE).event(Event.OFFLINE)//.guard(failGuard())
                .and()
                .withExternal().source(State.OFFLINE).target(State.ONLINE).event(Event.ONLINE)
                .and()
                .withExternal().source(State.OFFLINE).target(State.MAINTENANCE).event(Event.MAINTENANCE);
    }

    private Guard<State, Event> failGuard() {
        return ctx -> ctx.getMessageHeaders().containsKey("networkId");
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        StateMachineListenerAdapter<State, Event> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(org.springframework.statemachine.state.State<State, Event> from, org.springframework.statemachine.state.State<State, Event> to) {
                log.info(String.format("State Changed from : %s, to: %s", from, to));
            }
        };
        config.withConfiguration()
                .listener(adapter)
                .autoStartup(true);
    }

    /*@Bean
    public Action<State,Event> entryAction(){
        return ctx -> log.info(String.format("Entry action is %s to get from %s to %s", ctx.getEvent(), ctx.getSource(), ctx.getTarget()));
    }

    @Bean
    public Action<State,Event> exitAction(){
        return ctx -> log.info(String.format("Exit action is %s to get from %s to %s", ctx.getEvent(), ctx.getSource(), ctx.getTarget()));
    }*/

}
