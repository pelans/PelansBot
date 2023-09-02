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
    }

    @Id
    @Column(name = "ServerId", length = 50, nullable = false)
    private String ServerId;

    @Column(name = "AnnounceChannelId", length = 50, nullable = true)
    private String AnnounceChannelId;

    @Column(name = "Language", length = 50, nullable = true)
    private String Language;

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
}
