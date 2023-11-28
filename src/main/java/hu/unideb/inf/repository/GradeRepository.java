package hu.unideb.inf.repository;

import hu.unideb.inf.model.Grade1;
import hu.unideb.inf.model.Grade2;
import hu.unideb.inf.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GradeRepository {

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public GradeRepository() {
        this.emf = Persistence.createEntityManagerFactory("student_pu");
        this.entityManager = emf.createEntityManager();
    }

    public GradeRepository(String pu) {
        this.emf = Persistence.createEntityManagerFactory(pu);
        this.entityManager = emf.createEntityManager();
    }

    public List<Grade1> findbySubjects1(String subject){
        Query query = entityManager.createQuery("Select s from Grade1 s where s.subject.name ='" + subject + "'");
        return query.getResultList();
    }

    public List<Grade2> findbySubjects2(String subject){
        Query query = entityManager.createQuery("Select s from Grade2 s where s.subject.name ='" + subject + "'");
        return query.getResultList();
    }

    public List<Long> findIds1(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.id from Grade1 s");
        return query.getResultList();
    }

    public List<Long> findIds2(){
        //Write the output-list in lines
        Query query = entityManager.createQuery("Select s.id from Grade2 s");
        return query.getResultList();
    }

    public Grade1 find1(Long id) {
        return entityManager.find(Grade1.class, id);
    }

    public Grade2 find2(Long id) {
        return entityManager.find(Grade2.class, id);
    }

    public long count1(){
        Query query = entityManager.createQuery("Select count(s) from Grade1 s");
        return (Long) query.getSingleResult();
    }

    public long count2(){
        Query query = entityManager.createQuery("Select count(s) from Grade2 s");
        return (Long) query.getSingleResult();
    }

    //Érdemjegy-bejegyzés megkeresése adott tantárgyra és tanulóra( 2 félévre külön):

    public Grade1 findbyEntered1(String subjectname, String name){
        Long id = null;
        int i = 0;
        String[] idnumber = new String[(int) count1()];
        while (i < (int) count1()) {
            idnumber[i] = String.valueOf(findIds1().get(i));
            i++;
        }
        for (String I : idnumber) {
            Grade1 grade1 = find1(Long.valueOf(I));
            if (grade1.getSubject().getName().equals(subjectname) &&
                    grade1.getStudent().getName().equals(name)) {
                id = grade1.getId();
            }
        }

        Grade1 grade1 = find1(id);
        return grade1;
    }

    public Grade2 findbyEntered2(String subjectname, String name){
        Long id = null;
        int i = 0;
        String[] idnumber = new String[(int) count2()];
        while (i < (int) count2()) {
            idnumber[i] = String.valueOf(findIds2().get(i));
            i++;
        }
        for (String I : idnumber) {
            Grade2 grade2 = find2(Long.valueOf(I));
            if (grade2.getSubject().getName().equals(subjectname) &&
                    grade2.getStudent().getName().equals(name)) {
                id = grade2.getId();
            }
        }

        Grade2 grade2 = find2(id);
        return grade2;
    }

    /*Érdemjegy hozzáadása a megfelelő tanuló számára a megfelelő tárgyból
    a megfelelő modulusba:*/
    public void addGrade(String semester, String subjectname, String name,
                         int module, int mark) {
        String module1 = null;
        String module2 = null;
        String module3 = null;
        Long id = null;

        if (semester.equals("I.")) {
            int i = 0;
            String[] idnumber = new String[(int) count1()];
            while (i < (int) count1()) {
                idnumber[i] = String.valueOf(findIds1().get(i));
                i++;
            }
            for (String I : idnumber) {
                Grade1 grade1 = find1(Long.valueOf(I));
                if (grade1.getSubject().getName().equals(subjectname) &&
                        grade1.getStudent().getName().equals(name)) {
                    module1 = grade1.getModulegrades1();
                    module2 = grade1.getModulegrades2();
                    module3 = grade1.getModulegrades3();
                    id = grade1.getId();
                }
            }

            Grade1 grade1 = find1(id);

            switch (module) {
                case 1:
                    if (module1 == null || module1.equals("")) module1 = "" + mark;
                    else module1 = module1 + " " + mark;
                    grade1.setModulegrades1(module1);
                    updateGradesById1(grade1,id);
                    break;
                case 2:
                    if (module2 == null || module2.equals("")) module2 = "" + mark;
                    else module2 = module2 + " " + mark;
                    grade1.setModulegrades2(module2);
                    updateGradesById1(grade1,id);
                    break;
                case 3:
                    if (module3 == null || module3.equals("")) module3 = "" + mark;
                    else module3 = module3 + " " + mark;
                    grade1.setModulegrades3(module3);
                    updateGradesById1(grade1,id);
                    break;
                default:
            }
        } else {
            int i = 0;
            String[] idnumber = new String[(int) count2()];
            while (i < (int) count2()) {
                idnumber[i] = String.valueOf(findIds2().get(i));
                i++;
            }
            for (String I : idnumber) {
                Grade2 grade2 = find2(Long.valueOf(I));
                if (grade2.getSubject().getName().equals(subjectname) &&
                        grade2.getStudent().getName().equals(name)) {
                    module1 = grade2.getModulegrades1();
                    module2 = grade2.getModulegrades2();
                    module3 = grade2.getModulegrades3();
                    id = grade2.getId();
                }
            }

            Grade2 grade2 = find2(id);

            switch (module) {
                case 1:
                    if (module1 == null || module1.equals("")) module1 = "" + mark;
                    else module1 = module1 + " " + mark;
                    grade2.setModulegrades1(module1);
                    updateGradesById2(grade2,id);
                    break;
                case 2:
                    if (module2 == null || module2.equals("")) module2 = "" + mark;
                    else module2 = module2 + " " + mark;
                    grade2.setModulegrades2(module2);
                    updateGradesById2(grade2,id);
                    break;
                case 3:
                    if (module3 == null || module3.equals("")) module3 = "" + mark;
                    else module3 = module3 + " " + mark;
                    grade2.setModulegrades3(module3);
                    updateGradesById2(grade2,id);
                    break;
                default:

            }
        }
    }

    public void deleteGrade(String semester, String subjectname, String name,
                         int module) {
        String module1 = null;
        String module2 = null;
        String module3 = null;
        Grade1 grade1 = findbyEntered1(subjectname,name);
        Grade2 grade2 = findbyEntered2(subjectname,name);

        if (semester.equals("I.")) {
            Long id = grade1.getId();
            module1 = grade1.getModulegrades1();
            module2 = grade1.getModulegrades2();
            module3 = grade1.getModulegrades3();
            List<String> module1List = null;
            List<String> module2List = null;
            List<String> module3List = null;
            if(module1 != null) {
                module1List = new ArrayList<String>(Arrays.asList(module1.split(" ")));
            } if(module2 != null) {
                module2List = new ArrayList<String>(Arrays.asList(module2.split(" ")));
            } if (module3 !=null) {
                module3List = new ArrayList<String>(Arrays.asList(module3.split(" ")));
            }

            switch (module){
                case 1:
                    if (module1List !=null) {
                    module1List.remove(module1List.size()-1);
                    module1 = String.join(" ",module1List);
                    grade1.setModulegrades1(module1);
                    updateGradesById1(grade1,id);}
                    break;
                case 2:
                    if (module2List !=null) {
                    module2List.remove(module2List.size()-1);
                    module2 = String.join(" ",module2List);
                    grade1.setModulegrades2(module2);
                    updateGradesById1(grade1,id);}
                    break;
                case 3:
                    if (module3List !=null) {
                    module3List.remove(module3List.size()-1);
                    module3 = String.join(" ",module3List);
                    grade1.setModulegrades3(module3);
                    updateGradesById1(grade1,id);}
                    break;
                default:

            }


        } else {
            Long id = grade2.getId();
            module1 = grade2.getModulegrades1();
            module2 = grade2.getModulegrades2();
            module3 = grade2.getModulegrades3();
            List<String> module1List = null;
            List<String> module2List = null;
            List<String> module3List = null;
            if(module1 != null) {
                module1List = new ArrayList<String>(Arrays.asList(module1.split(" ")));
            } if(module2 != null) {
                module2List = new ArrayList<String>(Arrays.asList(module2.split(" ")));
            } if (module3 !=null) {
                module3List = new ArrayList<String>(Arrays.asList(module3.split(" ")));
            }

            switch (module){
                case 1:
                    if (module1List !=null) {
                    module1List.remove(module1List.size()-1);
                    module1 = String.join(" ",module1List);
                    grade2.setModulegrades1(module1);
                    updateGradesById2(grade2,id);}
                    break;
                case 2:
                    if (module2List !=null) {
                    module2List.remove(module2List.size()-1);
                    module2 = String.join(" ",module2List);
                    grade2.setModulegrades2(module2);
                    updateGradesById2(grade2,id);}
                    break;
                case 3:
                    if(module3List !=null){
                    module3List.remove(module3List.size()-1);
                    module3 = String.join(" ",module3List);
                    grade2.setModulegrades3(module3);
                    updateGradesById2(grade2,id);}
                    break;
                default:

            }

        }

    }

    public void calculateModule(String semester, String subjectname, String name,
                                int module){
        String module1 = null;
        String module2 = null;
        String module3 = null;
        Grade1 grade1 = findbyEntered1(subjectname,name);
        Grade2 grade2 = findbyEntered2(subjectname,name);
        if(semester.equals("I.")){
            Long id = grade1.getId();
            module1 = grade1.getModulegrades1();
            module2 = grade1.getModulegrades2();
            module3 = grade1.getModulegrades3();
            List<String> module1List = null;
            List<String> module2List = null;
            List<String> module3List = null;
            if(module1 != null) {
                module1List = new ArrayList<String>(Arrays.asList(module1.split(" ")));
            } if(module2 != null) {
                module2List = new ArrayList<String>(Arrays.asList(module2.split(" ")));
            } if (module3 !=null) {
                module3List = new ArrayList<String>(Arrays.asList(module3.split(" ")));
            }

            switch (module){
                case 1:
                    if (module1 != null && !module1.equals("") ){
                    int sum = 0; int avarage; int db=0; double q;
                    for(int i =0; i<module1List.size();i++){
                        sum = sum + Integer.parseInt( module1List.get(i));
                        db++;
                    }
                    q = sum/db;
                    avarage = (int) Math.round(q);
                    grade1.setModule1(avarage);}
                    else grade1.setModule1(null);
                    updateModulesById1(grade1,id);
                    break;
                case 2:
                    if (module2 != null && !module2.equals("")){
                        int sum = 0; int avarage; int db=0; double q;
                        for(int i =0; i<module2List.size();i++){
                            sum = sum + Integer.parseInt( module2List.get(i));
                            db++;
                        }
                        q = sum/db;
                        avarage = (int) Math.round(q);
                        grade1.setModule2(avarage);}
                        else  grade1.setModule2(null);
                        updateModulesById1(grade1,id);
                    break;
                case 3:
                    if (module3 != null && !module3.equals("")){
                        int sum = 0; int avarage; int db=0; double q;
                        for(int i =0; i<module3List.size();i++){
                            sum = sum + Integer.parseInt( module3List.get(i));
                            db++;
                        }
                        q = sum/db;
                        avarage = (int) Math.round(q);
                        grade1.setModule3(avarage);}
                        else grade1.setModule3(null);
                        updateModulesById1(grade1,id);
                    break;
                default:
            }
        }else {
            Long id = grade2.getId();
            module1 = grade2.getModulegrades1();
            module2 = grade2.getModulegrades2();
            module3 = grade2.getModulegrades3();
            List<String> module1List = null;
            List<String> module2List = null;
            List<String> module3List = null;
            if(module1 != null) {
                module1List = new ArrayList<String>(Arrays.asList(module1.split(" ")));
            } if(module2 != null) {
                module2List = new ArrayList<String>(Arrays.asList(module2.split(" ")));
            } if (module3 !=null) {
                module3List = new ArrayList<String>(Arrays.asList(module3.split(" ")));
            }

            switch (module){
                case 1:
                    if (module1 != null && !module1.equals("")){
                        int sum = 0; int avarage; int db=0; double q;
                        for(int i =0; i<module1List.size();i++){
                            sum = sum + Integer.parseInt( module1List.get(i));
                        }
                        q = sum/db;
                        avarage = (int) Math.round(q);
                        grade2.setModule1(avarage);}
                        else grade2.setModule1(null);
                        updateModulesById2(grade2,id);
                    break;
                case 2:
                    if (module2 != null && !module2.equals("")){
                        int sum = 0; int avarage; int db=0; double q;
                        for(int i =0; i<module2List.size();i++){
                            sum = sum + Integer.parseInt( module2List.get(i));
                        }
                        q = sum/db;
                        avarage = (int) Math.round(q);
                        grade2.setModule2(avarage);}
                        else grade2.setModule2(null);
                        updateModulesById2(grade2,id);
                    break;
                case 3:
                    if (module3!= null && !module3.equals("")){
                        int sum = 0; int avarage; int db = 0; double q;
                        for(int i =0; i<module3List.size();i++){
                            sum = sum + Integer.parseInt( module3List.get(i));
                        }
                        q = sum/db;
                        avarage = (int) Math.round(q);
                        grade2.setModule3(avarage);}
                        else grade2.setModule3(null);
                        updateModulesById2(grade2,id);
                    break;
                default:
            }
        }
    }

    public String calculateSemester(String semester, String subjectname, String name) {
        Grade1 grade1 = findbyEntered1(subjectname,name);
        Grade2 grade2 = findbyEntered2(subjectname,name);
        int module1; int module2; int module3;
        if(semester.equals("I.")) {
            Long id = grade1.getId();
            if (grade1.getModule1() !=null) {
                module1 = grade1.getModule1();
            } else {module1 = 0;}
            if (grade1.getModule2() !=null) {
                module2 = grade1.getModule2();
            } else {module2 = 0;}
            if (grade1.getModule3() !=null) {
                module3 = grade1.getModule3();
            } else {module3 = 0;}
            if (module1!= 0 && module2 !=0 && module3 != 0) {
                int sum = module1+module2+module3;
                double q = sum/3;
                int avarage = (int) Math.round(q);
                grade1.setSemester(avarage);
                updateSemesterById1(grade1,id);
                return "";


            } else return "A féléves átlagszámításhoz minden tz. szükséges!";

        } else {
            Long id = grade2.getId();
            if (grade2.getModule1() !=null) {
                module1 = grade2.getModule1();
            } else {module1 = 0;}
            if (grade2.getModule2() !=null) {
                module2 = grade2.getModule2();
            } else {module2 = 0;}
            if (grade2.getModule3() !=null) {
                module3 = grade2.getModule3();
            } else {module3 = 0;}
            if (module1!= 0 && module2 !=0 && module3 != 0) {
                int sum = module1+module2+module3;
                double q = sum/3;
                int avarage = (int) Math.round(q);
                grade1.setSemester(avarage);
                updateSemesterById2(grade2,id);
                return "";


            } else return "A féléves átlagszámításhoz minden tz. szükséges!";

        }

    }



    public void updateGradesById1(Grade1 grade1, Long id) {
        entityManager.getTransaction().begin();
        Query[] query = new Query[3];
        query[0] = entityManager.createQuery("Update Grade1 set modulegrades1 = '"+ grade1.getModulegrades1() + "' where id = " + id );
        query[1] = entityManager.createQuery("Update Grade1 set modulegrades2 = '"+ grade1.getModulegrades2() + "' where id = " + id );
        query[2] = entityManager.createQuery("Update Grade1 set modulegrades3 = '"+ grade1.getModulegrades3() + "' where id = " + id );
        for (int i=0; i<3; i++) {
            query[i].executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }



    public void updateGradesById2(Grade2 grade2, Long id) {
        entityManager.getTransaction().begin();
        Query[] query = new Query[3];
        query[0] = entityManager.createQuery("Update Grade2 set modulegrades1 = '"+ grade2.getModulegrades1() + "' where id = " + id );
        query[1] = entityManager.createQuery("Update Grade2 set modulegrades2 = '"+ grade2.getModulegrades2() + "' where id = " + id );
        query[2] = entityManager.createQuery("Update Grade2 set modulegrades3 = '"+ grade2.getModulegrades3() + "' where id = " + id );
        for (int i=0; i<3; i++) {
            query[i].executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
        }

    }

    public void updateModulesById1(Grade1 grade1, Long id) {
        entityManager.getTransaction().begin();
        Query[] query = new Query[3];
        query[0] = entityManager.createQuery("Update Grade1 set module1 = '"+ grade1.getModule1() + "' where id = " + id );
        query[1] = entityManager.createQuery("Update Grade1 set module2 = '"+ grade1.getModule2() + "' where id = " + id );
        query[2] = entityManager.createQuery("Update Grade1 set module3 = '"+ grade1.getModule3() + "' where id = " + id );
        for (int i=0; i<3; i++) {
            query[i].executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
        }
    }



    public void updateModulesById2(Grade2 grade2, Long id) {
        entityManager.getTransaction().begin();
        Query[] query = new Query[3];
        query[0] = entityManager.createQuery("Update Grade1 set module1 = '"+ grade2.getModule1() + "' where id = " + id );
        query[1] = entityManager.createQuery("Update Grade1 set module2 = '"+ grade2.getModule2() + "' where id = " + id );
        query[2] = entityManager.createQuery("Update Grade1 set module3 = '"+ grade2.getModule3() + "' where id = " + id );
        for (int i=0; i<3; i++) {
            query[i].executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.clear();
        }

    }

    public void updateSemesterById1(Grade1 grade1, Long id) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update Grade1 set semester = '"+ grade1.getSemester() + "' where id = " + id );
        entityManager.getTransaction().commit();
        entityManager.clear();

    }

    public void updateSemesterById2(Grade2 grade2, Long id) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Update Grade2 set semester = '"+ grade2.getSemester() + "' where id = " + id );
        entityManager.getTransaction().commit();
        entityManager.clear();

    }


    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
