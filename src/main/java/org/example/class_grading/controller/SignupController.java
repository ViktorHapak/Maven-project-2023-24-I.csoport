package org.example.class_grading.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.class_grading.ChangeWindow;
import org.example.class_grading.model.User;
import org.example.class_grading.repository.UserRepository;

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
                changeWindow.change(signupButton, "login.fxml"); //oldal visszaváltás
                userrepository.close();
            }
        });

        loginButton.setOnAction(event -> {
            changeWindow = new ChangeWindow();
            changeWindow.change(loginButton, "login.fxml");
            userrepository.close();
        });



        }
    }


