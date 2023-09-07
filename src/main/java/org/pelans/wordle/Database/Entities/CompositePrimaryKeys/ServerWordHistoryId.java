package org.pelans.wordle.Database.Entities.CompositePrimaryKeys;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

@Embeddable
public class ServerWordHistoryId  implements Serializable {
    public ServerWordHistoryId() {

    }
    public ServerWordHistoryId(String serverId) {
        ServerId = serverId;
        CalendarDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public ServerWordHistoryId(String serverId, Calendar calendarDate) {
        ServerId = serverId;
        CalendarDate = calendarDate;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Calendar CalendarDate;
    @Column(name = "ServerId", length = 50, nullable = false)

    private String ServerId;

    public String getServerId() {
        return ServerId;
    }

    public Calendar getCalendarDate() {
        return CalendarDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerWordHistoryId that = (ServerWordHistoryId) o;
        return Objects.equals(CalendarDate, that.CalendarDate) && Objects.equals(ServerId, that.ServerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CalendarDate, ServerId);
    }
}
