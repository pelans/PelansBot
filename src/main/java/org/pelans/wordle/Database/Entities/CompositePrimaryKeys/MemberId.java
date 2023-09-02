package org.pelans.wordle.Database.Entities.CompositePrimaryKeys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MemberId implements Serializable {

    public MemberId() {
    }
    public MemberId(String serverId, String userId) {
        this.ServerId = serverId;
        this.UserId = userId;
    }
    private String ServerId;
    private String UserId;

    public String getServerId() {
        return ServerId;
    }

    public String getUserId() {
        return UserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(ServerId, memberId.ServerId) && Objects.equals(UserId, memberId.UserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ServerId, UserId);
    }
}
