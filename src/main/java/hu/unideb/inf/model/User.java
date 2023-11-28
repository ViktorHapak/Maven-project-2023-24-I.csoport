package hu.unideb.inf.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@NamedQuery(name = "find user by username", query = "SELECT s from User s where s.username = :username")
@NamedQuery(name = "count by username", query = "SELECT count(s) from User s where s.username = :username")

public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "firstname", nullable = false, length = 150)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 150)
    private String lastname;

    @Column(name = "username", nullable = false, length = 150)
    private String username;

    @Column(name = "password", nullable = false, length = 150)
    private String password;

    @Column(name = "accomodation", nullable = false, length = 150)
    private String accomodation;

    public enum Role {visitor, parent,
        teacher,classhead,admin};

    @Column(name = "gender", nullable = false, length = 150)
    public String gender;

    @Column(name = "role", nullable = false, length = 150)
    private Role role;

    @ManyToMany(mappedBy = "parents")
    private Set<Student> students = new HashSet<>();

    public User(String firstname, String lastname,
                String username, String password, String accomodation, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.accomodation = accomodation;
        this.gender = gender;
        this.role= Role.visitor;
    }

    public User() {
        this.role= Role.visitor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accomodation='" + accomodation + '\'' +
                ", role=" + role +
                '}';
    }
}
