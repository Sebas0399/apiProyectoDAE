package com.mercado.security.repository;

import com.mercado.security.entity.Guia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GuiaRepository extends MongoRepository<Guia,String> {

}
