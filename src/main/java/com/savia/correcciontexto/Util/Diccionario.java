package com.savia.correcciontexto.Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
public class Diccionario {
    public Set<String> cargarDiccionario(String filePath) throws IOException {
        Set<String> diccionario = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                diccionario.add(line.toLowerCase());
            }
        }
        return diccionario;
    }
}
