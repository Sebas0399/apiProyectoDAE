package com.mercado.security.repository;

import com.mercado.security.repository.entity.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpresaRepository extends MongoRepository<Empresa,String> {
    public Empresa findByRuc(String ruc);
}
