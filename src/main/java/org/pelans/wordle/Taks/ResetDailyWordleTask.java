package org.pelans.wordle.Taks;

import org.pelans.wordle.Database.Entities.*;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.ServerWordHistoryId;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.UserWordHistoryId;
import org.pelans.wordle.Database.Services.*;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.TimerTask;

public class ResetDailyWordleTask extends TimerTask {

    @Override
    public void run() {
        synchronized (ServerWordService.class) {
            List<ServerWord> serverWords = ServerWordService.findAllServerWordWithCriteriaQuery();
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
            for (ServerWord serverWord : serverWords) {
                ServerWordHistory serverWordHistory = new ServerWordHistory(serverWord, c);
                ServerWordHistoryService.putServerWordHistory(serverWordHistory);
                ServerWordService.removeServerWord(serverWord);
            }
        }

        synchronized (UserWordService.class) {
            List<UserWord> userWords = UserWordService.findAllServerWordWithCriteriaQuery();
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
            for (UserWord userWord : userWords) {
                if(userWord.getWord1() != null && !userWord.isSaved() && userWord.isFirstGame()) {
                    UserWordHistory userWordHistory = new UserWordHistory(userWord, c);
                    UserWordHistoryService.putUserWordHistory(userWordHistory);
                    UserStats userStats = UserStatsService.getUserStats(userWord.getMemberId());
                    UserStatsService.putUserStats(userStats);
                }
                UserWordService.removeUserWord(userWord);
            }
        }
    }
}
