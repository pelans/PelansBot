package org.pelans.wordle.Taks;

import org.pelans.wordle.Database.Entities.GlobalSettings;
import org.pelans.wordle.Database.Services.GlobalSettingsService;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class DailyReset {
    public static void init() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        GlobalSettings globalSettings = GlobalSettingsService.getGlobalSettingsService();
        Calendar cLastUpdate = globalSettings.getCalendarDate();

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date dateToday = c.getTime();
        Date  dateLastUpdate = cLastUpdate.getTime();

        if (c.compareTo(cLastUpdate) > 0) {//If the bot was shutdown when the task should be executed
            new ResetDailyWordleTask().run();
        }

        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        Date executionDate = c.getTime();
        long repeatingTime = 24*60*60*1000;
        Timer timer = new Timer();
        timer.schedule(new ResetDailyWordleTask(), executionDate, repeatingTime);
    }
}
