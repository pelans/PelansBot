package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.UserStats;
import org.pelans.wordle.util.HibernateUtil;

public class UserStatsService {

    public static UserStats getUserStats(MemberId id) {
        Session session = HibernateUtil.openSession();
        UserStats userStats = (UserStats) session.get(UserStats.class, id);
        session.close();
        if( userStats == null) {
            userStats = new UserStats(id);
        }
        return userStats;
    }

    public static void putUserStats(UserStats userStats) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(userStats);
        transaction.commit();
        session.close();

    }
}
