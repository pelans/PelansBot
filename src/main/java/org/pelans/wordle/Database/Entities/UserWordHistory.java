package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;
import net.dv8tion.jda.api.entities.User;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.UserWordHistoryId;

import java.util.Calendar;

@Entity
@Table(name = "UserWordHistory")
public class UserWordHistory {

    public UserWordHistory() {
    }

    public UserWordHistory(UserWord userWord) {
        UserWordHistoryId = new UserWordHistoryId(
                userWord.getMemberId().getServerId(), userWord.getMemberId().getUserId());
        CorrectWord = userWord.getCorrectWord();
        Word1 = userWord.getWord1();
        Word2 = userWord.getWord2();
        Word3 = userWord.getWord3();
        Word4 = userWord.getWord4();
        Word5 = userWord.getWord5();
        Word6 = userWord.getWord6();
    }

    public UserWordHistory(UserWord userWord, Calendar c) {
        UserWordHistoryId = new UserWordHistoryId(
                userWord.getMemberId().getServerId(), userWord.getMemberId().getUserId(), c);
        CorrectWord = userWord.getCorrectWord();
        WordRandom = userWord.isWordRandom();
        Word1 = userWord.getWord1();
        Word2 = userWord.getWord2();
        Word3 = userWord.getWord3();
        Word4 = userWord.getWord4();
        Word5 = userWord.getWord5();
        Word6 = userWord.getWord6();
    }

    @EmbeddedId
    private UserWordHistoryId UserWordHistoryId;

    @Column(name = "CorrectWord", length = 50, nullable = false)
    private String CorrectWord;
    @Column(name = "WordRandom", nullable = false)
    private Boolean WordRandom;
    @Column(name = "Word1", length = 50, nullable = false)
    private String Word1;
    @Column(name = "Word2", length = 50, nullable = true)
    private String Word2;
    @Column(name = "Word3", length = 50, nullable = true)
    private String Word3;
    @Column(name = "Word4", length = 50, nullable = true)
    private String Word4;
    @Column(name = "Word5", length = 50, nullable = true)
    private String Word5;
    @Column(name = "Word6", length = 50, nullable = true)
    private String Word6;

    public UserWordHistoryId getUserWordHistoryId() {
        return UserWordHistoryId;
    }

    public String getWord1() {
        return Word1;
    }

    public String getWord2() {
        return Word2;
    }

    public String getWord3() {
        return Word3;
    }

    public String getWord4() {
        return Word4;
    }

    public String getWord5() {
        return Word5;
    }

    public String getWord6() {
        return Word6;
    }

    public String getCorrectWord() {
        return CorrectWord;
    }

    public Boolean isWordRandom() {
        return WordRandom;
    }
}
