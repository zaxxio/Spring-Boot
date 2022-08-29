package com.avaand.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Getter
@Setter
@Entity
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consumerId;
    @Column(unique = true, updatable = false, nullable = false)
    private String consumerName;
    private String password;
    private boolean enabled;
    @Column(columnDefinition = "TEXT")
    private String publicKey;
    @Lob
    //@JsonIgnore
    private String privateKey;

}
