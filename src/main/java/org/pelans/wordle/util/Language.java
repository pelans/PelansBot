package org.pelans.wordle.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Language {

    public String Lan;

    private static List<String> languages;
    private static Map<String, List<String>> phrases;
    public static void init() {
        try (
                Stream<String> stream = Files.lines(Paths.get("src/main/resources/language.csv"), StandardCharsets.UTF_8)
        ) {
            List<String> lines = stream.toList();
            List<String> config = new ArrayList<>(List.of(lines.get(0).split(";")));
            phrases = new HashMap<>();

            config.remove(0);
            languages = config;

            for (int i=1; i<lines.size(); i++) {
                List<String> translation = new ArrayList<>(List.of(lines.get(i).split(";")));
                String key = translation.get(0);
                translation.remove(0);
                phrases.put(key,translation);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Language(String lan) {
        Lan = lan;
    }

    public String get(String key) {
        if(!phrases.containsKey(key)) {
            return key;
        }
        int index = languages.indexOf(Lan);
        List<String> phrase = phrases.get(key);
        if(index == -1 || phrase.size() <= index) {
            return key;
        }
        return phrase.get(index);

    }
}
