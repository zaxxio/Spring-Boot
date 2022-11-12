package com.avaand.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
