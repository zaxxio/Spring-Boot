package com.avaand.app.service;

import com.avaand.app.domain.Network;
import com.avaand.app.machine.Event;
import com.avaand.app.machine.State;
import org.springframework.statemachine.StateMachine;

public interface NetworkService {
    Network newConnection(Network network);
    StateMachine<State, Event> offline(Long networkId);
    StateMachine<State, Event> online(Long networkId);
    StateMachine<State, Event> maintenance(Long networkId);
    StateMachine<State, Event> build(Long networkId);
    void sendEvent(Long networkId, StateMachine<State, Event> stateMachine, Event event);
}
