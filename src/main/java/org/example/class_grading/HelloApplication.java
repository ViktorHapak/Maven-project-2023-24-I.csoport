package org.example.class_grading;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.class_grading.model.Subject;
import org.example.class_grading.model.User;
import org.example.class_grading.repository.SubjectRepository;
import org.example.class_grading.repository.UserRepository;
import org.h2.tools.Server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Grading App");
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().setFill(Color.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            startDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        launch(args);
        stopDatabase();
    }

    private static Server s = new Server();

    private static void startDatabase() throws SQLException {
        s.runTool("-tcp", "-web", "-ifNotExists");
    }

    private static void stopDatabase()  {
        s.shutdown();
    }



}