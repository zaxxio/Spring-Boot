package com.avaand.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Pet {

    @Id
    //@GenericGenerator(name = "custom_sequence", strategy = "com.avaand.app.domain.generator.PetIdGenerator")
    //@GeneratedValue(generator = "custom_sequence")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer petId;
    private String petName;
    private String username;
    private String password;
    private Date date = new Date();

}
