package com.mercado.security.service;

import com.mercado.security.dto.AuthenticationRequest;
import com.mercado.security.dto.AuthenticationResponse;
import com.mercado.security.entity.Usuario;
import com.mercado.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserName(),authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);
        Usuario usuario=usuarioRepository.findUserByCedula(authenticationRequest.getUserName()).get();
        String jwt=jwtService.generateToken(usuario,generateExtraClaims(usuario));
      
        return new AuthenticationResponse(jwt);

    }

    private Map<String,Object> generateExtraClaims(Usuario usuario) {
        Map<String,Object>extraClaims=new HashMap<>();
        extraClaims.put("name",usuario.getCedula());
        extraClaims.put("role",usuario.getRol());
        extraClaims.put("permissions",usuario.getAuthorities());
        return extraClaims;
    }
}
