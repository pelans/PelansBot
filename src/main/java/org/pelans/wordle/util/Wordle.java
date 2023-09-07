package org.pelans.wordle.util;

import org.pelans.wordle.util.SpanishSpecialCharacters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Wordle {

    private static List<String> words = new ArrayList<String>();
    private static List<String> wordsWithoutAccent = new ArrayList<String>();
    public static void init() {
        try (
                Stream<String> lines = Files.lines(Paths.get("src/main/resources/0_palabras_todas_no_conjugaciones.txt"), StandardCharsets.UTF_8)
        ) {
            for (String line : lines.toList()) {
                words.add(line);
                wordsWithoutAccent.add(SpanishSpecialCharacters.replaceCharacters(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWord(Integer min, Integer max) {
        List<String> wordsFilter = getFilterWordList(min, max);
        Random rand = new Random();
        return wordsFilter.get(rand.nextInt(wordsFilter.size()));
    }

    private static List<String> getFilterWordList(Integer min, Integer max) {
        if(min == null && max == null) {
            return words;
        }
        List<String> wordsFilter = new ArrayList<>();
        for(String word : words) {
            if((min == null || min <= word.length()) && (max == null || max >= word.length()))
                wordsFilter.add(word);
        }
        return wordsFilter;
    }

    public static boolean exists(String word) {
        return wordsWithoutAccent.contains(SpanishSpecialCharacters.replaceCharacters(word.toLowerCase()));
    }

}
