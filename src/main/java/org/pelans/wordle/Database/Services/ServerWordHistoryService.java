package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.ServerWordHistoryId;
import org.pelans.wordle.Database.Entities.ServerWordHistory;
import org.pelans.wordle.util.HibernateUtil;

public class ServerWordHistoryService {

    public static ServerWordHistory getServerWordHistory(ServerWordHistoryId serverWordHistoryId) {
        Session session = HibernateUtil.openSession();
        ServerWordHistory serverWordHistory = (ServerWordHistory) session.get(ServerWordHistory.class, serverWordHistoryId);
        session.close();
        return serverWordHistory;
    }

    public static boolean putServerWordHistory(ServerWordHistory serverWordHistory) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(serverWordHistory);
        transaction.commit();
        session.close();
        return true;
    }
}
