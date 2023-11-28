package hu.unideb.inf.controller;

import hu.unideb.inf.ChangeWindow;
import hu.unideb.inf.model.User;
import hu.unideb.inf.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController {

    private ChangeWindow changeWindow = null; //ablakváltó osztály
    UserRepository userrepository = new UserRepository();

    @FXML
    private TextField signupLogin;

    @FXML
    private PasswordField signupPassword;

    @FXML
    private Button signupButton;

    @FXML
    private TextField signupLastname;

    @FXML
    private TextField signupName;

    @FXML
    private TextField signupLocation;

    @FXML
    private Label comment;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton signUpMale;

    @FXML
    private RadioButton signUpFemale;

    @FXML
    private Label Title;

    @FXML
    private Button loginButton;

    @FXML
     void initialize() {
        signupButton.setOnAction(event -> {
            String firstName = signupName.getText().trim();
            String lastName = signupLastname.getText().trim();
            String userName = signupLogin.getText().trim();
            String password = signupPassword.getText().trim();
            String accomodation = signupLocation.getText();
            String gender = "";
            if (signUpMale.isSelected()) gender ="Male";
            else gender = "Female";

            if (firstName.equals("") || lastName.equals("") || userName.equals("")
                    || password.equals("") || accomodation.equals("")) {
                comment.setText("Hiányos regisztráció!");
            }  else if (!userrepository.checkByUsername(userName)) {
                comment.setText("Használt felhasználónév!");
            } else {
                User user = new User(firstName, lastName, userName, password, accomodation, gender);

                userrepository.add(user);
                changeWindow = new ChangeWindow();
                changeWindow.change(signupButton, "/fxml/login.fxml"); //oldal visszaváltás
                userrepository.close();
            }
        });

        loginButton.setOnAction(event -> {
            changeWindow = new ChangeWindow();
            changeWindow.change(loginButton, "/fxml/login.fxml");
            userrepository.close();
        });



        }
    }


