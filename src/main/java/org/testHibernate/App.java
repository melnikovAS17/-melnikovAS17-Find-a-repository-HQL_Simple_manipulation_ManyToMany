package org.testHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testHibernate.models.Actor;
import org.testHibernate.models.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class App
{
    //ManyToMany - создается отдельная таблица с колонками - ссылками на id наших сущностей
    //у этой таблицы primary key(actor_id, movie_id) - составной
    public static void main( String[] args ){
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);
        //Добавляем новых актёров и фильм
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Movie movie = new Movie("Parado su net");
            Actor actor = new Actor("Tomas");
            Actor actor1 = new Actor("Bear");

            //List.of() - изменяемый но не расширяемый (9 - version Java)
            movie.setActorList(new ArrayList<>(Arrays.asList(actor,actor1)));
            //Устанавливаем связь с обеих сторон
            actor.setMovieList(new ArrayList<>(Collections.singletonList(movie)));
            actor1.setMovieList(new ArrayList<>(Collections.singletonList(movie)));
            //Каскадирование не настроено на стороне hibernate для метода SAVE_UPDATE
            session.save(movie);
            session.save(actor);
            session.save(actor1);

            session.getTransaction().commit();
        }
        //Добавляем фильм уже сущ-му актёру
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Actor actor = session.get(Actor.class,1);
            Movie movie = new Movie("Mups");
            actor.getMovieList().add(movie);
            movie.setActorList(new ArrayList<>(Collections.singletonList(actor)));
            session.save(movie);


            session.getTransaction().commit();
        }
        //Удаляем фильм у какого-то актёра
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();){
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Actor actor = session.get(Actor.class,1);
            Movie movie = session.get(Movie.class,1);
            //Чтобы удалять об-ты из коллекций не по индексу, а по значению полей необходимо реалтизовать в наших
            //классах методы equals и hashcode
            actor.getMovieList().remove(movie);
            movie.getActorList().remove(actor);

            session.getTransaction().commit();
        }
    }
}
