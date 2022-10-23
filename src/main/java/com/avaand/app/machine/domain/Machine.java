package com.avaand.app.machine.domain;

import com.avaand.app.machine.MachineState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity(name = "machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long machineId;
    private String machineName;
    @Enumerated(EnumType.STRING)
    private MachineState machineState;
}
