package com.mercado.security.controller;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.InsumoRepository;
import com.mercado.security.repository.entity.Empresa;
import com.mercado.security.repository.entity.Insumo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/insumos")
@CrossOrigin
public class InsumoController {
    @Autowired
    InsumoRepository insumoRepository;
    @Autowired
    EmpresaRepository empresaRepository;

    @PostMapping
    public ResponseEntity<Insumo> insertar(@RequestBody Insumo insumo) {
        try {
            Optional<Empresa> empresaOptional = empresaRepository.findByRuc(insumo.getEmpresa().getRuc());

            if (empresaOptional.isPresent()) {
                Empresa empresa = empresaOptional.get();
                insumo.setEmpresa(empresa);
                insumoRepository.save(insumo);

                // Utiliza ResponseEntity.ok() para respuestas exitosas
                return ResponseEntity.ok(insumo);
            } else {
                // Manejar el caso donde la empresa no existe
                // Devuelve un ResponseEntity con estado BAD_REQUEST y un mensaje indicando que no se encontró la empresa
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            // Manejar otros casos de excepciones con un ResponseEntity con estado INTERNAL_SERVER_ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            insumoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminado correctamente");

        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la empresa: " + e.getMessage());

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la empresa: " + e.getMessage());

        }


    }
    @PutMapping
    public ResponseEntity<Insumo> actualizar(@RequestBody Insumo insumo) {
        try {
            Optional<Insumo> insumoEncontrado = this.insumoRepository.findById(insumo.getId());
            //actualizar
            BeanUtils.copyProperties(insumo,insumoEncontrado.get(),"id","empresa");
            this.insumoRepository.save(insumoEncontrado.get());
            return ResponseEntity.status(HttpStatus.OK).body(insumo);

        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
}
