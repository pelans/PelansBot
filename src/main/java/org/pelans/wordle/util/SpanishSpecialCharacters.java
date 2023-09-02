package org.pelans.wordle.util;

public class SpanishSpecialCharacters {
    public static String replaceCharacters(String word) {
        return word.replace('á','a').replace('é','e')
                .replace('í','i').replace('ó','o')
                .replace('ü','u');
    }
}
