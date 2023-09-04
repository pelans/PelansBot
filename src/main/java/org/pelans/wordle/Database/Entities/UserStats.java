package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "UserStats")
public class UserStats {

    public UserStats() {}

    public UserStats(MemberId memberId) {
        MemberId = memberId;
        CurrentStreak = 0;
        MaxStreak = 0;
        Correct1 = 0;
        Correct2 = 0;
        Correct3 = 0;
        Correct4 = 0;
        Correct5 = 0;
        Correct6 = 0;
        Failed = 0;
    }

    //region Attributes
    @EmbeddedId
    private MemberId MemberId;

    @Column(name = "CurrentStreak", nullable = false)
    private Integer CurrentStreak;

    @Column(name = "MaxStreak", nullable = false)
    private Integer MaxStreak;
    @Column(name = "Correct1", nullable = false)
    private Integer Correct1;
    @Column(name = "Correct2", nullable = false)
    private Integer Correct2;
    @Column(name = "Correct3", nullable = false)
    private Integer Correct3;
    @Column(name = "Correct4", nullable = false)
    private Integer Correct4;
    @Column(name = "Correct5", nullable = false)
    private Integer Correct5;
    @Column(name = "Correct6", nullable = false)
    private Integer Correct6;
    @Column(name = "Failed", nullable = false)
    private Integer Failed;

    //endregion

    //region Getters and Setters


    public Integer getCorrect1() {
        return Correct1;
    }

    public void setCorrect1(Integer correct1) {
        Correct1 = correct1;
    }

    public Integer getCorrect2() {
        return Correct2;
    }

    public void setCorrect2(Integer correct2) {
        Correct2 = correct2;
    }

    public Integer getCorrect3() {
        return Correct3;
    }

    public void setCorrect3(Integer correct3) {
        Correct3 = correct3;
    }

    public Integer getCorrect4() {
        return Correct4;
    }

    public void setCorrect4(Integer correct4) {
        Correct4 = correct4;
    }

    public Integer getCorrect5() {
        return Correct5;
    }

    public void setCorrect5(Integer correct5) {
        Correct5 = correct5;
    }

    public Integer getCorrect6() {
        return Correct6;
    }

    public void setCorrect6(Integer correct6) {
        Correct6 = correct6;
    }

    public Integer getFailed() {
        return Failed;
    }

    public void setFailed(Integer failed) {
        Failed = failed;
    }

    public MemberId getMemberId() {
        return MemberId;
    }

    public Integer getMaxStreak() {
        return MaxStreak;
    }

    public Integer getCurrentStreak() {
        return CurrentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        CurrentStreak = currentStreak;
    }

    //endregion

    //region Methods
    public void add(UserWord userWord) {
        if(userWord.getWord1() == null)
            return;
        if(!userWord.hashWon()) {
            Failed++;
            CurrentStreak=0;
            return;
        }
        CurrentStreak++;
        if(CurrentStreak > MaxStreak)
            MaxStreak = CurrentStreak;
        if(userWord.getWord2() == null ) {
            Correct1++;
        } else if(userWord.getWord3() == null) {
            Correct2++;
        } else if(userWord.getWord4() == null) {
            Correct3++;
        } else if(userWord.getWord5() == null) {
            Correct4++;
        } else if(userWord.getWord6() == null) {
            Correct5++;
        } else {
            Correct6++;
        }
    }

    public int gamesPlayed() {
        return getCorrect1() + getCorrect2() + getCorrect3() + getCorrect4() + getCorrect5() + getCorrect6() + getFailed();
    }

    public int gamesSolved() {
        return getCorrect1() + getCorrect2() + getCorrect3() + getCorrect4() + getCorrect5() + getCorrect6();
    }

    public int mostFrequent() {
        List<Integer> values = new ArrayList<>();
        values.add(getCorrect1());
        values.add(getCorrect2());
        values.add(getCorrect3());
        values.add(getCorrect4());
        values.add(getCorrect5());
        values.add(getCorrect6());
        values.add(getFailed());
        return Collections.max(values);
    }

    public double avgGuess() {
        return (getCorrect1() + getCorrect2() * 2 + getCorrect3() * 3 + getCorrect4() * 4 + getCorrect5() * 5 +
                getCorrect6() * 6 + getFailed() * 7 + 0.0)/gamesPlayed();
    }


    //endregion

}
