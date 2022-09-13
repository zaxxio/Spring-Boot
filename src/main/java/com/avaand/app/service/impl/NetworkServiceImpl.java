package com.avaand.app.service.impl;

import com.avaand.app.domain.Network;
import com.avaand.app.machine.Event;
import com.avaand.app.machine.State;
import com.avaand.app.machine.listener.NetworkStateInterceptorListener;
import com.avaand.app.repository.NetworkRepository;
import com.avaand.app.service.NetworkService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NetworkServiceImpl implements NetworkService {

    public static final String NETWORK_PARAM = "NETWORK_CARD_PARAM";
    private final NetworkRepository networkRepository;
    private final StateMachineFactory<State,Event> factory;

    private final NetworkStateInterceptorListener stateMachineInterceptor;

    public NetworkServiceImpl(NetworkRepository networkRepository,
                              StateMachineFactory<State, Event> factory,
                              NetworkStateInterceptorListener stateMachineInterceptor) {
        this.networkRepository = networkRepository;
        this.factory = factory;
        this.stateMachineInterceptor = stateMachineInterceptor;
    }

    @Override
    public Network newConnection(Network network) {
        network.setState(State.ONLINE);
        network = networkRepository.save(network);
        sendEvent(network.getNetworkCardId(), build(network.getNetworkCardId()), Event.ONLINE);
        return network;
    }

    @Override
    public StateMachine<State, Event> offline(Long networkId) {
        StateMachine<State, Event> stateMachine = build(networkId);
        sendEvent(networkId, stateMachine, Event.OFFLINE);
        return stateMachine;
    }

    @Override
    public StateMachine<State, Event> online(Long networkId) {
        StateMachine<State, Event> stateMachine = build(networkId);
        sendEvent(networkId, stateMachine, Event.ONLINE);
        return stateMachine;
    }

    @Override
    public StateMachine<State, Event> maintenance(Long networkId) {
        StateMachine<State, Event> stateMachine = build(networkId);
        sendEvent(networkId, stateMachine, Event.MAINTENANCE);
        return stateMachine;
    }

    @Override
    public StateMachine<State, Event> build(Long networkId) {
        Optional<Network> optional = networkRepository.findById(networkId);
        if (optional.isEmpty()) {
            throw new RuntimeException(String.format("NetworkId %s not found.", networkId));
        }else {
            Network network = optional.get();
            StateMachine<State, Event> stateMachine = factory.getStateMachine(Long.toString(networkId));
            stateMachine.stop();
            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(sma -> {
                        sma.addStateMachineInterceptor(stateMachineInterceptor);
                        sma.resetStateMachine(
                                new DefaultStateMachineContext<>(network.getState(), null, null, null));

                    });
            stateMachine.start();
            return stateMachine;
        }
    }

    @Override
    public void sendEvent(Long networkId, StateMachine<State, Event> stateMachine, Event event) {
        Message<Event> message = MessageBuilder.withPayload(event)
                .setHeader(NETWORK_PARAM, networkId)
                .build();
        stateMachine.sendEvent(message);
    }

}
