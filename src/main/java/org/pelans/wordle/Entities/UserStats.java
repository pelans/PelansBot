package org.pelans.wordle.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "UserStats")
public class UserStats {

    public UserStats(String serverId, String userId) {
        ServerId = serverId;
        UserId = userId;
    }

    @Id
    @Column(name = "UserStats_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "ServerId", length = 50, nullable = false)
    private final String ServerId;
    @Column(name = "UserId", length = 50, nullable = false)
    private final String UserId;
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

    public String getServerId() {
        return ServerId;
    }

    public String getUserId() {
        return UserId;
    }
}
