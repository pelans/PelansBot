package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.util.SpanishSpecialCharacters;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UserWord")
public class UserWord {

    public UserWord() {
    }

    public UserWord(MemberId memberId, String correctWord, Boolean firstGame) {
        MemberId = memberId;
        CorrectWord = correctWord;
        Saved = false;
        FirstGame = firstGame;
    }

    //region Attributes
    @EmbeddedId
    private MemberId MemberId;

    @Column(name = "Saved", nullable = false)
    private Boolean Saved;
    @Column(name = "FirstGame", nullable = false)
    private Boolean FirstGame;
    @Column(name = "CorrectWord", length = 50, nullable = false)
    private String CorrectWord;
    @Column(name = "Word1", length = 50, nullable = true)
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
    //endregion

    //region Getters and Setters


    public String getCorrectWord() {
        return CorrectWord;
    }

    public String getFormattedCorrectWord() {
        return SpanishSpecialCharacters.replaceCharacters(CorrectWord);
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

    public MemberId getMemberId() {
        return MemberId;
    }

    public Boolean isSaved() {
        return Saved;
    }

    public void setSaved(Boolean saved) {
        Saved = saved;
    }

    public Boolean isFirstGame() {
        return FirstGame;
    }

    public void setFirstGame(Boolean firstGame) {
        FirstGame = firstGame;
    }

    //endregion

    //region Methods
    public boolean isComplete() {
        return Word6 != null;
    }
    public boolean addWord(String word) {
        if(isComplete())
            return false;
        String formattedWord = SpanishSpecialCharacters.replaceCharacters(word.toLowerCase());
        if(Word1 == null) {
            Word1 = formattedWord;
        } else if (Word2 == null) {
            Word2 = formattedWord;
        } else if (Word3 == null) {
            Word3 = formattedWord;
        } else if (Word4 == null) {
            Word4 = formattedWord;
        } else if (Word5 == null) {
            Word5 = formattedWord;
        } else {
            Word6 = formattedWord;
        }
        return true;
    }

    public List<String> getWords() {
        List<String> result = new ArrayList<String>();
        result.add(Word1);
        result.add(Word2);
        result.add(Word3);
        result.add(Word4);
        result.add(Word5);
        result.add(Word6);
        return result;
    }

    public boolean hashWon() {
        return getWords().contains(SpanishSpecialCharacters.replaceCharacters(getFormattedCorrectWord()));
    }
    //endregion

}
