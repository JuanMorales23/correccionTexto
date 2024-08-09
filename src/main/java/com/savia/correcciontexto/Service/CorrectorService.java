package com.savia.correcciontexto.Service;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Spanish;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class CorrectorService {
    private final JLanguageTool langTool;
    private final LevenshteinDistance levenshtein;

    // Diccionario personalizado de palabras mal formadas
    private final Map<String, String> customCorrections = Map.of(
            "pacnte", "paciente",
            "kls", "kilos",
            "cuen&", "cuenta",
            "cent&metros", "centímetros",
            "a&os", "años"
    );

    public CorrectorService() {
        this.langTool = new JLanguageTool(new Spanish());
        this.levenshtein = new LevenshteinDistance();
    }

    public String correctText(String inputText) throws Exception {
        // Paso 1: Corrección de palabras mal formadas
        String correctedText = correctCustomWords(inputText);

        // Paso 2: Corrección ortográfica utilizando LanguageTool
        correctedText = correctSpelling(correctedText);

        return correctedText;
    }

    private String correctCustomWords(String text) {
        for (Map.Entry<String, String> entry : customCorrections.entrySet()) {
            text = text.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue());
        }
        return text;
    }

    private String correctSpelling(String text) throws Exception {
        List<RuleMatch> matches = langTool.check(text);
        StringBuilder correctedText = new StringBuilder(text);
        int offset = 0;

        for (RuleMatch match : matches) {
            correctedText.replace(
                    match.getFromPos() + offset,
                    match.getToPos() + offset,
                    match.getSuggestedReplacements().get(0)
            );
            offset += match.getSuggestedReplacements().get(0).length() - (match.getToPos() - match.getFromPos());
        }

        return correctedText.toString();
    }
}
