package org.pelans.wordle.Database.Entities.CompositePrimaryKeys;

import jakarta.persistence.*;
import net.dv8tion.jda.api.entities.User;

import java.util.Calendar;
import java.util.TimeZone;

@Embeddable
public class UserWordHistoryId {

    public UserWordHistoryId() {
    }

    public UserWordHistoryId(String serverId, String userId) {
        CalendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        ServerId = serverId;
        UserId = userId;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Calendar CalendarDate;
    @Column(name = "ServerId", length = 50, nullable = false)
    private String ServerId;

    @Column(name = "UserId", length = 50, nullable = false)
    private String UserId;

    public Calendar getCalendarDate() {
        return CalendarDate;
    }

    public String getServerId() {
        return ServerId;
    }

    public String getUserId() {
        return UserId;
    }
}
