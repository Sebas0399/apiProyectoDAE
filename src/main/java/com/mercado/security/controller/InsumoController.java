package com.mercado.security.controller;

import com.mercado.security.repository.InsumoRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insumos")
@CrossOrigin
public class InsumoController {
    @Autowired
    InsumoRepository insumoRepository;
    @PostMapping
    public void insertar(@RequestBody Insumo insumo){
        insumoRepository.save(insumo);

    }
}
