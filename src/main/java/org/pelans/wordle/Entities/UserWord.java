package org.pelans.wordle.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "UserWord")
public class UserWord {

    public UserWord(String serverId, String userId) {
        ServerId = serverId;
        UserId = userId;
    }

    @Id
    @Column(name = "UserWord_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "ServerId", length = 50, nullable = false)
    private final String ServerId;
    @Column(name = "UserId", length = 50, nullable = false)
    private final String UserId;
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

    public void setWord1(String word1) {
        Word1 = word1;
    }

    public void setWord2(String word2) {
        Word2 = word2;
    }

    public void setWord3(String word3) {
        Word3 = word3;
    }

    public void setWord4(String word4) {
        Word4 = word4;
    }

    public void setWord5(String word5) {
        Word5 = word5;
    }

    public void setWord6(String word6) {
        Word6 = word6;
    }
}
