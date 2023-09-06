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
        Language = "es-ES";
        WordRandomForEachUser = false;
        ShareWordle = true;
        ShareStatus = true;
    }

    @Id
    @Column(name = "ServerId", length = 50, nullable = false)
    private String ServerId;

    @Column(name = "AnnounceChannelId", length = 50, nullable = true)
    private String AnnounceChannelId;

    @Column(name = "Language", length = 50, nullable = true)
    private String Language;

    @Column(name = "WordRandomForEachUser",  nullable = true)
    private boolean WordRandomForEachUser;

    @Column(name = "ShareWordle", nullable = true)
    private boolean ShareWordle;

    @Column(name = "ShareStatus", nullable = true)
    private boolean ShareStatus;

    public String getServerId() {
        return ServerId;
    }

    public String getAnnounceChannelId() {
        return AnnounceChannelId;
    }

    public String getLanguage() {
        return Language;
    }

    public void setAnnounceChannelId(String announceChannelId) {
        AnnounceChannelId = announceChannelId;
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
}
