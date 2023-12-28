package com.mercado.security.controller;

import com.mercado.security.repository.UsuarioRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public ResponseEntity<List<Empresa>> getEmpresas(@PathVariable String cedula){
        List<Empresa>empresas=usuarioRepository.findByCedula(cedula);
        if(empresas.isEmpty()){
            return ResponseEntity.internalServerError().body(null);
        }
        else{
            return ResponseEntity.ok().body(empresas);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario){
        String key= usuario.getPassword();
        usuario.setPassword(passwordEncoder.encode(key));


        usuarioRepository.insert(usuario);
        return ResponseEntity.ok(usuario);
    }
}
