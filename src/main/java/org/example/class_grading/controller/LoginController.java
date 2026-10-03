package org.example.class_grading.controller;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.class_grading.ChangeWindow;
import org.example.class_grading.model.User;
import org.example.class_grading.repository.GradeRepository;
import org.example.class_grading.repository.StudentRepository;
import org.example.class_grading.repository.SubjectRepository;
import org.example.class_grading.repository.UserRepository;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController<event> {

    User user = null;
    private ChangeWindow changeWindow = null; //ablakváltó osztály
    private UserRepository userRepository = new UserRepository();

    /* statikus session-változók (bejelentkezés után minden osztályból láthatóak, kijelentkezéskor
    null értéket vesznek fel: */
    public static String active_username;
    public static String active_role;

//fxml objektumok:
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginField;

    @FXML
    private Label AuthorizationLabel;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SigninButton;

    @FXML
    private Label Title;

    @FXML
    private Button SignupButton;

    @FXML
    private Label message;

    StudentRepository studentRepository = new StudentRepository();
    SubjectRepository subjectRepository = new SubjectRepository();
    GradeRepository gradeRepository = new GradeRepository();

    //metódusok:
    @FXML
    void initialize() {


        SigninButton.setOnAction(event -> {
            String loginText = LoginField.getText().trim();
            String loginPassword = PasswordField.getText().trim();
            System.out.println(loginText);
            System.out.println(loginPassword);

            if (loginText == "" || loginPassword == "") {
                message.setText("Üres mező!");
            } else if (!userRepository.checkByPassword(loginText,loginPassword)) {
                message.setText("Hibás felhasználónév vagy jelszó!" );
            } else  if(userRepository.checkByPassword(loginText,loginPassword)){
                user = userRepository.findByUsername(loginText);
                active_username = user.getUsername();
                active_role = String.valueOf(user.getRole());
                message.setText("Sikeres bejelentkezés! " + active_username + " : " + active_role);
                changeWindow = new ChangeWindow();
                changeWindow.change(SigninButton,"tableview.fxml");
            }
        });


        SignupButton.setOnAction(event -> {
            System.out.println("Clicked");

            changeWindow = new ChangeWindow();
            changeWindow.change(SignupButton,"signUp.fxml");


        });

    }


}