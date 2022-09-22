package com.avaand.app.signaling;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Signal {

    private String userId;
    private String type;
    private String content;
    private String toId;

}
