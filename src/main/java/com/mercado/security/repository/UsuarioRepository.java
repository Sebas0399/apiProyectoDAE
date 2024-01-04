package com.mercado.security.repository;

import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import com.mercado.security.repository.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {
    @Query("{cedula:'?0'}")
    Optional<Usuario> findUserByCedula(String username);


}
