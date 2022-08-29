package com.avaand.app.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Payload {

    private String cardHolderName;
    private String cardNumber;
    private String cardExpiration;
    private String cardCVV;

}
