package hu.unideb.inf.repository;

import hu.unideb.inf.model.Student;
import hu.unideb.inf.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UserRepository {
    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public UserRepository() {
        this.emf = Persistence.createEntityManagerFactory("student_pu");
        this.entityManager = emf.createEntityManager();
    }

    public UserRepository(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = emf.createEntityManager();
    }

    public User add(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public User findByUsername(String username){
        Query query = entityManager.createQuery("select s from User s where s.username = '" + username + "'");
        return (User) query.getSingleResult();
    }

    public long countByUsername(String username){
        Query query = entityManager.createNamedQuery("count by username");
        query.setParameter("username", username);
        return (Long) query.getSingleResult();
    }

    //Igaz értéket ad vissza, ha nincs ilyen felhasználó (tehát létrehozható)
    public boolean checkByUsername(String username){
        if (countByUsername(username) == 0)
        return true;
        else return false;

    }

    //Igaz értéket ad vissza, ha létezik ilyen felhasználó és egyezik a jelszó
    public boolean checkByPassword(String username, String password){
        if (countByUsername(username) == 0 )
            return false;
        else {
            User user;
            user = findByUsername(username);
            if (password.equals(user.getPassword()))
            {
                return true;
             } else {
                return false;
            }
        }
    }

    //felhasználó adatainak frissítése
    public void updateByUsername(User user, String username) {
        User user1 = findByUsername(username);
        Long id = user1.getId();
        int rolenumber = 0;
        User.Role role = user1.getRole();
        switch (role){
            case visitor:
                rolenumber=0;
                break;
            case parent:
                rolenumber=1;
                break;
            case teacher:
                rolenumber=2;
                break;
            case classhead:
                rolenumber=3;
                break;
            case admin:
                rolenumber=4;
                break;
            default:
        }
        entityManager.getTransaction().begin();
        Query[] query = new Query[6];

        query[0] = entityManager.createQuery("Update User set firstname = '" + user.getFirstname() + "' where id = " + id);
        query[1] = entityManager.createQuery("Update User set lastname = '" + user.getLastname() + "' where id = " + id);
        query[2] = entityManager.createQuery("Update User set username = '" + user.getUsername() + "' where id = " + id);
        query[3] = entityManager.createQuery("Update User set password = '" + user.getPassword() + "' where id = " + id);
        query[4] = entityManager.createQuery("Update User set accomodation = '" + user.getAccomodation() + "' where id = " + id);
        query[5] = entityManager.createQuery("Update User set gender = '" + user.getGender() + "' where id = " + id);

        for (int i=0; i<6; i++) {
            query[i].executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }

    public void updateRole(User.Role role,String username){
        User user = findByUsername(username);
        Long id = user.getId();
        int rolenumber = 0;
        switch (role){
            case visitor:
                rolenumber=0;
                break;
            case parent:
                rolenumber=1;
                break;
            case teacher:
                rolenumber=2;
                break;
            case classhead:
                rolenumber=3;
                break;
            case admin:
                rolenumber=4;
                break;
            default:
        }
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update User set role = '" + rolenumber + "' where id = " + id);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }


    public void delete(User user) {
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
