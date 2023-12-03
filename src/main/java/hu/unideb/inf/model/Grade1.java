package hu.unideb.inf.model;

import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Grade1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;

    @OneToMany(mappedBy = "grade1", cascade = CascadeType.ALL)
    private List<Final_grade> finalGrades;
    private String modulegrades1;
    private Integer module1;
    private String modulegrades2;
    private Integer module2;
    private String modulegrades3;
    private Integer module3;
    private Integer semester;

    public Grade1(Subject subject, Student student) {
        this.subject = subject;
        this.student = student;
    }

    public Grade1() {

    }

    public Grade1(Subject subject, Student student, String modulegrades1,
                  Integer module1, String modulegrades2, Integer module2,
                  String modulegrades3, Integer module3, Integer semester) {
        this.subject = subject;
        this.student = student;
        this.modulegrades1 = modulegrades1;
        this.module1 = module1;
        this.modulegrades2 = modulegrades2;
        this.module2 = module2;
        this.modulegrades3 = modulegrades3;
        this.module3 = module3;
        this.semester = semester;
    }

    public Grade1(Long id, String name, String modulegrades1,
                  Integer module1, String modulegrades2, Integer module2,
                  String modulegrades3, Integer module3, Integer semester) {
        this.id =id;
        this.name = name;
        this.modulegrades1 = modulegrades1;
        this.module1 = module1;
        this.modulegrades2 = modulegrades2;
        this.module2 = module2;
        this.modulegrades3 = modulegrades3;
        this.module3 = module3;
        this.semester = semester;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getModulegrades1() {
        return modulegrades1;
    }

    public void setModulegrades1(String modulegrades1) {
        this.modulegrades1 = modulegrades1;
    }

    public Integer getModule1() {
        return module1;
    }

    public void setModule1(Integer module1) {
        this.module1 = module1;
    }

    public String getModulegrades2() {
        return modulegrades2;
    }

    public void setModulegrades2(String modulegrades2) {
        this.modulegrades2 = modulegrades2;
    }

    public Integer getModule2() {
        return module2;
    }

    public void setModule2(Integer module2) {
        this.module2 = module2;
    }

    public String getModulegrades3() {
        return modulegrades3;
    }

    public void setModulegrades3(String modulegrades3) {
        this.modulegrades3 = modulegrades3;
    }

    public Integer getModule3() {
        return module3;
    }

    public void setModule3(Integer module3) {
        this.module3 = module3;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Grade1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject=" + subject +
                ", student=" + student +
                ", modulegrades1='" + modulegrades1 + '\'' +
                ", module1=" + module1 +
                ", modulegrades2='" + modulegrades2 + '\'' +
                ", module2=" + module2 +
                ", modulegrades3='" + modulegrades3 + '\'' +
                ", module3=" + module3 +
                ", semester=" + semester +
                '}';
    }
}
