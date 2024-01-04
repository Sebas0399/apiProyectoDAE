package com.mercado.security.repository;

import com.mercado.security.repository.entity.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmpresaRepository extends MongoRepository<Empresa,String> {
    public Empresa findByRuc(String ruc);
    List<Empresa> findEmpresasByUsuario_Id(String cedula);
}
