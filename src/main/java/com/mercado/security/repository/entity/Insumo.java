package com.mercado.security.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "insumo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insumo {
    @Id
    private String id;
    private String codigo;
    private String subpartida;
    private String descripcion;
    @DBRef
    private Empresa empresa;


}
