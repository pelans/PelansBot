package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.ServerWordHistoryId;

import java.util.Calendar;


@Entity
@Table(name = "ServerWordHistory")
public class ServerWordHistory {

    public ServerWordHistory() {

    }

    public ServerWordHistory(ServerWord serverWord, Calendar c) {
        ServerWordHistoryId = new ServerWordHistoryId(serverWord.getServerId(), c);
        Word = serverWord.getWord();
    }
    public ServerWordHistory(ServerWord serverWord) {
        ServerWordHistoryId = new ServerWordHistoryId(serverWord.getServerId());
        Word = serverWord.getWord();
    }
    public ServerWordHistory(ServerWordHistoryId serverWordHistoryId, String word) {
        ServerWordHistoryId = serverWordHistoryId;
        Word = word;
    }

    @EmbeddedId
    private ServerWordHistoryId ServerWordHistoryId;

    @Column(name = "Word", length = 50, nullable = false)
    private String Word;

    public String getWord() {
        return Word;
    }
    public ServerWordHistoryId getServerWordHistoryId() {
        return ServerWordHistoryId;
    }
}
