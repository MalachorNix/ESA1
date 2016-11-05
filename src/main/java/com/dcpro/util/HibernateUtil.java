package com.dcpro.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static final StandardServiceRegistry SERVICE_REGISTRY;

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            SERVICE_REGISTRY = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SESSION_FACTORY = configuration.buildSessionFactory(SERVICE_REGISTRY);
        } catch (Throwable e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
