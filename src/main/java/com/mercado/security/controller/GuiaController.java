package com.mercado.security.controller;

import com.mercado.security.entity.Guia;
import com.mercado.security.repository.GuiaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/guias")
public class GuiaController {
    @Autowired
    GuiaRepository guiaRepository;
    @GetMapping
    public ResponseEntity<List<Guia>>findAll(){

        List<Guia>guias=this.guiaRepository.findAll();
        if(!guias.isEmpty()){
            return ResponseEntity.ok(guias);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Guia>createOne(@RequestBody @Valid Guia guia){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.guiaRepository.save(guia)
        );
    }
}
