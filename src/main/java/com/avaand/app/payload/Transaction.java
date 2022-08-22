package com.avaand.app.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Double transactionId;

    private String rsaPublicKey;

    @JsonIgnore
    private String rsaPrivateKey;

    private Payload payload;

}
