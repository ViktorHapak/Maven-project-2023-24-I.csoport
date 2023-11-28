package hu.unideb.inf.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import hu.unideb.inf.ChangeWindow;
import hu.unideb.inf.model.Grade1;
import hu.unideb.inf.model.Grade2;
import hu.unideb.inf.repository.GradeRepository;
import hu.unideb.inf.repository.StudentRepository;
import hu.unideb.inf.repository.SubjectRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class GradesController {

    @FXML
    private TableView<Grade1> gradesTable;

    @FXML
    private TableColumn<Grade1, String> idCol;

    @FXML
    private TableColumn<Grade1, String> nameCol;

    @FXML
    private TableColumn<Grade1, String> module1Col;

    @FXML
    private TableColumn<Grade1, String> moduletest1Col;

    @FXML
    private TableColumn<Grade1, String> module2Col;

    @FXML
    private TableColumn<Grade1, String> moduletest2Col;

    @FXML
    private TableColumn<Grade1, String> module3Col;

    @FXML
    private TableColumn<Grade1, String> moduletest3Col;

    @FXML
    private TableColumn<Grade1, String> semesterCol;

    @FXML
    private HBox teacherLine;

    @FXML
    private ComboBox selectSemester;

    @FXML
    private ComboBox selectSubject;

    @FXML
    private ComboBox selectStudent;

    @FXML
    private ComboBox selectModule;

    @FXML
    private ComboBox selectGrade;

    @FXML
    private Label message;

    @FXML
    private FontAwesomeIcon outIcon;

    @FXML
    private FontAwesomeIcon printIcon;

    @FXML
    private FontAwesomeIcon semesterIcon;

    @FXML
    private Button loadButton;

    @FXML
    private Button schoolyearButton;

    @FXML
    private Button deleteAllButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label roleLabel;

    ChangeWindow changeWindow = null;
    ObservableList<Grade1> SemesterList = FXCollections.observableArrayList();
    StudentRepository studentRepository = new StudentRepository();
    SubjectRepository subjectRepository = new SubjectRepository();
    GradeRepository gradeRepository = new GradeRepository();

    //metódusok:
    @FXML
    void initialize() {
        usernameLabel.setText(LoginController.active_username);
        roleLabel.setText(LoginController.active_role);


        selectGrade.getItems().addAll(1,2,3,4,5);
        selectSemester.getItems().addAll("I.","II.");
        selectModule.getItems().addAll(1,2,3);
        selectStudent.getItems().addAll(studentRepository.findNames());
        selectSubject.getItems().addAll(subjectRepository.findSubjectNames());



        outIcon.setOnMouseClicked(mouseEvent -> {
            changeWindow = new ChangeWindow();
            studentRepository.close();
            gradeRepository.close();
            changeWindow.changeOnIcon(outIcon,"/fxml/tableview.fxml");

        });

        //Megadott félévre adott tantárgy érdemjegylistájának beolvasása
        loadButton.setOnAction(event -> {
            if(selectSemester.getValue() == null || selectSubject.getValue() == null ) {
                message.setText("Kérem válassza ki a félévet és a tantárgyat!");
            } else {
                load();

            }
        });

        //félévi átlag kiszámítása:
        semesterIcon.setOnMouseClicked(mouseEvent -> {
            if (selectSemester.getValue() == null || selectSubject.getValue() == null
                    || selectStudent.getValue() == null) {
                message.setText("Átlag számításához nem hagyhatsz üresen mezőt!");
            } else {

                String messageText = gradeRepository.calculateSemester(
                        (String) selectSemester.getValue(),
                        (String) selectSubject.getValue(),
                        (String) selectStudent.getValue());
                message.setText(messageText);
            }

        });
    }

    @FXML
    void addGrade(MouseEvent event) {
        if (selectSemester.getValue() == null || selectSubject.getValue() == null
        || selectSubject.getValue() == null || selectModule.getValue() == null ||
        selectGrade.getValue() == null) {
            message.setText("Érdemjegy beírásához nem hagyhatsz üresen mezőt!");
        } else {
            message.setText("");
            gradeRepository.addGrade((String) selectSemester.getValue(),
                    (String) selectSubject.getValue(),
                    (String) selectStudent.getValue(),
                    (Integer) selectModule.getValue(),
                    (Integer) selectGrade.getValue());
        }

    }

    @FXML
    void calculateModule(MouseEvent event) {
        if (selectSemester.getValue() == null || selectSubject.getValue() == null
                || selectSubject.getValue() == null || selectModule.getValue() == null) {
            message.setText("Tz. számításához több adatra van szükség!");
        } else {
            message.setText("");
            gradeRepository.calculateModule((String) selectSemester.getValue(),
                    (String) selectSubject.getValue(),
                    (String) selectStudent.getValue(),
                    (Integer) selectModule.getValue());
        }

    }

    @FXML
    void deleteGrade(MouseEvent event) {
        if (selectSemester.getValue() == null || selectSubject.getValue() == null
                || selectSubject.getValue() == null || selectModule.getValue() == null) {
            message.setText("Érdemjegy törléséhez nem hagyhatsz üresen mezőt!");
        } else {
            message.setText("");
            gradeRepository.deleteGrade((String) selectSemester.getValue(),
                    (String) selectSubject.getValue(),
                    (String) selectStudent.getValue(),
                    (Integer) selectModule.getValue());
        }

    }

    @FXML
    void refreshTable(MouseEvent event) {
            load();
    }

    void load(){
        message.setText("");
        SemesterList.clear();


        int count1 =  (int) gradeRepository.count1();
        int count2 =  (int) gradeRepository.count2();

        String semester = (String) selectSemester.getValue();
        switch (semester) {
            case "I.":
                int I=0;
                String[] idnumber = new String[count1];
                while (I<count1) {
                    idnumber[I] = String.valueOf(gradeRepository.findIds1().get(I));
                    I++;
                }

                I=1;

                for(String i :idnumber) {
                    Grade1 grade1 = gradeRepository.find1(Long.valueOf(i));
                    System.out.println(grade1.toString());
                    if(grade1.getSubject().getName()==selectSubject.getValue()) {
                        System.out.println(grade1.toString());
                        SemesterList.add(new Grade1(
                                (long) I,
                                grade1.getStudent().getName(),
                                grade1.getModulegrades1(),
                                grade1.getModule1(),
                                grade1.getModulegrades2(),
                                grade1.getModule2(),
                                grade1.getModulegrades3(),
                                grade1.getModule3(),
                                grade1.getSemester()
                        ));
                        I++;
                    }
                }
                gradesTable.setItems(SemesterList);
                idCol.setCellValueFactory(new PropertyValueFactory("id"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                module1Col.setCellValueFactory(new PropertyValueFactory<>("modulegrades1"));
                moduletest1Col.setCellValueFactory(new PropertyValueFactory<>("module1"));
                module2Col.setCellValueFactory(new PropertyValueFactory<>("modulegrades2"));
                moduletest2Col.setCellValueFactory(new PropertyValueFactory<>("module2"));
                module3Col.setCellValueFactory(new PropertyValueFactory<>("modulegrades3"));
                moduletest3Col.setCellValueFactory(new PropertyValueFactory<>("module3"));
                semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
                break;

            case "II.":
                I=0;
                idnumber = new String[count2];
                while (I<count2) {
                    idnumber[I] = String.valueOf(gradeRepository.findIds2().get(I));
                    I++;
                }

                I=1;

                for(String i :idnumber) {
                    Grade2 grade2 = gradeRepository.find2(Long.valueOf(i));
                    if(grade2.getSubject().getName()==selectSubject.getValue()) {
                        SemesterList.add(new Grade1(
                                (long) I,
                                grade2.getStudent().getName(),
                                grade2.getModulegrades1(),
                                grade2.getModule1(),
                                grade2.getModulegrades2(),
                                grade2.getModule2(),
                                grade2.getModulegrades3(),
                                grade2.getModule3(),
                                grade2.getSemester()
                        ));
                        I++;
                    }
                }
                gradesTable.setItems(SemesterList);
                idCol.setCellValueFactory(new PropertyValueFactory("id"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                module1Col.setCellValueFactory(new PropertyValueFactory<>("modulegrades1"));
                moduletest1Col.setCellValueFactory(new PropertyValueFactory<>("module1"));
                module2Col.setCellValueFactory(new PropertyValueFactory<>("modulegrades2"));
                moduletest2Col.setCellValueFactory(new PropertyValueFactory<>("module2"));
                module3Col.setCellValueFactory(new PropertyValueFactory<>("modulegrades3"));
                moduletest3Col.setCellValueFactory(new PropertyValueFactory<>("module3"));
                semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
                break;
            default:
        }

    }

}

