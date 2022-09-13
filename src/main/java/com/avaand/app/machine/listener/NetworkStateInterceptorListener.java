package com.avaand.app.machine.listener;

import com.avaand.app.domain.Network;
import com.avaand.app.machine.Event;
import com.avaand.app.machine.State;
import com.avaand.app.repository.NetworkRepository;
import com.avaand.app.service.impl.NetworkServiceImpl;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NetworkStateInterceptorListener extends StateMachineInterceptorAdapter<State, Event> {

    private final NetworkRepository networkRepository;

    public NetworkStateInterceptorListener(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    @Override
    public void preStateChange(org.springframework.statemachine.state.State<State, Event> state,
                               Message<Event> message,
                               Transition<State, Event> transition,
                               StateMachine<State, Event> stateMachine,
                               StateMachine<State, Event> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable((Long.class.cast(msg.getHeaders().getOrDefault(NetworkServiceImpl.NETWORK_PARAM, -1L))))
                    .ifPresent(networkId -> {
                        Optional<Network> network = networkRepository.findById(networkId);
                        network.get().setState(state.getId());
                        networkRepository.save(network.get());
                    });
        });
    }
}
