package com.savia.correcciontexto.Service;
import com.savia.correcciontexto.Util.Diccionario;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jetbrains.annotations.NotNull;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Spanish;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class CorrectorService {
    // Diccionario personalizado de palabras mal formadas
    private final Set<String> diccionario;
    private final JLanguageTool langTool;
    private final LevenshteinDistance levenshtein;
    private final String dictionaryPath = "src/main/java/com/savia/correcciontexto/Util/0_palabras_todas.txt";

    public CorrectorService() throws IOException {
        Diccionario cargadorDiccionario = new Diccionario();
        this.diccionario = cargadorDiccionario.cargarDiccionario(dictionaryPath);
        this.langTool = new JLanguageTool(new Spanish());
        this.levenshtein = new LevenshteinDistance();
    }

    public String correctText(@NotNull String inputText) throws Exception {
        // Paso 1: Corrección de palabras mal formadas usando el diccionario
        String correctedText = correctCustomWords(inputText);

        // Paso 2: Corrección ortográfica utilizando LanguageTool
        correctedText = correctSpelling(correctedText);

        return correctedText;
    }

    private String correctCustomWords(String text) {
        String[] lines = text.split("\\r?\\n"); // Se divide por lineas
        StringBuilder correctedText = new StringBuilder();
        for (String line : lines) {
            String[] words = line.split("\\s+"); // Se divide en palabras
            for (String word : words) {
                // Verifica si el texto es un salto de línea o contiene solo números
                if (word.matches("[0-9]+") || word.matches("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+")) {
                    correctedText.append(word).append(" ");
                } else {
                    // Elimina caracteres especiales y verificar en el diccionario
                    String cleanedWord = word.replaceAll("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]", "");

                    if (diccionario.contains(cleanedWord.toLowerCase()) || cleanedWord.equals("")) {
                        correctedText.append(word).append(" ");
                    } else {
                        String closestWord = findClosestWord(cleanedWord);
                        correctedText.append(closestWord != null ? closestWord : word).append(" ");
                    }
                }
            }
            correctedText.append("\n"); // Mantener el salto de línea
        }
        return correctedText.toString().trim();
    }

    private String findClosestWord(String word) {
        if (word.matches("[0-9]+")) {
            // Si la palabra es un número, no se hacen correcciones
            return word;
        }

        return diccionario.stream()
                .min((w1, w2) -> Integer.compare(
                        levenshtein.apply(word.toLowerCase(), w1),
                        levenshtein.apply(word.toLowerCase(), w2)))
                .orElse(word);
    }

    private String correctSpelling(String text) throws Exception {
        String[] lines = text.split("\\r?\\n");  // Dividir en líneas para mantener los saltos de línea
        StringBuilder correctedText = new StringBuilder();

        for (String line : lines) {
            List<RuleMatch> matches = langTool.check(line);
            StringBuilder correctedLine = new StringBuilder(line);
            int offset = 0;

            for (RuleMatch match : matches) {
                if (!match.getSuggestedReplacements().isEmpty()) {
                    String replacement = match.getSuggestedReplacements().get(0);
                    correctedLine.replace(
                            match.getFromPos() + offset,
                            match.getToPos() + offset,
                            replacement
                    );
                    offset += replacement.length() - (match.getToPos() - match.getFromPos());
                }
            }

            correctedText.append(correctedLine).append("\n");
        }
        return correctedText.toString().trim();
    }
}
