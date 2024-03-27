package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.UsuarioRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Usuario;
import com.mercado.security.service.mail.EmailService;
import com.mercado.security.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private EmailService emailService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EmpresaRepository empresaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{cedula}/empresas")

    public ResponseEntity<List<Empresa>> getEmpresas(@PathVariable String cedula) {
        Usuario usuario = usuarioRepository.findUserByCedula(cedula).get();
        Optional<List<Empresa>> empresas = empresaRepository.findEmpresasByUsuario_Id(usuario.getId());
        if (empresas.isEmpty()) {
            return ResponseEntity.internalServerError().body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(empresas.get());
        }
    }

    @GetMapping("/{cedula}")

    public ResponseEntity<Usuario> getUsuario(@PathVariable String cedula) {
        Usuario usuario = usuarioRepository.findUserByCedula(cedula).get();
        if (usuario == null) {
            return ResponseEntity.internalServerError().body(null);
        } else {
            return ResponseEntity.ok().body(usuario);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario) {
        Optional<Usuario> usuario1 = this.usuarioRepository.findUserByCedula(usuario.getCedula());
        if (usuario1.isEmpty()) {
            String key = usuario.getPassword();
            usuario.setPassword(passwordEncoder.encode(key));
            usuario.setRol(Role.COMERCIANTE);
            usuario.setCreditos(20);
            usuarioRepository.insert(usuario);
            emailService.sendMail(usuario.getCorreo(), "Registro exitoso", "Su cuenta se registro exitosamene");

            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @PutMapping(value = "/{cedula}")
        public ResponseEntity<Usuario> updateUsuario(@PathVariable String cedula,@RequestParam Integer consumo) {
        System.out.println(consumo);
        Optional<Usuario> optionalUsuario = usuarioRepository.findUserByCedula(cedula);

        if (optionalUsuario.isPresent()) {
            Usuario existingUsuario = optionalUsuario.get();

            existingUsuario.setCreditos(existingUsuario.getCreditos() - 1);
            Map<LocalDate,Integer> existingConsumo=existingUsuario.getConsumo();
            if(existingConsumo!=null){
                var consumos=existingConsumo.entrySet();
                AtomicBoolean existe= new AtomicBoolean(false);
                consumos.stream().forEach(localDateIntegerEntry -> {
                    if(localDateIntegerEntry.getKey().isEqual(LocalDate.now())){
                        localDateIntegerEntry.setValue(localDateIntegerEntry.getValue()+consumo);
                        existe.set(true);
                    }
                });
                if(!existe.get()){
                    existingConsumo.put(LocalDate.now(),consumo);
                }
                existingUsuario.setConsumo(existingConsumo);

            }
            else{
                Map<LocalDate,Integer> nuevoConsumo=new HashMap<>();
                nuevoConsumo.put(LocalDate.now(),consumo);
                existingUsuario.setConsumo(nuevoConsumo);
            }

            usuarioRepository.save(existingUsuario);

            return ResponseEntity.ok(existingUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
