package org.pelans.wordle.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.util.SpanishSpecialCharacters;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Embeds {

    private static EmbedBuilder base(UserWord userWord, String correctWord, boolean hideWords) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(":regional_indicator_w: :regional_indicator_o: :regional_indicator_r: :regional_indicator_d:" +
                " :regional_indicator_l: :regional_indicator_e:" +
                String.format(" (%s letters)",correctWord.length()));
        StringBuilder sb = new StringBuilder();
        for (String word : userWord.getWords()) {
            if(word == null) {
                sb.append(":black_large_square:".repeat(correctWord.length()));
            } else {
                for(String text : getColors(word, SpanishSpecialCharacters.replaceCharacters(correctWord))) {
                    sb.append(text);
                }
            if(!hideWords)
                sb.append(word);
            }
            sb.append("\n");
        }
        eb.setDescription(sb);
        return eb;
    }

    public static MessageEmbed wordle(UserWord userWord, String correctWord, boolean hideWords){
        EmbedBuilder eb = base(userWord, correctWord, hideWords);
        return eb.build();
    }

    public static MessageEmbed shareWordle(UserWord userWord, String correctWord, boolean hideWords){
        EmbedBuilder eb = base(userWord, correctWord, hideWords);
        StringBuilder sb = eb.getDescriptionBuilder();
        sb.append("\n ");
        sb.append(String.format("<@%s>",userWord.getMemberId().getUserId()));
        if(userWord.hashWon(correctWord)) {
            eb.setColor(Color.GREEN);
        } else {
            eb.setColor(Color.RED);
        }
        return eb.build();
    }

    public static MessageEmbed wordle(UserWord userWord, String correctWord, boolean hideWords, String additionalMessage) {
        EmbedBuilder eb = base(userWord, correctWord,hideWords);
        StringBuilder sb = eb.getDescriptionBuilder();
        sb.append(additionalMessage);
        return eb.build();
    }

    private static List<String> getColors(String word1, String word2) {
        List<String> result = new ArrayList<String>();
        List<Character> remaining1 = new ArrayList<Character>();
        List<Character> remaining2 = new ArrayList<Character>();
        for (int i=0; i<word1.length(); i++) {
            if(word1.charAt(i) == word2.charAt(i)) {
                result.add(":green_square:");
                remaining1.add(null);
                remaining2.add(null);
            } else {
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
                result.set(i,":yellow_square:");
            }
        }
        return result;
    }

}
