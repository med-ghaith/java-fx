package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IEventDao;
import esprit.changemakers.sportshub.dao.Impl.EventDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.services.IPlanningService;
import esprit.changemakers.sportshub.services.Impl.PlanningServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventController implements Initializable {
    @FXML
    private TableView<Event> idTableView;

    @FXML
    private TableColumn<Date, Event> tvcstartdate;
    @FXML
    private TableColumn<Date, Event> tvcenddate;
    @FXML
    private TableColumn<String, Event> tvcdescription;
    @FXML
    private TableColumn<Integer, Event> tvcidplanning;
    @FXML
    private TableColumn<Integer, Event> tvcidevent;
    @FXML
    private DatePicker dpstartdate;
    @FXML
    private DatePicker dpenddate;
    @FXML
    private TextField txtdescription;
    @FXML
    private TextField txtidplanning;
    @FXML
    private TextField inputsearch;

    IEventDao eventDao = new EventDaoImpl();
    IPlanningService ps = new PlanningServiceImpl();
    ObservableList<Event> listEt = FXCollections.observableArrayList();
    private static EventController instance;

    public EventController() {
        instance = this;
    }

    public static EventController getInstance() {
        return instance;
    }

    public void initialize(URL location, ResourceBundle resources) {

        listEt = eventDao.getAll();
        tvcidevent.setCellValueFactory(new PropertyValueFactory<Integer, Event>("id_event"));
        tvcstartdate.setCellValueFactory(new PropertyValueFactory<Date, Event>("start_date"));
        tvcenddate.setCellValueFactory(new PropertyValueFactory<Date, Event>("end_date"));
        tvcdescription.setCellValueFactory(new PropertyValueFactory<String, Event>("description"));
        tvcidplanning.setCellValueFactory(new PropertyValueFactory<Integer, Event>("planning_id"));
        idTableView.setItems(listEt);
        FilteredList<Event> fl = new FilteredList<>(listEt, b -> true);
        inputsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fl.setPredicate(event -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String inputsearch = newValue.toLowerCase();
                if (event.getDescription().toLowerCase().indexOf(inputsearch) > -1) {
                    return true;
                } else if (event.getStart_date().toString().indexOf(inputsearch) > -1) {
                    return true;

                } else
                    return false;
            });
        });
        SortedList<Event> sortedList = new SortedList<>(fl);
        sortedList.comparatorProperty().bind((idTableView.comparatorProperty()));
        idTableView.setItems(sortedList);
    }


    public void ajouter() {
        LocalDate startdate = dpstartdate.getValue();
        LocalDate enddate = dpenddate.getValue();


        if (startdate == null || enddate == null || txtdescription.getText().isEmpty() || txtidplanning.getText().isEmpty()) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();
        } else if (startdate.compareTo(enddate) > 0) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("event can't have ending date before start date");
            err.showAndWait();
        } else if (!txtidplanning.getText().matches("[0-9]*")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Planning id must be number");
            err.showAndWait();
        } else if (ps.getById(Integer.parseInt(txtidplanning.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Planning doesn't exist");
            err.showAndWait();
        } else {
            /*
           // Event event = new Event(start, end, description.getText(),idPlanningEvent,Integer.parseInt(nbrres.getText()),Integer.parseInt(prix.getText()),categori.getText(),"url");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Add this Event ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Event added successfully!");
                xx.showAndWait();
                eventDao.save(event);
                listEt.add(event);

            }
*/
        }

    }

    public void supprimer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you Sure you want to delete this Event ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert xx = new Alert(Alert.AlertType.INFORMATION);
            xx.setContentText("Event deleted successfully!");
            xx.showAndWait();
            int id_event = idTableView.getSelectionModel().getSelectedItem().getId_event();
            eventDao.deleteById(id_event);

            for (int i = 0; i < listEt.size(); i++) {
                if (listEt.get(i).getId_event() == id_event) {
                    listEt.remove(i);
                }
            }
        }
    }

    public void modifier() {
        LocalDate startdate = dpstartdate.getValue();
        LocalDate enddate = dpenddate.getValue();

        if (startdate == null || enddate == null || txtdescription.getText().isEmpty() || txtidplanning.getText().isEmpty()) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();
        } else if (startdate.compareTo(enddate) > 0) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("event can't have ending date before start date");
            err.showAndWait();
        } else if (!txtidplanning.getText().matches("[0-9]*")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Planning id must be number");
            err.showAndWait();
        } else if (ps.getById(Integer.parseInt(txtidplanning.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Planning doesn't exist");
            err.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Modify this Event ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Event Modified successfully!");
                xx.showAndWait();

                Event event = eventDao.getById(idTableView.getSelectionModel().getSelectedItem().getId_event());
                event.setStart_date(startdate);
                event.setEnd_date(enddate);
                event.setDescription(txtdescription.getText());
                event.setPlanning_id(Integer.parseInt(txtidplanning.getText()));
                eventDao.update(event);
                for (int i = 0; i < listEt.size(); i++) {
                    if (listEt.get(i).getId_event() == event.getId_event()) {
                        listEt.set(i, event);
                    }
                }
            }

        }
    }

    public void pdf(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
        fc.setTitle("save to pdf");
        fc.setInitialFileName("Events");


        WritableImage nodeshot = idTableView.snapshot(new SnapshotParameters(), null);
        File file = new File("Event.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
        } catch (IOException e) {

        }

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        PDImageXObject pdimage;
        PDPageContentStream content;

        try {
            pdimage = PDImageXObject.createFromFile("Event.png", doc);
            content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 100, 100);
            content.close();
            doc.addPage(page);
            doc.save("Event.pdf");
            doc.close();
            file.delete();

        } catch (IOException ex) {
        }



    }

    }


