package hu.unideb.inf.repository;

import hu.unideb.inf.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class SubjectRepository {

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public SubjectRepository() {
        this.emf = Persistence.createEntityManagerFactory("student_pu");
        this.entityManager = emf.createEntityManager();
    }

    public SubjectRepository(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = emf.createEntityManager();
    }

    public void add(Subject subject) {
        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.getTransaction().commit();

        /* Hasonlóképpen itt is célszerű megoldani, hogy tantárgy hozzáadásakor megjelenje
         új eredményjegy-bejegyzés ezzel a tantárggyal minden tanuló számára:*/

        StudentRepository studentRepository1 = new StudentRepository();
        int count = (int) studentRepository1.count();
        int I = 0;
        String[] studentnum = new String[count];
        while (I<count) {
            studentnum[I] = String.valueOf(studentRepository1.findIds().get(I));
            I++;
        }

        I=0;
        for(String i: studentnum) {
            Grade1 grade1 = new Grade1(subject, studentRepository1.find(Long.valueOf(i)));
            entityManager.getTransaction().begin();
            entityManager.persist(grade1);
            entityManager.getTransaction().commit();

            Grade2 grade2 = new Grade2(subject, studentRepository1.find(Long.valueOf(i)));
            entityManager.getTransaction().begin();
            entityManager.persist(grade2);
            entityManager.getTransaction().commit();

            Final_grade final_grade = new Final_grade(grade1,grade2);
            entityManager.getTransaction().begin();
            entityManager.persist(final_grade);
            entityManager.getTransaction().commit();

        }
    }

    public Subject find(Long id) {
        return entityManager.find(Subject.class, id);
    }

    public List<Subject> findSubjects(){

        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s from Subject s");
        return query.getResultList();
    }

    public List<Integer> findIds(){

        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.id from Subject s");
        return query.getResultList();
    }

    public List<Subject> findSubjectNames(){

        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.name from Subject s");
        return query.getResultList();
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
