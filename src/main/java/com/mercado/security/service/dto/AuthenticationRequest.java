package com.mercado.security.service.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userName;
    private String password;
}
