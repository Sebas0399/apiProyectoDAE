package com.mercado.security.controller;

import com.mercado.security.repository.entity.TextDataRequest;
import com.mercado.security.repository.entity.TextDataResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DataController {
    @PostMapping("/process-pdf")
    public String processPdf(@RequestBody Map<Integer, List<String>> extractedText) {
        System.out.println(extractedText);
        try {
            // Aquí puedes realizar el procesamiento con el mapa recibido
            for (Map.Entry<Integer, List<String>> entry : extractedText.entrySet()) {
                Integer pageNumber = entry.getKey();
                List<String> texts = entry.getValue();

                // Realiza la lógica de procesamiento aquí
                // Por ejemplo, puedes imprimir el contenido de cada página
                System.out.println("Page " + pageNumber + ": " + texts);
            }

            // Devuelve una respuesta exitosa o el resultado del procesamiento
            return "PDF processing successful!";
        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            return "Error processing PDF";
        }
    }
}
