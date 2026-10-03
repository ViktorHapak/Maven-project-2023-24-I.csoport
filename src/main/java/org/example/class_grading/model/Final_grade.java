package org.example.class_grading.model;

import javax.persistence.*;

@Entity
public class Final_grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //int-ket cserélem lefelé long-ra, így egyenlőre hibát ír ki

    @ManyToOne
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;


    private int grade1;


    private int grade2;

    private int exam;
    private int schoolyear;

    public Final_grade(long id, int grade1, int grade2, int exam, int schoolyear) {
        this.id = id;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.exam = exam;
        this.schoolyear = schoolyear;
    }

    public Final_grade() {

    }

    public Final_grade(int grade1, int grade2) {
        this.grade1 = grade1;
        this.grade2 = grade2;
    }

    public Final_grade(Subject subject, Student student) {
        this.subject = subject;
        this.student = student;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGrade1() {
        return grade1;
    }

    public void setGrade1(int grade1) {
        this.grade1 = grade1;
    }

    public int getGrade2() {
        return grade2;
    }

    public void setGrade2(int grade2) {
        this.grade2 = grade2;
    }

    public int getExam() {
        return exam;
    }

    public void setExam(int exam) {
        this.exam = exam;
    }

    public int getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(int schoolyear) {
        this.schoolyear = schoolyear;
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

    @Override
    public String toString() {
        return "Final_grade{" +
                "id=" + id +
                ", grade1=" + grade1 +
                ", grade2=" + grade2 +
                ", exam=" + exam +
                ", schoolyear=" + schoolyear +
                '}';
    }
}
