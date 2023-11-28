package hu.unideb.inf.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    private User user;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Subject() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
