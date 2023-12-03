package com.mercado.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "guia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guia {
    @Id
    private String id;
    private String descripcion;

}
