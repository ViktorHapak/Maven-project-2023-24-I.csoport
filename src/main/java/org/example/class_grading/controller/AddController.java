package org.example.class_grading.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.class_grading.model.Student;
import org.example.class_grading.model.User;
import org.example.class_grading.repository.StudentRepository;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;

public class AddController {

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private Label messageName;

    @FXML
    private Label messageDate;

    @FXML
    private Label messageAddress;

    @FXML
    private Label messageEmail;

    @FXML
    private Label messageField;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    StudentRepository studentRepository = new StudentRepository();

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        clearMessage();

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            clearMessage();
        });

        dateField.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            clearMessage();
        });

        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            clearMessage();
        });

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            clearMessage();
        });



    }


    @FXML
    void addStudent(MouseEvent event) {
        clearMessage();
        if (!hasRole()) {
            nameField.setText("");
            dateField.setValue(null);
            addressField.setText("");
            emailField.setText("");
            messageField.setVisible(true);
            messageField.setText("A művelethez nincs jogosultsága!");
        }
        else if ((nameField.getText().trim().equals("")) || (dateField.getEditor().getText().equals("")) ||
                (addressField.getText().trim().equals("")) || (emailField.getText().trim().equals(""))) {
            if (nameField.getText().trim().equals("")) messageName.setText("Üres mező!");
            if (dateField.getEditor().getText().equals("")) messageDate.setText("Üres mező!");
            if (addressField.getText().trim().equals("")) messageAddress.setText("Üres mező!");
            if (emailField.getText().trim().equals("")) messageEmail.setText("Üres mező!");
        } else if (!emailField.getText().trim().contains("@")) {
            emailField.setText("");
            messageEmail.setText("Tartalmaznia kell @-t");
        } else if (!studentRepository.checkByName(nameField.getText())){
            nameField.setText("");
            messageName.setText("Már létezik ilyen nevű diák!");
        }
        else {
            String name = nameField.getText();

            LocalDate date;
            try {
                date = dateparser(dateField.getEditor().getText());
                String address = addressField.getText();
                String email = emailField.getText().trim();
                Student student = new Student(name, date, address, email);
                studentRepository.add(student);
                studentRepository.close();
                saveButton.getScene().getWindow().hide();
            } catch (DateTimeException e1) {
                    dateField.getEditor().setText("");
                    messageDate.setText("Hibás formátum!");
                    return;
            } catch (NumberFormatException e2) {
                    dateField.getEditor().setText("");
                    messageDate.setText("Hibás formátum!");
                    return;

            }

        }
    }

    private LocalDate dateparser(String dateString) {
        LocalDate localDate ;
        String[] dateStringArray = dateString.split(". ");
        int year = Integer.parseInt(dateStringArray[0]);
        System.out.println("Year: " + year);
        int month = Integer.parseInt(dateStringArray[1]);
        System.out.println("Month: " + month);
        int day = Integer.parseInt(dateStringArray[2].substring(0,2));
        System.out.println("Day: " + dateStringArray[2]);

        localDate = LocalDate.of(year, month, day);

        return localDate;
    }

    @FXML
    void clear(MouseEvent event) {
        clearMessage();
        nameField.setText("");
        dateField.setValue(null);
        addressField.setText("");
        emailField.setText("");
    }

    void clearMessage(){
        if (messageName!=null) messageName.setText(".");
        if (messageDate!=null) messageDate.setText(".");
        if (messageAddress!=null) messageAddress.setText(".");
        if (messageEmail!=null) messageEmail.setText(".");

    }

    boolean hasRole(){
        if ((LoginController.active_role.toLowerCase().trim().equals(User.Role.admin.name()))
                || (LoginController.active_role.toLowerCase().trim().equals(User.Role.classhead.name())))
            return true;
        else return false;
    }

}


