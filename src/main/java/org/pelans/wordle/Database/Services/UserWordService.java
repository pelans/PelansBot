package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.CompositePrimaryKeys.MemberId;
import org.pelans.wordle.Database.Entities.ServerWord;
import org.pelans.wordle.Database.Entities.UserWord;
import org.pelans.wordle.Wordle;
import org.pelans.wordle.util.HibernateUtil;

public class UserWordService {

    public static UserWord getUserWord(MemberId id) {
        Session session = HibernateUtil.openSession();
        UserWord userWord = (UserWord) session.get(UserWord.class, id);
        session.close();
        if( userWord == null) {
            userWord = new UserWord(id);
        }
        return userWord;
    }

    public static boolean putUserWord(UserWord userWord) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(userWord);
        transaction.commit();
        session.close();
        return true;
    }


}
