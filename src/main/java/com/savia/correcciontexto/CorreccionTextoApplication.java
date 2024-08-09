package com.savia.correcciontexto;

import com.savia.correcciontexto.Service.CorrectorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorreccionTextoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorreccionTextoApplication.class, args);
        CorrectorService correctorService = new CorrectorService();

        String inputText = "El pacnte cuen& con 100kls de peso y 168 cent&metros de altura con 18 a&os";
        try {
            String correctedText = correctorService.correctText(inputText);
            System.out.println("Texto original: " + inputText);
            System.out.println("Texto corregido: " + correctedText);
        } catch (Exception e) {
            System.out.println("Error al corregir el texto: " + e.getMessage());
        }
    }

}
