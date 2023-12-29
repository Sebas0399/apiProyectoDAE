package com.mercado.security.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Empresa {
    @Id
    private String id;
    private String nombre;
    private String ruc;
        private String direccion;
    @DBRef
    private Usuario usuario;
}
