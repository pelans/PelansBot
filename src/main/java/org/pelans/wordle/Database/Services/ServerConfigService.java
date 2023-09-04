package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.util.HibernateUtil;

public class ServerConfigService {

    public static ServerConfig getServerConfig(String id) {
        Session session = HibernateUtil.openSession();
        ServerConfig serverConfig = (ServerConfig) session.get(ServerConfig.class, id);
        session.close();
        if( serverConfig == null) {
            serverConfig = putServerConfig(id);
        }
        return serverConfig;
    }

    private static synchronized ServerConfig putServerConfig(String id) {
        Session session = HibernateUtil.openSession();
        ServerConfig serverConfig = (ServerConfig) session.get(ServerConfig.class, id);
        if(serverConfig == null) {
            Transaction transaction = session.beginTransaction();
            serverConfig = new ServerConfig(id);
            session.persist(serverConfig);
            transaction.commit();
        }
        session.close();
        return serverConfig;

    }

    public static void putServerConfig(ServerConfig serverConfig) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(serverConfig);
        transaction.commit();
        session.close();
    }
}
