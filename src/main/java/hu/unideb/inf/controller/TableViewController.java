package hu.unideb.inf.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import hu.unideb.inf.ChangeWindow;
import hu.unideb.inf.model.Student;
import hu.unideb.inf.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

public class TableViewController {

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, String> idCol;

    @FXML
    private TableColumn<Student, String> nameCol;

    @FXML
    private TableColumn<Student, String> birthCol;

    @FXML
    private TableColumn<Student, String> adressCol;

    @FXML
    private TableColumn<Student, String> emailCol;

    @FXML
    private TableColumn<Student, String> editCol;

    @FXML
    private FontAwesomeIcon addStudentIcon;

    @FXML
    private FontAwesomeIcon printIcon;

    @FXML
    private FontAwesomeIcon refreshIcon;

    @FXML
    private FontAwesomeIcon settingIcon;

    @FXML
    private FontAwesomeIcon gradesIcon;

    @FXML
    private FontAwesomeIcon galleryIcon;

    @FXML
    private FontAwesomeIcon outIcon;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label roleLabel;

    public TableViewController(){

    }

    StudentRepository studentRepository = new StudentRepository();
    ObservableList<Student> StudentList = FXCollections.observableArrayList();
    ChangeWindow changeWindow = null;


    @FXML
    void initialize () throws SQLException, ClassNotFoundException {

        loadDate();
        usernameLabel.setText(LoginController.active_username);
        roleLabel.setText(LoginController.active_role);


        outIcon.setOnMouseClicked(mouseEvent -> {
            LoginController.active_username = null;
            LoginController.active_role = null;
            changeWindow = new ChangeWindow();
            studentRepository.close();
            changeWindow.changeOnIcon(outIcon,"/fxml/login.fxml");

        });

        gradesIcon.setOnMouseClicked(mouseEvent -> {
            changeWindow = new ChangeWindow();
            studentRepository.close();
            changeWindow.changeOnIcon(outIcon,"/fxml/grades.fxml");
        });
    }

    @FXML
    void getAddView(MouseEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
        //Scene scene = new Scene(root);
        //Stage stage = new Stage();
        //stage.setTitle("TableView");
        //stage.setScene(scene);
        //stage.initStyle(UTILITY);
        //stage.setResizable(false);
        //stage.show();


    }

    @FXML
    void print(MouseEvent event) {


    }

    @FXML
    void refresh(MouseEvent event) throws SQLException {
        refreshTable();
    }

    @FXML
    void refreshTable() throws SQLException {
        StudentList.clear();
        int count = (int) studentRepository.count();

        int I = 0;
        String[] idnumber = new String[count];
        while (I<count) {
            idnumber[I] = String.valueOf(studentRepository.findIds().get(I));
            I++;
        }

        I = 0;
        for(String i :idnumber) {
            I++;
            Student student = studentRepository.findById(Long.valueOf(i));
            StudentList.add(new Student(
                    I,
                    student.getName(),
                    student.getBirth(),
                    student.getAddress(),
                    student.getEmail()
            ));
        }

            studentsTable.setItems(StudentList);
    }

    void loadDate() throws SQLException, ClassNotFoundException {

        refreshTable();

        //set student datatable colums for table colums
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFoctory = (TableColumn<Student, String> param) -> {
            // make cell containing buttons
            final TableCell<Student, String> cell = new TableCell<Student, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);

                    } else {


                        FontAwesomeIcon editIcon = new FontAwesomeIcon();
                        editIcon.setGlyphName("EDIT");
                        editIcon.setSize("16px");
                        editIcon.setFill(Color.GREEN);
                        editIcon.setCursor(Cursor.HAND);
                        FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
                        deleteIcon.setGlyphName("TRASH");
                        deleteIcon.setSize("16px");
                        deleteIcon.setFill(Color.RED);
                        deleteIcon.setCursor(Cursor.HAND);


                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {


                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        //managebtn.getChildren().remove(editIcon); -nem megfelelő jogosultság esetén ezzel a függvénnyel töröljük az ikont
                        //managebtn.getChildren().remove(deleteIcon);


                        setGraphic(managebtn);

                    }
                    setText(null);
                }
            };
            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        studentsTable.setItems(StudentList);


    }





}
