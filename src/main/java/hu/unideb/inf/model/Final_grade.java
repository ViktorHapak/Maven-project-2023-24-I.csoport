package hu.unideb.inf.model;

import javax.persistence.*;

@Entity
public class Final_grade {

    @Id
    @GeneratedValue
    private Long id; //int-ket cserélem lefelé long-ra, így egyenlőre hibát ír ki


    @OneToOne
    private Grade1 grade1;

    @OneToOne
    private Grade2 grade2;

    private int exam;
    private int schoolyear;

    public Final_grade(long id, Grade1 grade1, Grade2 grade2, int exam, int schoolyear) {
        this.id = id;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.exam = exam;
        this.schoolyear = schoolyear;
    }

    public Final_grade() {

    }

    public Final_grade(Grade1 grade1, Grade2 grade2) {
        this.grade1 = grade1;
        this.grade2 = grade2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Grade1 getGrade1() {
        return grade1;
    }

    public void setGrade1(Grade1 grade1) {
        this.grade1 = grade1;
    }

    public Grade2 getGrade2() {
        return grade2;
    }

    public void setGrade2(Grade2 grade2) {
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
