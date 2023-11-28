package hu.unideb.inf.controller;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import hu.unideb.inf.ChangeWindow;
import hu.unideb.inf.model.Student;
import hu.unideb.inf.model.Subject;
import hu.unideb.inf.model.User;
import hu.unideb.inf.repository.StudentRepository;
import hu.unideb.inf.repository.SubjectRepository;
import hu.unideb.inf.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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
                changeWindow.change(SigninButton,"/fxml/tableview.fxml");
            }
        });


        SignupButton.setOnAction(event -> {
            System.out.println("Clicked");

            changeWindow = new ChangeWindow();
            changeWindow.change(SignupButton,"/fxml/signUp.fxml");


        });

    }


}