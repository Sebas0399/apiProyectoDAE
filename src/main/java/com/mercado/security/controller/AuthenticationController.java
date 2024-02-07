package com.mercado.security.controller;

import com.mercado.security.service.dto.AuthenticationRequest;
import com.mercado.security.service.dto.AuthenticationResponse;
import com.mercado.security.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired

    private AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity <AuthenticationResponse>login(@RequestBody @Valid AuthenticationRequest authRequest){
        System.out.println(authRequest);
        AuthenticationResponse jwtDto=authenticationService.login(authRequest);
        return ResponseEntity.ok(jwtDto);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String oldToken) {
        System.out.println(oldToken);
        String newToken = authenticationService.renewToken(oldToken);
        return ResponseEntity.ok(new AuthenticationResponse(newToken));
    }
    @GetMapping("/public-access")
    public ResponseEntity<String > publicAccessEndPoint(){
        return ResponseEntity.ok("EndPointPublico");
    }
}
