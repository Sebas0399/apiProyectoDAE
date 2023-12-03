package com.mercado.security.controller;

import com.mercado.security.dto.AuthenticationRequest;
import com.mercado.security.dto.AuthenticationResponse;
import com.mercado.security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired

    private AuthenticationService authenticationService;
    @PostMapping("/authenticate")
    public ResponseEntity <AuthenticationResponse>login(@RequestBody @Valid AuthenticationRequest authRequest){
        System.out.println(authRequest);
        AuthenticationResponse jwtDto=authenticationService.login(authRequest);
        return ResponseEntity.ok(jwtDto);
    }
    @GetMapping("/public-access")
    public ResponseEntity<String > publicAccessEndPoint(){
        return ResponseEntity.ok("EndPointPublico");
    }
}
