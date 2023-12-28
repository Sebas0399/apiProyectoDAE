package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.InsumoRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertar(@RequestBody Empresa empresa){
        empresaRepository.save(empresa);

    }
}
