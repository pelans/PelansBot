package org.pelans.wordle.Database.Entities;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.TimeZone;

@Entity
@Table(name = "GlobalSettings")
public class GlobalSettings {

    public GlobalSettings() {
        GlobalKey = "Key";
        CalendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        BugChannelId = "1149286822741024889";
        SuggestionChannelId = "1149286853703389294";
    }
    @Id
    @Column(name = "GlobalKey", length = 50, nullable = false)
    private String GlobalKey;

    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Calendar CalendarDate;

    @Column(name = "BugChannelId", length = 50, nullable = false)
    private String BugChannelId;

    @Column(name = "SuggestionChannelId", length = 50, nullable = false)
    private String SuggestionChannelId;

    public Calendar getCalendarDate() {
        CalendarDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        CalendarDate.set(Calendar.HOUR_OF_DAY, 0);
        CalendarDate.set(Calendar.MINUTE, 0);
        CalendarDate.set(Calendar.SECOND, 0);
        return CalendarDate;
    }

    public void setCalendarDate(Calendar calendarDate) {
        CalendarDate = calendarDate;
    }

    public void setCalendarDate() {
        CalendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public String getBugChannelId() {
        return BugChannelId;
    }

    public void setBugChannelId(String bugChannelId) {
        BugChannelId = bugChannelId;
    }

    public String getSuggestionChannelId() {
        return SuggestionChannelId;
    }

    public void setSuggestionChannelId(String suggestionChannelId) {
        SuggestionChannelId = suggestionChannelId;
    }
}
