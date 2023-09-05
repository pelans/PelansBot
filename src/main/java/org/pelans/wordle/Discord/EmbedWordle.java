package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.util.Emojis;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmbedWordle {

    private static EmbedBuilder base(UserWord userWord, boolean hideWords, boolean showAviableLetters) {
        EmbedBuilder eb = new EmbedBuilder();
        String type = userWord.isFirstGame() ? "Daily" : "Practice";
        eb.setTitle( String.format(":cherry_blossom: %s WORDLE :cherry_blossom: (%s letters) :palm_tree:",type, userWord.getCorrectWord().length()));
        StringBuilder sb = new StringBuilder();
        for (String word : userWord.getWords()) {
            if(word == null) {
                sb.append(":black_large_square:".repeat(userWord.getCorrectWord().length()));
            } else {
                for(String text : getColors(word, userWord.getFormattedCorrectWord(), hideWords)) {
                    sb.append(text);
                }
            }
            sb.append("\n");
        }
        if(showAviableLetters) { //Must be improved
            String wordleEmojis = sb.toString();
            sb.append("__**Letters:**__\n");
            sb.append(getEmojis("qwertyuiop", wordleEmojis) + "\n");
            sb.append(getEmojis("asdfghjklñ", wordleEmojis) + "\n");
            sb.append(":black_large_square:" + getEmojis("zxcvbnm", wordleEmojis) + ":black_large_square::black_large_square:\n");
        }
        eb.setDescription(sb);
        return eb;
    }

    public static MessageEmbed wordle(UserWord userWord, boolean hideWords){
        boolean showAviableLetters = true;
        EmbedBuilder eb = base(userWord, hideWords, showAviableLetters);
        return eb.build();
    }

    public static MessageEmbed shareWordle(UserWord userWord, boolean hideWords){
        boolean showAviableLetters = false;
        EmbedBuilder eb = base(userWord, hideWords, showAviableLetters);
        StringBuilder sb = eb.getDescriptionBuilder();
        sb.append("\n ");
        if(userWord.hashWon()) {
            sb.append(String.format("Won by: <@%s> :trophy:", userWord.getMemberId().getUserId()));
        } else {
            sb.append(String.format("Lost by: <@%s> :skull_crossbones:", userWord.getMemberId().getUserId()));
        }
        if(userWord.hashWon()) {
            eb.setColor(Color.GREEN);
        } else {
            eb.setColor(Color.RED);
        }
        return eb.build();
    }

    public static MessageEmbed wordle(UserWord userWord, boolean hideWords, String additionalMessage) {
        boolean showAviableLetters = true;
        EmbedBuilder eb = base(userWord, hideWords, showAviableLetters);
        StringBuilder sb = eb.getDescriptionBuilder();
        sb.append("\n").append(additionalMessage);
        return eb.build();
    }

    private static List<String> getColors(String word1, String word2, boolean hideWords) {
        List<String> result = new ArrayList<String>();
        List<Character> remaining1 = new ArrayList<Character>();
        List<Character> remaining2 = new ArrayList<Character>();
        for (int i=0; i<word1.length(); i++) {
            if(word1.charAt(i) == word2.charAt(i)) {
                if(!hideWords)
                    result.add(Emojis.getGreen(word1.charAt(i)));
                else
                    result.add(":green_square:");
                remaining1.add(null);
                remaining2.add(null);
            } else {
                if(!hideWords)
                    result.add(Emojis.getBlack(word1.charAt(i)));
                else
                    result.add(":red_square:");
                remaining1.add(word1.charAt(i));
                remaining2.add(word2.charAt(i));
            }
        }
        for(int i=0; i<remaining1.size(); i++) {
            if(remaining1.get(i) == null)
                continue;
            if(remaining2.contains(remaining1.get(i))) {
                int index = remaining2.indexOf(remaining1.get(i));
                remaining2.set(index, null);
                if(!hideWords)
                    result.set(i,Emojis.getYellow(remaining1.get(i)));
                else
                    result.set(i,":yellow_square:");
            }
        }
        return result;
    }

    private static String getEmojis(String textToConvert, String textToCompare) {
        //This method will be improved
        StringBuilder result = new StringBuilder();
        String textToCompareLowered = textToCompare.toLowerCase();
        for (int i=0; i<textToConvert.length(); i++) {
            String letter = String.valueOf(textToConvert.toLowerCase().charAt(i));
            if (letter.equals("ñ")) {
                letter = "nn";
            }
            if (textToCompareLowered.contains("green_" + letter + ":")) {
                result.append(Emojis.getGreen(textToConvert.toLowerCase().charAt(i)));
            } else if (textToCompareLowered.contains("yellow_" + letter + ":")) {
                result.append(Emojis.getYellow(textToConvert.toLowerCase().charAt(i)));
            } else if (textToCompareLowered.contains("black_" + letter + ":")) {
                result.append(Emojis.getBlack(textToConvert.toLowerCase().charAt(i)));
            } else {
                result.append(Emojis.getGrey(textToConvert.toLowerCase().charAt(i)));
            }
        }
        return result.toString();
    }

}
