package com.mercado.security.service;

import com.mercado.security.service.dto.AuthenticationRequest;
import com.mercado.security.service.dto.AuthenticationResponse;
import com.mercado.security.repository.entity.Usuario;
import com.mercado.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String jwt = jwtService.generateToken(usuario, generateExtraClaims(usuario));

        return new AuthenticationResponse(jwt);
    }

    public String renewToken(String oldToken) {
        String username = jwtService.extractUsername(oldToken);

        if (jwtService.validateToken(oldToken)) {
            Usuario usuario = usuarioRepository.findUserByCedula(username).orElseThrow(() ->
                    new RuntimeException("Usuario no encontrado: " + username)
            );

            String newToken = jwtService.generateToken(usuario, generateExtraClaims(usuario));
            return newToken;
        } else {
            throw new RuntimeException("El token proporcionado no es válido.");
        }
    }

    private Map<String, Object> generateExtraClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", usuario.getCedula());
        extraClaims.put("role", usuario.getRol());
        extraClaims.put("permissions", usuario.getAuthorities());
        return extraClaims;
    }
}