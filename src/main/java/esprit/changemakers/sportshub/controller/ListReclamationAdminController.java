/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.changemakers.sportshub.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPage;
import esprit.changemakers.sportshub.entities.Reclamation;
import esprit.changemakers.sportshub.services.Impl.ReclamationServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.Notifications;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;


/**
 * @author med
 */
public class ListReclamationAdminController implements Initializable {

    //java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    ReclamationServiceImpl ser = new ReclamationServiceImpl();

    ObservableList<Reclamation> RecList = null;

    @FXML
    TableView<Reclamation> list;

    @FXML
    private TableColumn<Reclamation, Integer> iduser;

    @FXML
    private TableColumn<Reclamation, String> type;

    @FXML
    private TableColumn<Reclamation, String> object;

    @FXML
    private TableColumn<Reclamation, String> status;

    @FXML
    private TableColumn<Reclamation, String> description;

    @FXML
    private TableColumn<Reclamation, Date> date;

    @FXML
    private TextField cherchertxt;

    @FXML
    private TableColumn<Reclamation, String> response;

    @FXML
    private TableColumn<Reclamation, String> delete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Reclamation> RecList = null;
        try {
            RecList = FXCollections.observableArrayList(ser.getAllReclamation());
            list.setItems(RecList);
            list.setTableMenuButtonVisible(false);
            FilteredList<Reclamation> filteredData = new FilteredList<>(RecList, b -> true);

            cherchertxt.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(r -> {
                    // If filter text is empty, display all reclamation.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // lowercase.
                    String lowerCaseFilter = newValue.toUpperCase();

                    if (r.getObject().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches
                    } else if (r.getTypeReclamation().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (r.getStatus().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (r.getDescription().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(r.getDateReclamation()).indexOf(lowerCaseFilter) != -1)
                        return true;


                    else
                        return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Reclamation> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(list.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            list.setItems(sortedData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        System.out.println(ser.getAllReclamation());

        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryU
                =
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                            final Button btn = new Button("Traiter");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {

                                        if (getTableView().getItems().get(getIndex()) == null) {

                                        } else {
                                            //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();


                                            try {
                                                Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());
                                                reclamationSelectione.setStatus("Pending");//reload
                                                System.out.println(reclamationSelectione);
                                                ser.updateReclamation(reclamationSelectione);
                                                ObservableList observableList = FXCollections.observableArrayList(ser.getAllReclamation());
                                                list.setItems(observableList);
                                                //System.out.println(reclamationSelectione.getDescription());
                                                loadUpdateWindow(reclamationSelectione, event);
                                            } catch (Exception ex) {
                                                Logger.getLogger(ListReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
                                                System.out.println(ex.getMessage() + "**************************************************");
                                            }
                                        }
                                        // list();

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                    btn.setStyle(" -fx-base:#F96504;");
                                }
                            }


                        };
                        return cell;
                    }
                };
        //editer update
        response.setCellFactory(cellFactoryU);

        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryU2
                =
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Reclamation, String> param) {
                        final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                            final Button btn = new Button("Delete");

                            //btn.setStyle("-fx-border-color: #ff0000;");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {

                                        if (getTableView().getItems().get(getIndex()) == null) {

                                        } else {
                                            //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();


                                            try {
                                                Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());

                                                System.out.println(reclamationSelectione);
                                                deleteReclamation(reclamationSelectione);
                                                ObservableList observableList = FXCollections.observableArrayList(ser.getAllReclamation());

                                                list.setItems(observableList);
                                                //System.out.println(reclamationSelectione.getDescription());

                                            } catch (Exception ex) {
                                                Logger.getLogger(ListReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
                                                System.out.println(ex.getMessage() + "**************************************************");
                                            }
                                        }
                                        // list();

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                    btn.setStyle(" -fx-base:#F61B0E;");
                                }
                            }


                        };
                        return cell;
                    }
                };
        //editer update
        delete.setCellFactory(cellFactoryU2);

        iduser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeReclamation"));
        object.setCellValueFactory(new PropertyValueFactory<>("object"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
        //list.setItems(ser.getAllReclamation()); 
    }


    private void loadUpdateWindow(Reclamation reclamationSelectione, ActionEvent event) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TraiterReclamationAdminFXML.fxml"));
            Parent root = loader.load();

            TaiterReclamationAdminFXMLController c = loader.getController();
            c.setReclamation(reclamationSelectione);
            DashbordAdminController.getInstance().getBorderPane().setCenter(root);

            //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //Scene scene = new Scene(root);
            // stage.setScene(scene);
            //stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TaiterReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteReclamation(Reclamation reclamationSelectione) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("are you sure you want to delete this reclamation");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);


            if (result.get() == ButtonType.OK) {
                ser.deleteReclamation(reclamationSelectione);
            }
            //oke button is pressed
            else if (result.get() == ButtonType.CANCEL) {
                System.out.println("cancel");
            }
        } catch (Exception e) {
        }
    }


    @FXML
    private void OnClick(ActionEvent event) {
        try {
            ObservableList<Reclamation> list = ser.getAllReclamation();
            //   PDFViewer m_PDFViewer = new PDFViewer();
            //    BorderPane borderPane = new BorderPane(m_PDFViewer);
            Scene scene = new Scene(new Group());
            Stage stage = new Stage();
            stage.setTitle("Type Reclamation stats");
            stage.setWidth(500);
            stage.setHeight(500);
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("FRAUD", list.stream().filter(e -> e.getTypeReclamation().equals("FRAUD")).map(e -> e.getTypeReclamation()).count()),
                            new PieChart.Data("RACISM", list.stream().filter(e -> e.getTypeReclamation().equals("RACISM")).map(e -> e.getTypeReclamation()).count()),
                            new PieChart.Data("FAKEUSER", list.stream().filter(e -> e.getTypeReclamation().equals("FAKEUSER")).map(e -> e.getTypeReclamation()).count()),
                            new PieChart.Data("SCAM", list.stream().filter(e -> e.getTypeReclamation().equals("SCAM")).map(e -> e.getTypeReclamation()).count()),
                            new PieChart.Data("HARASSEMENT", list.stream().filter(e -> e.getTypeReclamation().equals("HARASSEMENT")).map(e -> e.getTypeReclamation()).count()),
                            new PieChart.Data("VIOLENCE", list.stream().filter(e -> e.getTypeReclamation().equals("VIOLENCE")).map(e -> e.getTypeReclamation()).count())
                    );
            final PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Type Reclamation");
            //label
            final Label caption = new Label("");
            caption.setTextFill(Color.DARKORANGE);
            caption.setStyle("-fx-font: 24 arial;");
            for (final PieChart.Data data : chart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                caption.setTranslateX(e.getSceneX());
                                caption.setTranslateY(e.getSceneY());
                                caption.setText(String.valueOf(data.getPieValue()) + " Reclamations");
                            }
                        });
            }
            ((Group) scene.getRoot()).getChildren().add(chart);
            ((Group) scene.getRoot()).getChildren().add(caption);
            Button btn = new Button();
            ((Group) scene.getRoot()).getChildren().add(btn);
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
            fc.setTitle("save to pdf");
            fc.setInitialFileName("Statistique Reclamation");
            btn.setText("save PDF ");
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    WritableImage nodeshot = chart.snapshot(new SnapshotParameters(), null);
                    File file = new File("chart.png");

                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
                    } catch (IOException e) {

                    }

                    PDDocument doc = new PDDocument();
                    PDPage page = new PDPage();
                    PDImageXObject pdimage;
                    PDPageContentStream content;

                    try {
                        pdimage = PDImageXObject.createFromFile("chart.png", doc);
                        content = new PDPageContentStream(doc, page);
                        content.drawImage(pdimage, 100, 100);
                        content.close();
                        doc.addPage(page);
                        doc.save("pdf_file.pdf");
                        doc.close();
                        file.delete();

                        Notifications n = Notifications.create()
                                .title("Success")
                                .text("Operation effectue aves success")
                                .graphic(null)
                                .position(Pos.CENTER)
                                .hideAfter(Duration.seconds(5));
                        n.showConfirm();
                    } catch (IOException ex) {
                        //Logger.getLogger(NodeToPdf.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            Logger.getLogger(ListReclamationUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
