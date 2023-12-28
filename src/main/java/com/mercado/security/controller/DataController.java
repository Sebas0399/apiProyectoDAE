package com.mercado.security.controller;

import com.mercado.security.repository.entity.TextDataRequest;
import com.mercado.security.repository.entity.TextDataResponse;
import com.mercado.security.service.DataService;
import com.mercado.security.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DataController {
    @Autowired
    DataService dataService;
    @PostMapping("/process-pdf")
    public ResponseEntity<List<List<String>>> processPdf(@RequestBody Map<Integer, List<String>> extractedText) {
        var respuesta=dataService.procesarData(extractedText);
        if(respuesta.isEmpty()){
            return ResponseEntity.internalServerError().body(null);
        }
        else{
            return ResponseEntity.ok().body(respuesta);
        }
    }
}
