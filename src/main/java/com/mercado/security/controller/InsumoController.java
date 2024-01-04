package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
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
    @Autowired
    EmpresaRepository empresaRepository;
    @PostMapping
    public void insertar(@RequestBody Insumo insumo){
        Empresa empresa=empresaRepository.findByRuc(insumo.getEmpresa().getRuc());
        insumo.setEmpresa(empresa);
        insumoRepository.save(insumo);

    }
}
