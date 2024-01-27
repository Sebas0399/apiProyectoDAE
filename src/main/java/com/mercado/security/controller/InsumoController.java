package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.InsumoRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insumos")
@CrossOrigin
public class InsumoController {
    @Autowired
    InsumoRepository insumoRepository;
    @Autowired
    EmpresaRepository empresaRepository;

    @PostMapping
    public ResponseEntity<Insumo>insertar(@RequestBody Insumo insumo){
        Empresa empresa=empresaRepository.findByRuc(insumo.getEmpresa().getRuc());
        insumo.setEmpresa(empresa);
        try{
            insumoRepository.save(insumo);
            return ResponseEntity.status(HttpStatus.CREATED).body(insumo);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
