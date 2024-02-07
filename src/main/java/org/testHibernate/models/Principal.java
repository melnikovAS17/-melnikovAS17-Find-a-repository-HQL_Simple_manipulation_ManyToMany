package org.testHibernate.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "principal")
public class Principal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    //Каскадирование на стороне директора работет в обе стороны, SAVE_UPDATE - метод save будет атвоматически
    //сохранять оба об-та и директора, и школу
    //@OneToOne - mappedBy(имя поля foreign key в классе school)
    @OneToOne(mappedBy = "principal")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private School school;

    public Principal(){}

    public Principal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
