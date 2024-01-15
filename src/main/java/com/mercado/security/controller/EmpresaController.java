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

import java.util.List;

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
    public List<Insumo> obtenerInsumosPorEmpresa(@PathVariable String ruc) {
        // Aquí debes cargar la dirección utilizando su ID
        Empresa empresa = empresaRepository.findByRuc(ruc);
        if (empresa != null) {

            List<Insumo> insumo =insumoRepository.findByEmpresa(empresa);
            return insumo;
        } else {
            // Manejar la situación en la que la dirección no existe
            return null;
        }
    }
    @PostMapping
    public ResponseEntity<String> insertar(@RequestBody Empresa empresa){
    try{
        Usuario usuario=usuarioRepository.findUserByCedula(empresa.getUsuario().getCedula()).get();
        empresa.setUsuario(usuario);

        empresaRepository.save(empresa);

        return  ResponseEntity.status(HttpStatus.OK).body("Success");

    }
    catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar la empresa: " + e.getMessage());

    }


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id){
        try{
            Empresa empresa = empresaRepository.findById(id).get();
            empresaRepository.deleteById(id);

            return  ResponseEntity.status(HttpStatus.OK).body("Success");

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la empresa: " + e.getMessage());

        }


    }
}
