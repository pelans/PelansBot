package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "UserWordHistory")
public class UserWordHistory {

    public UserWordHistory(String serverId, String userId, String word1, String word2, String word3, String word4, String word5, String word6) {
        ServerId = serverId;
        UserId = userId;
        Word1 = word1;
        Word2 = word2;
        Word3 = word3;
        Word4 = word4;
        Word5 = word5;
        Word6 = word6;
    }

    @Id
    @Column(name = "UserWordHistory_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "ServerId", length = 50, nullable = false)
    private final String ServerId;
    @Column(name = "UserId", length = 50, nullable = false)
    private final String UserId;
    @Column(name = "Word1", length = 50, nullable = false)
    private final String Word1;
    @Column(name = "Word2", length = 50, nullable = true)
    private final String Word2;
    @Column(name = "Word3", length = 50, nullable = true)
    private final String Word3;
    @Column(name = "Word4", length = 50, nullable = true)
    private final String Word4;
    @Column(name = "Word5", length = 50, nullable = true)
    private final String Word5;
    @Column(name = "Word6", length = 50, nullable = true)
    private final String Word6;

    public String getServerId() {
        return ServerId;
    }

    public String getUserId() {
        return UserId;
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
