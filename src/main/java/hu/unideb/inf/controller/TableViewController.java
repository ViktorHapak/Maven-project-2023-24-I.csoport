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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import java.io.IOException;
import java.sql.SQLException;

public class TableViewController {

    Student selectedStudent = new Student();
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

        addStudentIcon.setOnMouseClicked(mouseEvent -> {
            LoginController.active_role = "0";
            changeWindow = new ChangeWindow();
            studentRepository.close();
            changeWindow.changeOnIcon(outIcon,"/fxml/AddStudent.fxml");
        });
        if (!LoginController.active_role.equals("admin"))
        {settingIcon.setVisible(false);}
        else{
            settingIcon.setVisible(true);
        settingIcon.setOnMouseClicked(mouseEvent -> {


        });
        }

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
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);


            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            float margin = 10;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float bottomMargin = 70;
            float yStartNewPage = page.getMediaBox().getHeight() - margin;

            int rowsPerPage = 25;
            int numberOfRows = studentsTable.getItems().size();
            int numberOfPages = numberOfRows / rowsPerPage;

            for (int currentPage = 0; currentPage < numberOfPages + 1; currentPage++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yStartNewPage);

                contentStream.showText("Diák Adatok");
                contentStream.newLineAtOffset(0, -20);
                contentStream.newLineAtOffset(margin, -20);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText("Név");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Születési dátum");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Cím");
                contentStream.newLineAtOffset(180, 0);
                contentStream.showText("Email");
                contentStream.newLineAtOffset(-440, 0);

                contentStream.setLineWidth(1f);
                //contentStream.moveTo(margin, yStart);
                //contentStream.lineTo(margin + tableWidth, yStart);
                //contentStream.stroke();

                int start = currentPage * rowsPerPage;
                int end = Math.min(start + rowsPerPage, numberOfRows);

                for (int row = start; row < end; row++) {
                    yPosition -= 20;

                    contentStream.newLineAtOffset(margin, -20);
                    contentStream.showText("" + studentsTable.getItems().get(row).getId());
                    contentStream.newLineAtOffset(50, 0);
                    contentStream.showText("" + studentsTable.getItems().get(row).getName());
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText("" + studentsTable.getItems().get(row).getBirth());
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText("" + studentsTable.getItems().get(row).getAddress());
                    contentStream.newLineAtOffset(180, 0);
                    contentStream.showText("" + studentsTable.getItems().get(row).getEmail());

                    contentStream.newLineAtOffset(-440, -20); // Visszatérünk az első oszlophoz
                }

                contentStream.endText();
                contentStream.close();

                if (currentPage < numberOfPages) {
                    document.addPage(new PDPage());
                    yStartNewPage = yStart;
                    yPosition = yStart;
                }
            }

            document.save("Studentlist.pdf");
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


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
                            int selectedIndex = getTableRow().getIndex();
                            String email = emailCol.getCellData(selectedIndex);
                            //Student selectedStudent = studentsTable.getItems().get(selectedIndex);
                            studentRepository.deleteByEmail(email);
                        });

                        editIcon.setOnMouseClicked(mouseEvent -> {
                            UpdateStudentController updateStudentController = new UpdateStudentController();
                            int selectedIndex = getTableRow().getIndex();
                            String email = emailCol.getCellData(selectedIndex);
                            Long studentId = studentRepository.findByStudentemail(email).getId();
                            selectedStudent = studentRepository.findById(studentId);

                            // Átadjuk a kiválasztott diákot az updateStudentController-nek
                            updateStudentController.setCurrentStudent(selectedStudent);

                            changeWindow = new ChangeWindow();
                            changeWindow.changeOnIcon(editIcon, "/fxml/UpdateStudent.fxml");


                        });



                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        if (LoginController.active_role.equals("visitor")) {
                            managebtn.getChildren().remove(editIcon); //nem megfelelő jogosultság esetén ezzel a függvénnyel töröljük az ikont
                            managebtn.getChildren().remove(deleteIcon);
                        }

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
