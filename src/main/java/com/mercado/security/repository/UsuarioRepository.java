package com.mercado.security.repository;

import com.mercado.security.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {
    @Query("{cedula:'?0'}")
    Optional<Usuario> findUserByCedula(String username);
}
