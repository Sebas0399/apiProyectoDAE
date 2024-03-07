package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.InsumoRepository;
import com.mercado.security.repository.UsuarioRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import com.mercado.security.repository.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/empresas")
@CrossOrigin
public class EmpresaController {
    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    InsumoRepository insumoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/{ruc}/insumos")
    public ResponseEntity<List<Insumo>> obtenerInsumosPorEmpresa(@PathVariable String ruc) {
        Optional<Empresa> empresa = empresaRepository.findByRuc(ruc);
        if (empresa.isPresent()) {

            List<Insumo> insumo = insumoRepository.findByEmpresa(empresa.get());
            return ResponseEntity.status(HttpStatus.OK).body(insumo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Empresa> insertar(@RequestBody Empresa empresa) {
        try {
            Usuario usuario = usuarioRepository.findUserByCedula(empresa.getUsuario().getCedula()).get();
            empresa.setUsuario(usuario);

            empresaRepository.save(empresa);

            return ResponseEntity.status(HttpStatus.OK).body(empresa);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            Optional<Empresa> empresa = empresaRepository.findById(id);

            empresaRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la empresa: " + e.getMessage());

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la empresa: " + e.getMessage());

        }


    }

    @PutMapping
    public ResponseEntity<Empresa> actualizar(@RequestBody Empresa empresa) {
        try {
            Optional<Empresa> empresaEncontrada = this.empresaRepository.findById(empresa.getId());
            //actualizar
            BeanUtils.copyProperties(empresa,empresaEncontrada.get(),"id","usuario");
            this.empresaRepository.save(empresaEncontrada.get());
            return ResponseEntity.status(HttpStatus.OK).body(empresa);

        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
}
