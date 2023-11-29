package hu.unideb.inf.repository;

import hu.unideb.inf.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.security.auth.Subject;
import java.util.List;

public class StudentRepository {
    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public StudentRepository() {
        this.emf = Persistence.createEntityManagerFactory("student_pu");
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

    public void add(Student student) {
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        /* Célszerű megoldani, hogy új tanuló hozzáadásakor a úgy jegybejegyzés is megjelenjen
        ezzel a tanulóval mindegyik tantárgyra nézve, így le kérjük a tantárgyak listáját:
         */
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

            Final_grade final_grade = new Final_grade(grade1,grade2);
            entityManager.getTransaction().begin();
            entityManager.persist(final_grade);
            entityManager.getTransaction().commit();
        }


    }


    public Student find(Long id) {
        return entityManager.find(Student.class, id);
    }

    //Frissítés, adott tanuló adatainak frissítése
    public Student update(Student student) {
        Student studentToUpdate  = find(student.getId());
        entityManager.getTransaction().begin();
        studentToUpdate.setName(student.getName());
        studentToUpdate.setBirth(student.getBirth());
        studentToUpdate.setAddress(student.getAddress());
        studentToUpdate.setEmail(student.getEmail());

        entityManager.getTransaction().commit();
        entityManager.clear();
        return studentToUpdate;
    }

    //függvények a tanulók adatlistáihoz (a táblázat oszlopainak szerkesztéséhez)
    public List<Integer> findIds(){
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

    public Student findByStudentemail(String email){
        Query query = entityManager.createQuery("select s from Student s where s.email = '" + email + "'");
        return (Student) query.getSingleResult();
    }

    public List<Student> findStudents() {
        Query query = entityManager.createQuery("Select s from Student s");
        return query.getResultList();
    }

    public long count(){
        Query query = entityManager.createQuery("SELECT count(s) from Student s");
        return (Long) query.getSingleResult();
    }

    //tanuló törlése a táblából:

    public void delete(Student student) {
        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }



}
