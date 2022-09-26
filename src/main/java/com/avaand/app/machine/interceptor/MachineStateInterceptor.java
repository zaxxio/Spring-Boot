package com.avaand.app.machine.interceptor;

import com.avaand.app.machine.MachineEvent;
import com.avaand.app.machine.MachineState;
import com.avaand.app.machine.domain.Machine;
import com.avaand.app.machine.service.impl.MachineServiceImpl;
import com.avaand.app.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MachineStateInterceptor extends StateMachineInterceptorAdapter<MachineState, MachineEvent> {

    private final MachineRepository machineRepository;

    public MachineStateInterceptor(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public void preStateChange(State<MachineState, MachineEvent> state, Message<MachineEvent> message, Transition<MachineState, MachineEvent> transition, StateMachine<MachineState, MachineEvent> stateMachine, StateMachine<MachineState, MachineEvent> rootStateMachine) {
        Long machineId = (Long) message.getHeaders().getOrDefault(MachineServiceImpl.MACHINE_ID, -1);
        Optional<Machine> machine = machineRepository.findById(machineId);
        if (machine.isPresent()){
            machine.get().setMachineState(state.getId());
            machineRepository.save(machine.get());
        }
    }
}
