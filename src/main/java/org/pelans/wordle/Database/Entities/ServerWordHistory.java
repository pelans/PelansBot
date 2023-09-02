package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ServerWordHistory")
public class ServerWordHistory {

    public ServerWordHistory(String serverId, String word) {
        ServerId = serverId;
        Word = word;
    }

    @Id
    @Column(name = "ServerWordHistory_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "ServerId", length = 50, nullable = false)
    private final String ServerId;
    @Column(name = "Word", length = 50, nullable = false)
    private final String Word;

    public String getServerId() {
        return ServerId;
    }

    public String getWord() {
        return Word;
    }
}
