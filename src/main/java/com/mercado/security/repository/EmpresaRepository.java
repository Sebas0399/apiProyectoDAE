package com.mercado.security.repository;

import com.mercado.security.repository.entity.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends MongoRepository<Empresa,String> {
    public Optional<Empresa> findByRuc(String ruc);
    public Optional<Empresa> findById(String id);

    Optional<List<Empresa>> findEmpresasByUsuario_Id(String cedula);
}
