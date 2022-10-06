package com.avaand.app.machine.service;

import com.avaand.app.machine.MachineEvent;
import com.avaand.app.machine.MachineState;
import com.avaand.app.machine.domain.Machine;
import org.springframework.statemachine.StateMachine;

public interface MachineService {
    StateMachine<MachineState, MachineEvent> start(Machine machine);
    StateMachine<MachineState, MachineEvent> stop(Machine machine);
    StateMachine<MachineState, MachineEvent> build(Long machineId);
}
