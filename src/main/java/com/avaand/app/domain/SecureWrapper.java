package com.avaand.app.domain;

import lombok.Data;

@Data
public class SecureWrapper {
    private Long consumerId;
    private String payload;
}
