package org.testHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testHibernate.models.Principal;
import org.testHibernate.models.School;


public class App
{
    //OneToOne - создается так же с внешним ключом, только на ключ вешаем ограничение UNIQUE
    public static void main( String[] args ){
        Configuration configuration = new Configuration().addAnnotatedClass(Principal.class)
                .addAnnotatedClass(School.class);
        //Получение щколы у директора
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

           Principal principal = session.get(Principal.class,1);
            System.out.println(principal.getSchool().getNumber());


            session.getTransaction().commit();
        }
        //Создание нового директора и школы со связыванием этих сущностей
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Principal principal = new Principal("Pool",45);
            //У объекта Owning side - School (в данном случае) в конструкторе с парам-ми указываем об-т Principal
            //А у Principal нет
            School school = new School(12341,principal);
            //Для правильного кеша Hibernate связь уст-м с двух сторон
            //Для автоматизации данного процесса можно в методе setPrincipal у School вызывать метод
            //principal.setSchool(this)
            principal.setSchool(school);
            session.save(principal);

            session.getTransaction().commit();
        }
        //Меняем директора у сущ-й школы
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            School school = session.get(School.class,2);
            Principal principal = new Principal("Yappk", 33);
            session.save(principal);
            school.setPrincipal(principal);
            principal.setSchool(school);

            session.getTransaction().commit();
        }
        //Пробуем добавить вторую школу для существующего директора
        //ОШИБКА повторяющееся значение ключа нарушает ограничение уникальности "school_principal_id_key"
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Principal principal = session.get(Principal.class,3);
            School school = new School(1111,principal);
            session.save(school);
            principal.setSchool(school);

            session.getTransaction().commit();
        }
    }
}
