package org.pelans.wordle.Database.Services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pelans.wordle.Database.Entities.ServerConfig;
import org.pelans.wordle.util.HibernateUtil;

public class ServerConfigService {

    public static ServerConfig getServerConfig(String id) {
        Session session = HibernateUtil.openSession();
        ServerConfig ServerConfig = (ServerConfig) session.get(ServerConfig.class, id);
        session.close();
        if( ServerConfig == null) {
            ServerConfig = putServerConfig(id);
        }
        return ServerConfig;
    }

    private static synchronized ServerConfig putServerConfig(String id) {
        Session session = HibernateUtil.openSession();
        ServerConfig ServerConfig = (ServerConfig) session.get(ServerConfig.class, id);
        if(ServerConfig == null) {
            Transaction transaction = session.beginTransaction();
            ServerConfig = new ServerConfig(id);
            session.persist(ServerConfig);
            transaction.commit();
        }
        session.close();
        return ServerConfig;

    }

    public static boolean putServerConfig(ServerConfig serverConfig) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(serverConfig);
        transaction.commit();
        session.close();
        return true;
    }
}
