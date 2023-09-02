package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Wordle;
import org.pelans.wordle.util.HibernateUtil;

public class ServerWordService {

    public static ServerWord getServerWord(String id) {
        Session session = HibernateUtil.openSession();
        ServerWord serverWord = (ServerWord) session.get(ServerWord.class, id);
        session.close();
        if( serverWord == null) {
            serverWord = putServerWord(id);
        }
        return serverWord;
    }

    private static synchronized ServerWord putServerWord(String id) {
        Session session = HibernateUtil.openSession();
        ServerWord serverWord = (ServerWord) session.get(ServerWord.class, id);
        if(serverWord == null) {
            Transaction transaction = session.beginTransaction();
            serverWord = new ServerWord(id, Wordle.getWord());
            session.persist(serverWord);
            transaction.commit();
        }
        session.close();
        return serverWord;

    }

    public static void removeServerWord(ServerWord serverWord) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(serverWord);
        transaction.commit();
        session.close();
    }


}
