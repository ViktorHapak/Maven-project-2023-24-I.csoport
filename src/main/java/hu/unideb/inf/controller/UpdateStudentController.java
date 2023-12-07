package hu.unideb.inf.controller;

import hu.unideb.inf.ChangeWindow;
import hu.unideb.inf.model.Student;
import hu.unideb.inf.repository.StudentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UpdateStudentController {

    private ChangeWindow changeWindow = null;
    private final StudentRepository studentRepository = new StudentRepository() ;
    private Student currentStudent = new Student();

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthField ;

    @FXML
    private TextField addressField ;

    @FXML
    private TextField emailField ;

    @FXML
    private Button updateButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label comment;



    public void setCurrentStudent(Student student) {
        currentStudent.setId(student.getId());
        currentStudent.setName(student.getName());
        currentStudent.setBirth(student.getBirth());
        currentStudent.setAddress(student.getAddress());
        currentStudent.setEmail(student.getEmail());
    }

    @FXML
    void initialize() {



        updateButton.setOnAction(event -> {

            String name = nameField.getText();
            String birth = birthField.getText();
            String address = addressField.getText();
            String email = emailField.getText();

            if (name.equals("") || birth.equals("") || address.equals("") || email.equals("")) {
                comment.setText("Töltsön ki minden mezőt!");
            } else {
                // Az adatok módosítása az aktuális diáknál
                currentStudent.setName(name);
                currentStudent.setBirth(LocalDate.parse(birth));
                currentStudent.setAddress(address);
                currentStudent.setEmail(email);
                System.out.println(currentStudent.getId());
                System.out.println(currentStudent.getName());
                // Adatbázis frissítése
                studentRepository.update(currentStudent);

                // Ablak váltása a táblázatos nézetre
                changeWindow = new ChangeWindow();
                changeWindow.change(updateButton, "/fxml/tableview.fxml");
                studentRepository.close();
            }
        });

        exitButton.setOnAction(event -> {
            changeWindow = new ChangeWindow();
            changeWindow.change(exitButton, "/fxml/tableview.fxml");
            studentRepository.close();
        });
    }
}
/**/