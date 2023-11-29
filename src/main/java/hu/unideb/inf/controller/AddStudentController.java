package hu.unideb.inf.controller;

import hu.unideb.inf.ChangeWindow;
import hu.unideb.inf.model.Student;
import hu.unideb.inf.model.User;
import hu.unideb.inf.repository.StudentRepository;
import hu.unideb.inf.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddStudentController {
    private ChangeWindow changeWindow = null; //ablakváltó osztály
    StudentRepository studentRepository = new StudentRepository();

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private Button addButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label comment;

    @FXML
    void initialize() {
        addButton.setOnAction(event ->  {String name = nameField.getText();
        String birth = birthField.getText();
        String address = addressField.getText();
        String email = emailField.getText();


        if (name.equals("") || birth.equals("") || address.equals("")
                || email.equals("")) {
            comment.setText("Hibás adat");
        }

        else {

            Student student = new Student(name, LocalDate.parse(birth), address, email);


            studentRepository.add(student);
            changeWindow = new ChangeWindow();
            changeWindow.change(addButton, "/fxml/tableview.fxml");
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