package org.pelans.wordle.Database.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ServerConfig")
public class ServerConfig {

    public ServerConfig() {
    }

    public ServerConfig(String serverId) {
        ServerId = serverId;
        Language = "en-US";
        WordRandomForEachUser = false;
        ShareWordle = true;
        ShareStatus = true;
        minWordLength = 5;
        maxWordLength = 5;
    }

    @Id
    @Column(name = "ServerId", length = 50, nullable = false)
    private String ServerId;

    @Column(name = "DailyAnnounceChannelId", length = 50, nullable = true)
    private String DailyAnnounceChannelId;

    @Column(name = "PracticeAnnounceChannelId", length = 50, nullable = true)
    private String PracticeAnnounceChannelId;

    @Column(name = "Language", length = 50, nullable = true)
    private String Language;

    @Column(name = "WordRandomForEachUser",  nullable = true)
    private boolean WordRandomForEachUser;

    @Column(name = "ShareWordle", nullable = true)
    private boolean ShareWordle;

    @Column(name = "ShareStatus", nullable = true)
    private boolean ShareStatus;
    @Column(name = "minWordLength", nullable = true)
    private Integer minWordLength;

    @Column(name = "maxWordLength", nullable = true)
    private Integer maxWordLength;


    public String getServerId() {
        return ServerId;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public boolean isWordRandomForEachUser() {
        return WordRandomForEachUser;
    }

    public void setWordRandomForEachUser(boolean wordRandomForEachUser) {
        WordRandomForEachUser = wordRandomForEachUser;
    }

    public boolean isShareWordle() {
        return ShareWordle;
    }

    public void setShareWordle(boolean shareWordle) {
        ShareWordle = shareWordle;
    }

    public boolean isShareStatus() {
        return ShareStatus;
    }

    public void setShareStatus(boolean shareStatus) {
        ShareStatus = shareStatus;
    }

    public String getDailyAnnounceChannelId() {
        return DailyAnnounceChannelId;
    }

    public void setDailyAnnounceChannelId(String dailyAnnounceChannelId) {
        DailyAnnounceChannelId = dailyAnnounceChannelId;
    }

    public String getPracticeAnnounceChannelId() {
        return PracticeAnnounceChannelId;
    }

    public void setPracticeAnnounceChannelId(String practiceAnnounceChannelId) {
        PracticeAnnounceChannelId = practiceAnnounceChannelId;
    }

    public Integer getMinWordLength() {
        return minWordLength;
    }

    public void setMinWordLength(Integer minWordLength) {
        this.minWordLength = minWordLength;
    }

    public Integer getMaxWordLength() {
        return maxWordLength;
    }

    public void setMaxWordLength(Integer maxWordLength) {
        this.maxWordLength = maxWordLength;
    }
}
