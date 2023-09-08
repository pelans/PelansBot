package org.pelans.wordle.util;

import org.pelans.wordle.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("language.csv");
        try
         {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> lines = br.lines().toList();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Language(String lan) {
        Lan = lan;
    }

    public String getLan() {
        return Lan;
    }

    public String getDictionary(String word) {
        switch (Lan) {
            case "es-ES" -> {
                return "https://dle.rae.es/" + word;
            }
            case "en-US" -> {
                return "https://en.wiktionary.org/wiki/" + word;
            }
        }
        return null;
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
