package org.example.class_grading.repository;



import org.example.class_grading.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class StudentRepository {
    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public StudentRepository() {
        this.emf = Persistence.createEntityManagerFactory("class_grading_pu");
        this.entityManager = emf.createEntityManager();
    }

    public StudentRepository(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = emf.createEntityManager();
    }

    public Student findById(Long id) {
        Query query = entityManager.createNamedQuery("find student by id");
        query.setParameter("id", id);
        return (Student) query.getSingleResult();
    }

    /* Célszerű megoldani, hogy új tanuló hozzáadásakor a úgy jegybejegyzés is megjelenjen
        ezzel a tanulóval mindegyik tantárgyra nézve, így le kérjük a tantárgyak listáját
        (kaszkádolás):
         */

    public void add(Student student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        SubjectRepository subjectRepository1 = new SubjectRepository();
        int count = (int) subjectRepository1.count();
        int I = 0;
        String[] subjectnum = new String[count];
        while (I<count){
            subjectnum[I] = String.valueOf(subjectRepository1.findIds().get(I));
            I++;
        }

        I = 0;
        for(String i :subjectnum) {
           Grade1 grade1 = new Grade1(subjectRepository1.find(Long.valueOf(i)),student);
           entityManager.getTransaction().begin();
           entityManager.persist(grade1);
           entityManager.getTransaction().commit();

           Grade2 grade2 = new Grade2(subjectRepository1.find(Long.valueOf(i)),student);
            entityManager.getTransaction().begin();
            entityManager.persist(grade2);
            entityManager.getTransaction().commit();

            Final_grade final_grade = new Final_grade(subjectRepository1.find(Long.valueOf(i)),student);
            entityManager.getTransaction().begin();
            entityManager.persist(final_grade);
            entityManager.getTransaction().commit();
        }


    }

    public void addSimply(Student student){
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

    public void deleteByName(String name) {
        entityManager.getTransaction().begin();

        Long studentId = findByName(name).getId();
        System.out.println(studentId);
        Student studentToDelete = entityManager.find(Student.class, studentId);
        System.out.println("To delete: " + studentToDelete.toString());
        entityManager.remove(studentToDelete);
        /*

        if (studentToDelete != null) {
            entityManager.remove(studentToDelete);
        }
        */
        entityManager.getTransaction().commit();
    }


    public Student findByName(String name){
        List<Long> Ids = findIds();
        Student student = new Student();
        for(int i=0; i<Ids.size(); i++){
            if (name.equals(find(Ids.get(i)).getName())) {
                student = findById(Ids.get(i));
                return student;
            }
        }
        return student;
    }


    public long countByName(String name){
        Query query = entityManager.createNamedQuery("count students by name");
        query.setParameter("name",name);
        return (long) query.getSingleResult();

    }

    public boolean checkByName(String name){
        if(countByName(name) == 0) return true;
        else return false;
    }


    public Student find(Long id) {
        return entityManager.find(Student.class, id);
    }

    //Frissítés, adott tanuló adatainak frissítése
    public void update(Student student, Student oldStudent) {
        Student studentToUpdate  = findByName(oldStudent.getName());
        entityManager.getTransaction().begin();
        studentToUpdate.setName(student.getName());
        studentToUpdate.setBirth(student.getBirth());
        studentToUpdate.setAddress(student.getAddress());
        studentToUpdate.setEmail(student.getEmail());

        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    //függvények a tanulók adatlistáihoz (a táblázat oszlopainak szerkesztéséhez)
    public List<Long> findIds(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.id from Student s");
        return query.getResultList();
    }

    public List<String> findNames(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.name from Student s");
        return query.getResultList();
    }

    public List<String> findBirths(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.birth from Student s");
        return query.getResultList();
    }

    public List<String> findAddresses(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.address from Student s");
        return query.getResultList();
    }

    public List<String> findEmails(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.email from Student s");
        return query.getResultList();
    }

    public List<Student> findStudents() {
        Query query = entityManager.createQuery("Select s from Student s");
        return query.getResultList();
    }



    public long count(){
        Query query = entityManager.createQuery("SELECT count(s) from Student s");
        return (Long) query.getSingleResult();
    }



    public void close() {
        this.entityManager.close();
        this.emf.close();
    }



}
