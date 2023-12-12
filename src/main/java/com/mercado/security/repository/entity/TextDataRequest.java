package com.mercado.security.repository.entity;

import lombok.Data;

import java.util.List;

@Data
public class TextDataRequest {
    private List<List<String>> textData;

}
