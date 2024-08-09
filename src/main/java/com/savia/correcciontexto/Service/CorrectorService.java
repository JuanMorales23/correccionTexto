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
        // Paso 1: Corrección de representaciones incorrectas de caracteres especiales
        String correctedText = correctSpecialCharacters(inputText);

        // Paso 2: Corrección de palabras mal formadas usando el diccionario
        correctedText = correctCustomWords(correctedText);

        // Paso 3: Corrección ortográfica utilizando LanguageTool
        correctedText = correctSpelling(correctedText);

        return correctedText;
    }

    private String correctSpecialCharacters(String text) {
        return text.replaceAll("a&os", "años")
                .replaceAll("cent&metros", "centímetros")
                .replaceAll("cuerna", "cuenta") // Corrige otros errores comunes aquí
                .replaceAll("kls", "kilos");
    }

    private String correctCustomWords(String text) {
        String[] lines = text.split("\\r?\\n");
        StringBuilder correctedText = new StringBuilder();
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.matches("[0-9]+") || word.matches("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+")) {
                    correctedText.append(word).append(" ");
                } else {
                    String cleanedWord = word.replaceAll("[^a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]", "");

                    if (diccionario.contains(cleanedWord.toLowerCase()) || cleanedWord.isEmpty()) {
                        correctedText.append(word).append(" ");
                    } else {
                        String closestWord = findClosestWord(cleanedWord);
                        correctedText.append(closestWord != null ? closestWord : word).append(" ");
                    }
                }
            }
            correctedText.append("\n");
        }
        return correctedText.toString().trim();
    }

    private String findClosestWord(String word) {
        if (word.matches("[0-9]+")) {
            return word;
        }

        return diccionario.stream()
                .min((w1, w2) -> Integer.compare(
                        levenshtein.apply(word.toLowerCase(), w1),
                        levenshtein.apply(word.toLowerCase(), w2)))
                .orElse(word);
    }

    private String correctSpelling(String text) throws IOException {
        String[] lines = text.split("\\r?\\n");
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
