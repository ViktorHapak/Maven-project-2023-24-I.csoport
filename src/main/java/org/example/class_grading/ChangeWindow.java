package org.example.class_grading;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.stage.StageStyle.UTILITY;


//ablakváltó-osztály
public class ChangeWindow {

    public ChangeWindow() {

    }

    public void change(Button button, String r) {
        button.getScene().getWindow().hide(); //Ablak elrejtése
        FXMLLoader loader = null;
        Parent root = null;
        Stage stage = null;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(r));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root = loader.getRoot(); //útvonal a betöltendő fájlhoz
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.setTitle("I.csoport 2023/24");
        stage.setResizable(false);
        stage.show(); //megmutatja és megvárja, amíg ez az ablak megjelenik

    }

    public void changeOnIcon(FontAwesomeIcon fontAwesomeIcon, String r) {
        fontAwesomeIcon.getScene().getWindow().hide(); //Ablak elrejtése
        FXMLLoader loader = null;
        Parent root = null;
        Stage stage = null;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(r));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root = loader.getRoot(); //útvonal a betöltendő fájlhoz
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.setTitle("Grading App");
        stage.setResizable(false);
        stage.show(); //megmutatja és megvárja, amíg ez az ablak megjelenik

    }

    public void addNewWindow(FontAwesomeIcon fontAwesomeIcon, String r) {
        Stage currentStage = (Stage) fontAwesomeIcon.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(r));

        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.setTitle("Grading App");
        stage.setResizable(false);
        stage.initStyle(UTILITY);
        stage.initOwner(currentStage);
        stage.show();
    }


}
