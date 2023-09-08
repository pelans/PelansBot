package org.pelans.wordle.Database.Services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.util.Wordle;
import org.pelans.wordle.util.HibernateUtil;

import java.util.List;

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
        ServerConfig serverConfig = ServerConfigService.getServerConfig(id);
        if(serverWord == null) {
            Transaction transaction = session.beginTransaction();
            serverWord = new ServerWord(id, Wordle.getWord(serverConfig.getLanguage(), serverConfig.getMinWordLength(), serverConfig.getMaxWordLength()));
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

    public static List<ServerWord> findAllServerWordWithCriteriaQuery() {
        Session session = HibernateUtil.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ServerWord> query  = cb.createQuery(ServerWord.class);
        Root<ServerWord> root = query.from(ServerWord.class);
        query.select(root);

        //Example of filter
        //query.where(cb.equal(root.get(MyClass.NUM),ordId));

        Query<ServerWord> sessionQuery = session.createQuery(query);
        return sessionQuery.getResultList();
    }


}
