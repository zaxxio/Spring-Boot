package com.avaand.app.machine.config;

import com.avaand.app.machine.MachineEvent;
import com.avaand.app.machine.MachineState;
import com.avaand.app.machine.service.impl.MachineServiceImpl;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.action.Actions;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Log
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<MachineState, MachineEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<MachineState, MachineEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    private StateMachineListener<MachineState, MachineEvent> listener() {
        return new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<MachineState, MachineEvent> from, State<MachineState, MachineEvent> to) {
                if (from != null){
                    log.info("From : " + from.getId() + ", To : " + to.getId());
                }
            }

            @Override
            public void eventNotAccepted(Message<MachineEvent> event) {
                log.info("Event Failed : " + event.getPayload().name());
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<MachineState, MachineEvent> states) throws Exception {
        states
                .withStates()
                .initial(MachineState.INITIAL)
                .states(EnumSet.allOf(MachineState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MachineState, MachineEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(MachineState.INITIAL).target(MachineState.START_ENGINE).event(MachineEvent.START)
                .action(Actions.errorCallingAction(action(), errorAction()))
                .guard(guard())
                .and()
                .withExternal()
                .source(MachineState.START_ENGINE).target(MachineState.STOP_ENGINE).event(MachineEvent.STOP).and()
                .withExternal()
                .source(MachineState.STOP_ENGINE).target(MachineState.START_ENGINE).event(MachineEvent.START).and();
    }

    @Bean
    public Guard<MachineState, MachineEvent> guard() {
        return ctx -> ctx.getMessage().getHeaders().containsKey(MachineServiceImpl.MACHINE_ID);
    }

    @Bean
    public Action<MachineState, MachineEvent> action() {
        return new Action<MachineState, MachineEvent>() {
            @Override
            public void execute(StateContext<MachineState, MachineEvent> stateContext) {
                //throw new RuntimeException("Error");
            }
        };
    }

    @Bean
    public Action<MachineState, MachineEvent> errorAction() {
        return stateContext -> {
            Exception exception = stateContext.getException();
            log.info(exception.getMessage());
        };
    }

}
