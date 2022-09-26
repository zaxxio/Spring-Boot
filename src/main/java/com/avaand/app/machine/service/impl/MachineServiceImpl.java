package com.avaand.app.machine.service.impl;

import com.avaand.app.machine.MachineEvent;
import com.avaand.app.machine.MachineState;
import com.avaand.app.machine.domain.Machine;
import com.avaand.app.machine.interceptor.MachineStateInterceptor;
import com.avaand.app.machine.service.MachineService;
import com.avaand.app.repository.MachineRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class MachineServiceImpl implements MachineService {

    public static final String MACHINE_ID = "MACHINE_ID";
    private final MachineRepository machineRepository;
    private final StateMachineFactory<MachineState, MachineEvent> factory;
    private final MachineStateInterceptor machineStateInterceptor;

    public MachineServiceImpl(MachineRepository machineRepository,
                              StateMachineFactory<MachineState, MachineEvent> factory,
                              MachineStateInterceptor machineStateInterceptor) {
        this.machineRepository = machineRepository;
        this.factory = factory;
        this.machineStateInterceptor = machineStateInterceptor;
    }

    @Override
    public StateMachine<MachineState, MachineEvent> start(Machine machine) {
        if (machine.getMachineId() == null){
            machine.setMachineState(MachineState.INITIAL);
            machine = this.machineRepository.save(machine);
            StateMachine<MachineState, MachineEvent> sm = build(machine.getMachineId());
            sendEvent(machine.getMachineId(), sm, MachineEvent.START);
            return sm;
        }else {
            StateMachine<MachineState, MachineEvent> sm = build(machine.getMachineId());
            sendEvent(machine.getMachineId(), sm, MachineEvent.START);
            return sm;
        }
    }

    private void sendEvent(Long machineId, StateMachine<MachineState, MachineEvent> sm, MachineEvent event) {
        Message<MachineEvent> message = MessageBuilder.withPayload(event)
                .setHeader(MACHINE_ID, machineId)
                .build();
        sm.sendEvent(Mono.just(message)).subscribe();
    }

    @Override
    public StateMachine<MachineState, MachineEvent> stop(Machine machine) {
        StateMachine<MachineState, MachineEvent> sm = build(machine.getMachineId());
        sendEvent(machine.getMachineId(), sm, MachineEvent.STOP);
        return sm;
    }

    @Override
    public StateMachine<MachineState, MachineEvent> build(Long machineId) {
        Optional<Machine> optionalMachine = this.machineRepository.findById(machineId);
        if (optionalMachine.isPresent()){
            StateMachine<MachineState, MachineEvent> sm = factory.getStateMachine(Long.toString(machineId));
            sm.stopReactively().subscribe();
            sm.getStateMachineAccessor()
                    .doWithAllRegions(sma -> {
                        sma.addStateMachineInterceptor(machineStateInterceptor);
                        sma.resetStateMachineReactively(new DefaultStateMachineContext<>(optionalMachine.get().getMachineState(), null, null, null)).subscribe();
                    });
            sm.startReactively().subscribe();
            return sm;
        }
        throw new RuntimeException(String.format("MachineId %f not found.", machineId));
    }


}
