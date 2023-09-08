package org.pelans.wordle.Database.Services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.util.Wordle;
import org.pelans.wordle.util.HibernateUtil;

import java.util.List;

public class UserWordService {

    public static UserWord createUserWord(MemberId id, Boolean firstPlayed) {
        String word;
        ServerConfig serverConfig = ServerConfigService.getServerConfig(id.getServerId());
        if(serverConfig.isWordRandomForEachUser()) {
            word = Wordle.getWord(serverConfig.getMinWordLength(), serverConfig.getMaxWordLength());
        } else {
            ServerWord serverWord = ServerWordService.getServerWord(id.getServerId());
            word = serverWord.getWord();
        }
        UserWord userWord = new UserWord(id, word, firstPlayed, serverConfig.isWordRandomForEachUser());
        putUserWord(userWord);
        return  userWord;
    }

    public static UserWord getUserWord(MemberId id) {
        Session session = HibernateUtil.openSession();
        UserWord userWord = (UserWord) session.get(UserWord.class, id);
        session.close();
        if( userWord == null) {
            userWord = createUserWord(id, true);
        }
        return userWord;
    }

    public static void putUserWord(UserWord userWord) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(userWord);
        transaction.commit();
        session.close();
    }

    public static void removeUserWord(UserWord userWord) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(userWord);
        transaction.commit();
        session.close();
    }

    public static List<UserWord> findAllServerWordWithCriteriaQuery() {
        Session session = HibernateUtil.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserWord> query  = cb.createQuery(UserWord.class);
        Root<UserWord> root = query.from(UserWord.class);
        query.select(root);

        //Example of filter
        //query.where(cb.equal(root.get(MyClass.NUM),ordId));

        Query<UserWord> sessionQuery = session.createQuery(query);
        return sessionQuery.getResultList();
    }


}
