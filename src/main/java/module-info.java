module org.example.class_grading {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires com.h2database;
    requires fontawesomefx;
    requires org.apache.pdfbox;

    opens org.example.class_grading to javafx.fxml;
    opens org.example.class_grading.controller to javafx.fxml;
    opens org.example.class_grading.model;

    exports org.example.class_grading;
}