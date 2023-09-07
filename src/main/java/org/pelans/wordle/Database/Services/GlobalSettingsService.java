package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.GlobalSettings;
import org.pelans.wordle.util.HibernateUtil;

public class GlobalSettingsService {

    public static GlobalSettings getGlobalSettingsService() {
        Session session = HibernateUtil.openSession();
        GlobalSettings GlobalSettings = (GlobalSettings) session.get(GlobalSettings.class, "Key");
        session.close();
        if( GlobalSettings == null) {
            GlobalSettings = putGlobalSettings();
        }
        return GlobalSettings;
    }

    private static synchronized GlobalSettings putGlobalSettings() {
        Session session = HibernateUtil.openSession();
        GlobalSettings GlobalSettings = (GlobalSettings) session.get(GlobalSettings.class, "Key");
        if(GlobalSettings == null) {
            Transaction transaction = session.beginTransaction();
            GlobalSettings = new GlobalSettings();
            session.persist(GlobalSettings);
            transaction.commit();
        }
        session.close();
        return GlobalSettings;

    }

    public static void putGlobalSettings(GlobalSettings GlobalSettings) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(GlobalSettings);
        transaction.commit();
        session.close();
    }
}
