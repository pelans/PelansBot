package org.pelans.wordle.Taks;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class DailyReset {
    public static void init() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date executionDate = c.getTime();
        long repeatingTime = 24*60*60*1000;
        Timer timer = new Timer();
        timer.schedule(new ResetDailyWordleTask(), executionDate, repeatingTime);
    }
}
