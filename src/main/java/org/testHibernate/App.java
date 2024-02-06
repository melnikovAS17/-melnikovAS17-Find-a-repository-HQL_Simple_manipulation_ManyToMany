package org.testHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class App
{
    public static void main( String[] args ){
        Configuration configuration = new Configuration().addAnnotatedClass(Director.class)
                .addAnnotatedClass(Film.class);
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();



            session.getTransaction().commit();
        }
    }
}
