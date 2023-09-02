package org.pelans.wordle;

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
            for (String linea : lines.toList()) {
                words.add(linea);
                wordsWithoutAccent.add(SpanishSpecialCharacters.replaceCharacters(linea));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    public static boolean exists(String word) {
        return wordsWithoutAccent.contains(SpanishSpecialCharacters.replaceCharacters(word));
    }

}
