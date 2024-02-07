package org.testHibernate.models;

import com.sun.istack.NotNull;
import org.testHibernate.models.Principal;

import javax.persistence.*;

@Entity
@Table( name = "school")
public class School {
    @Id
    @Column( name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "school_number")
    @NotNull
    private int number;

    @OneToOne
    @JoinColumn(name = "principal_id", referencedColumnName = "id")
    private Principal principal;

    public School(int number, Principal principal) {
        this.number = number;
        this.principal = principal;
    }
    public School(){}

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", number=" + number +
                ", principal=" + principal +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
