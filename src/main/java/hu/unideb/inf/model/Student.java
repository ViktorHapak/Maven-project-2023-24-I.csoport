/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "student")
@NamedQuery(name = "find student by id", query = "SELECT s from Student s where s.id = :id")
public class Student {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private LocalDate birth;
    private String address;
    private String email;
    private String feauture;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "parents_students",
            joinColumns =  { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") },
            uniqueConstraints = {
                    @UniqueConstraint(
                            columnNames = { "user_id", "student_id" }
                    )
            }
    )
    private Set<User> parents = new HashSet<>();

    public Student(String name, LocalDate birth, String address, String email) {
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.email = email;
    }

    public Student() {

    }

    public Student(long id, String name, LocalDate birth, String address, String email) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.email = email;
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeauture() {
        return feauture;
    }

    public void setFeauture(String feauture) {
        this.feauture = feauture;
    }

    public Set<User> getParents() {
        return parents;
    }

    public void setParents(Set<User> parents) {
        this.parents = parents;
    }

    public void addParent(User user) {
        boolean added = parents.add(user);
        if(added) {
            user.getStudents().add(this);
        }
    }

    public void removeParent(User user) {
        boolean removed = parents.remove(user);
        if(removed) {
            user.getStudents().remove(this);
        }
    }
}
