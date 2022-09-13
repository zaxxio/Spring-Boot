package com.avaand.app.domain;

import com.avaand.app.machine.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "network_card")
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long networkCardId;
    private String name;
    @Enumerated(EnumType.STRING)
    private State state;

}
