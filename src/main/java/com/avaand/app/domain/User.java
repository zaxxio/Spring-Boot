package com.avaand.app.domain;


import com.avaand.app.validation.IpAddress;
import com.avaand.app.validation.MacAddress;
import lombok.*;
import lombok.extern.java.Log;

import javax.validation.constraints.*;

@Log
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @NotNull(message = "Please enter userId.")
    private Double userId;
    @Email(message = "Please enter email format.")
    @NotEmpty(message = "Please enter username.")
    private String username;
    @NotEmpty(message = "Please enter password.")
    @Min(value = 4, message = "Please password should be more than 4 digit or character.")
    private String password;
    @IpAddress(message = "Provide valid Ip address.")
    private String ipAddress;
    @MacAddress(message = "Provide valid Mac address.")
    private String macAddress;
}
