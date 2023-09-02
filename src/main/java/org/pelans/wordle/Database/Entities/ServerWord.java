package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ServerWord")
public class ServerWord {

    public ServerWord() {
    }

    public ServerWord(String serverId, String word) {
        ServerId = serverId;
        Word = word;
    }

    @Id
    @Column(name = "ServerId", length = 50, nullable = false)
    private String ServerId;
    @Column(name = "Word", length = 50, nullable = false)
    private String Word;

    public String getServerId() {
        return ServerId;
    }

    public String getWord() {
        return Word;
    }

}
