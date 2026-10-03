package org.example.class_grading.repository;



import org.example.class_grading.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public SubjectRepository() {
        this.emf = Persistence.createEntityManagerFactory("class_grading_pu");
        this.entityManager = emf.createEntityManager();
    }

    public SubjectRepository(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = emf.createEntityManager();
    }

    /*A tanulók és jegy-bejegyzések közötti perzisztens kapcsolat miatt nem tudunk
    tantárgyra hozzáadni, ezért ha új tárgyat adunk hozzá, töröljük, majd ismét hozzáadjuk
    a tanulókat, így azok az új tárggyal együtt jelennek majd meg:
     */

    public void add(Subject subject) {
        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.getTransaction().commit();

        StudentRepository studentRepository = new StudentRepository();
        GradeRepository gradeRepository = new GradeRepository();

        Student student;
        List<String> Students = studentRepository.findNames();
        Student[] studentArray = new Student[Students.size()];

        //Mentsük ki a jegybejegyzéseket is, mert új tantárgyra töröljük a táblából a diákokkal együtt:

        Grade1 grade1;
        List<Long> Grades1 = gradeRepository.findIds1();
        Grade1[] grade1Array = new Grade1[Grades1.size()];

        Grade2 grade2;
        List<Long> Grades2 = gradeRepository.findIds2();
        Grade2[] grade2Array = new Grade2[Grades2.size()];

        Final_grade final_grade;
        List<Long> Final_grades = gradeRepository.findIdsYear();
        Final_grade[] final_gradeArray = new Final_grade[Final_grades.size()];

        for(int i=0; i<Grades1.size(); i++){
            grade1 = gradeRepository.find1(Grades1.get(i));
            grade1Array[i] = grade1;
        }

        for(int i=0; i<Grades2.size(); i++){
            grade2 = gradeRepository.find2(Grades2.get(i));
            grade2Array[i] = grade2;
        }

        for(int i=0; i<Final_grades.size(); i++){
            final_grade = gradeRepository.findYear(Final_grades.get(i));
            final_gradeArray[i] = final_grade;
        }

        /*Tantárgybejegyzéseket elmentettük a tömbökbe, újraíráshoz töröljök a tanulókat, kimentve
        közben egy tömbbe a tanulókat is:
         */

        for(int i=0; i<Students.size(); i++){
            student = studentRepository.findByName(Students.get(i));
            studentArray[i] = student;
            studentRepository.deleteByName(student.getName());
        }

        //Törlés után visszatöltjük a tanulókat és a bejegyzéseket az új tantárgynak megfelelően:

        for(int i=0; i<studentArray.length; i++){
            Student student1 = new Student(studentArray[i].getName(),
                    studentArray[i].getBirth(), studentArray[i].getAddress(),
                    studentArray[i].getEmail());
            studentRepository.add(student1);
        }

        List<Long> Grades1New = gradeRepository.findIds1();
        List<Long> Grades2New = gradeRepository.findIds2();

        for(int i=0; i<Grades1.size(); i++){
            gradeRepository.update1(grade1Array[i],
                    gradeRepository.find1(Grades1New.get(i)));
        }

        for(int i=0; i<Grades2.size(); i++){
            gradeRepository.update2(grade2Array[i],
                    gradeRepository.find2(Grades2New.get(i)));
        }

        /*for(int i=0; i<Students.size(); i++){
            student = studentRepository.findByName(Students.get(i));
            gradeRepository.add1(new Grade1(subject,student));
            gradeRepository.add2(new Grade2(subject,student));
            gradeRepository.addYear(new Final_grade(subject,student));
        }*/

    }

    public void delete(Subject subject) {
        GradeRepository gradeRepository = new GradeRepository();
        System.out.println(subject.getName());
        List<Long> grade1Ids = gradeRepository.findIds1();
        List<Long> grade2Ids= gradeRepository.findIds2();
        List<Long> final_gradeIds = gradeRepository.findIdsYear();
        List<Boolean> isDelete = new ArrayList<>();

        List<Grade1> grades1 = new ArrayList<>(); Grade1 grade1;
        List<Grade2> grades2 = new ArrayList<>();; Grade2 grade2;
        List<Final_grade> final_grades = new ArrayList<>(); Final_grade final_grade;


        for(int i=0; i<grade1Ids.size(); i++) {
            System.out.println("Grades1: " + grade1Ids.get(i));
            System.out.println("Grades2: " + grade1Ids.get(i));
            System.out.println("Final_grades: " + final_gradeIds.get(i));
            grades1.add(gradeRepository.find1(grade1Ids.get(i)));
            grades2.add(gradeRepository.find2(grade2Ids.get(i)));
            final_grades.add(gradeRepository.findYear(final_gradeIds.get(i)));
        }

        /* Kezelhetjük egy ciklussal a 3 osztály adatstruktúráit, tudva, hogy ugyananni elemből
        kell, hogy álljanak  */

        /* Feltételt szabva megadjuk, hogy akkor töröljön egy grade-bejegyzést, ha annak tárgya
        egyezik a törlendő tárggyal. Ezután töröljük a törlendő tárgyat is.
         */
        for(int i=0; i<grade1Ids.size(); i++) {
            if (grades1.get(i).getSubject().getName().equals(subject.getName())) {
                gradeRepository.deleteStudent1(grades1.get(i));
                gradeRepository.deleteStudent2(grades2.get(i));
                gradeRepository.deleteStudentYear(final_grades.get(i));
                isDelete.add(true);
            } else isDelete.add(false);
        }

        entityManager.getTransaction().begin();
        entityManager.remove(subject);
        entityManager.getTransaction().commit();

    }



    public void deleteSimply(Subject subject){
        entityManager.getTransaction().begin();
        entityManager.remove(subject);
        entityManager.getTransaction().commit();
    }




    public Subject find(Long id) {
        return entityManager.find(Subject.class, id);
    }

    public List<Subject> findSubjects(){

        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s from Subject s");
        return query.getResultList();
    }

    public List<Long> findIds(){

        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.id from Subject s");
        return query.getResultList();
    }

    public List<Subject> findSubjectNames(){

        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.name from Subject s");
        return query.getResultList();
    }

    public Subject findByName(String name){
        List<Long> Ids = findIds();
        Subject subject = new Subject();
        for(int i=0; i<Ids.size(); i++){
            if (name.equals(find(Ids.get(i)).getName())) {
                subject = find(Ids.get(i));
                return subject;
            }
        }
        return subject;
    }


    public long count(){
        Query query = entityManager.createQuery("SELECT count(s) from Subject s");
        return (Long) query.getSingleResult();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
