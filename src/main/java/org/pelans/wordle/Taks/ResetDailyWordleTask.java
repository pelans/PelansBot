package org.pelans.wordle.Taks;

import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.ServerWordHistoryId;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.UserWordHistoryId;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Database.Entities.ServerWordHistory;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.Database.Entities.UserWordHistory;
import org.pelans.wordle.Database.Services.ServerWordHistoryService;
import org.pelans.wordle.Database.Services.ServerWordService;
import org.pelans.wordle.Database.Services.UserWordHistoryService;
import org.pelans.wordle.Database.Services.UserWordService;

import java.util.List;
import java.util.TimerTask;

public class ResetDailyWordleTask extends TimerTask {

    @Override
    public void run() {
        synchronized (ServerWordService.class) {
            List<ServerWord> serverWords = ServerWordService.findAllServerWordWithCriteriaQuery();
            for (ServerWord serverWord : serverWords) {
                ServerWordHistory serverWordHistory = new ServerWordHistory(serverWord);
                ServerWordHistoryService.putServerWordHistory(serverWordHistory);
                ServerWordService.removeServerWord(serverWord);
            }
        }

        synchronized (UserWordService.class) {
            List<UserWord> userWords = UserWordService.findAllServerWordWithCriteriaQuery();
            for (UserWord userWord : userWords) {
                UserWordHistory userWordHistory = new UserWordHistory(userWord);
                UserWordHistoryService.putUserWordHistory(userWordHistory);
                UserWordService.removeUserWord(userWord);
            }
        }
    }
}
