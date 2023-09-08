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

    private static List<String> wordsEs = new ArrayList<String>();
    private static List<String> wordsEn = new ArrayList<String>();
    private static List<String> wordsWithoutAccent = new ArrayList<String>();
    public static void init() {
        try (
                Stream<String> lines = Files.lines(Paths.get("src/main/resources/0_palabras_todas_no_conjugaciones.txt"), StandardCharsets.UTF_8)
        ) {
            for (String line : lines.toList()) {
                wordsEs.add(line);
                wordsWithoutAccent.add(SpanishSpecialCharacters.replaceCharacters(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
                Stream<String> lines = Files.lines(Paths.get("src/main/resources/words_alpha.txt"), StandardCharsets.UTF_8)
        ) {
            for (String line : lines.toList()) {
                wordsEn.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWord(String lan, Integer min, Integer max) {
        List<String> wordsFilter = getFilterWordList(lan, min, max);
        Random rand = new Random();
        return wordsFilter.get(rand.nextInt(wordsFilter.size()));
    }

    private static List<String> getFilterWordList(String lan, Integer min, Integer max) {
        if(min == null && max == null) {
            return getListByLan(lan);
        }
        List<String> wordsFilter = new ArrayList<>();
        for(String word : getListByLan(lan)) {
            if((min == null || min <= word.length()) && (max == null || max >= word.length()))
                wordsFilter.add(word);
        }
        return wordsFilter;
    }

    private static List<String> getListByLan(String lan) {
        if(lan.equals("en-US")) {
            return wordsEn;
        } else if (lan.equals("es-ES")) {
            return wordsEs;
        }
        return wordsEs;
    }

    public static boolean exists(String lan, String word) {
        if(lan.equals("es-ES")) {
            return wordsWithoutAccent.contains(SpanishSpecialCharacters.replaceCharacters(word.toLowerCase()));
        }
        List<String> words = getListByLan(lan);
        return words.contains(word.toLowerCase());
    }

}
