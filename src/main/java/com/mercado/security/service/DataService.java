package com.mercado.security.service;

import com.mercado.security.repository.EmpresaRepository;
import com.mercado.security.repository.InsumoRepository;
import com.mercado.security.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    @Autowired
    EmpresaRepository empresaRepository;
    @Autowired
    InsumoRepository insumoRepository;
    public List<List<String>> procesarData(Map<Integer, List<String>> textoExtraido){
        List<List<String>>datosSalida=new ArrayList<>();

        for (Map.Entry<Integer, List<String>> entry : textoExtraido.entrySet()) {
            List<String> texts = entry.getValue();
            var index=0;
            for(int i=0;i<texts.size();i++){
                if(texts.get(i).contains("Cantidad de contenedores")){

                    index=i;
                }
            }
            if(index!=0){
                System.out.println(texts.get(index+1));
                var cantida=new BigDecimal(texts.get(index+1));

                var merma=cantida.multiply(Constante.merma).round(new MathContext(2));
                datosSalida.add(List.of(cantida.toString(),merma.toString()));

            }

        }
        return datosSalida;


    }
}
