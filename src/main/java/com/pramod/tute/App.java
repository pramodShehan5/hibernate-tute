package com.pramod.tute;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class App {

    private static ServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        /*
         Only one sessionfactory should be used in the application.
         That is why getSessionFactory  method is static.
         */

        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        System.out.println("Session is open " + session.isOpen());
        session.getTransaction().commit();
        session.close();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }
}