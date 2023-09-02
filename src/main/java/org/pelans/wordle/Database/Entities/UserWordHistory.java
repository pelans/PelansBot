package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.UserWordHistoryId;

@Entity
@Table(name = "UserWordHistory")
public class UserWordHistory {

    public UserWordHistory() {
    }

    public UserWordHistory(UserWordHistoryId userWordHistoryId, String word1, String word2, String word3, String word4, String word5, String word6) {
        UserWordHistoryId = userWordHistoryId;
        Word1 = word1;
        Word2 = word2;
        Word3 = word3;
        Word4 = word4;
        Word5 = word5;
        Word6 = word6;
    }
    @EmbeddedId
    private UserWordHistoryId UserWordHistoryId;
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
}
