package com.mercado.security.service;

import com.mercado.security.entity.Guia;
import com.mercado.security.repository.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuiaService {
    @Autowired
    private GuiaRepository repository;

    public Guia create(Guia guia){

        repository.save(guia);
        return guia;
    }
}
