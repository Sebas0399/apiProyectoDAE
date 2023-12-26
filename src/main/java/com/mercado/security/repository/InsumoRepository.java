package com.mercado.security.repository;

import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InsumoRepository extends MongoRepository<Insumo,String> {
    List<Insumo> findByEmpresa(Empresa empresa);
    List<Insumo> findByEmpresa_Id(String empresaId);}
