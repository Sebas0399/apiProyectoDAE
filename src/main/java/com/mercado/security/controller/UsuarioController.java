package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.UsuarioRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Usuario;
import com.mercado.security.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EmpresaRepository empresaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/{cedula}/empresas")

    public ResponseEntity<List<Empresa>> getEmpresas(@PathVariable String cedula){
        Usuario usuario=usuarioRepository.findUserByCedula(cedula).get();
        Optional<List<Empresa>>empresas=empresaRepository.findEmpresasByUsuario_Id(usuario.getId());
        if(empresas.isEmpty()){
            return ResponseEntity.internalServerError().body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(empresas.get());
        }
    }

    @GetMapping("/{cedula}")

    public ResponseEntity<Usuario> getUsuario(@PathVariable String cedula){
        Usuario usuario=usuarioRepository.findUserByCedula(cedula).get();
        if(usuario==null){
            return ResponseEntity.internalServerError().body(null);
        }
        else{
            return ResponseEntity.ok().body(usuario);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario){
        Optional<Usuario> usuario1=this.usuarioRepository.findUserByCedula(usuario.getCedula());
        if(usuario1.isEmpty()){
            String key= usuario.getPassword();
            usuario.setPassword(passwordEncoder.encode(key));
            usuario.setRol(Role.COMERCIANTE);
            usuario.setCreditos(20);

            usuarioRepository.insert(usuario);
            return ResponseEntity.ok(usuario);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }
    @PutMapping("/{cedula}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable String cedula) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findUserByCedula(cedula);

        if (optionalUsuario.isPresent()) {
            Usuario existingUsuario = optionalUsuario.get();

            existingUsuario.setCreditos(existingUsuario.getCreditos()-1);
            usuarioRepository.save(existingUsuario);

            return ResponseEntity.ok(existingUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
