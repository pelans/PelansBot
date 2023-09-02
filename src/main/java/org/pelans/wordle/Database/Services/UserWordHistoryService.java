package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.UserWordHistoryId;
import org.pelans.wordle.Database.Entities.UserWordHistory;
import org.pelans.wordle.util.HibernateUtil;

public class UserWordHistoryService {
    public static UserWordHistory getUserWordHistory(UserWordHistoryId userWordHistoryId) {
        Session session = HibernateUtil.openSession();
        UserWordHistory UserWordHistory = (UserWordHistory) session.get(UserWordHistory.class, userWordHistoryId);
        session.close();
        return UserWordHistory;
    }

    public static boolean putUserWordHistory(UserWordHistory UserWordHistory) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(UserWordHistory);
        transaction.commit();
        session.close();
        return true;
    }
}
