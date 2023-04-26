package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IPlanningDao;
import esprit.changemakers.sportshub.dao.IReservationDao;
import esprit.changemakers.sportshub.dao.IUserDao;
import esprit.changemakers.sportshub.dao.Impl.PlanningDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.ReservationDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.Planning;
import esprit.changemakers.sportshub.entities.Reservation;
import esprit.changemakers.sportshub.services.IEventService;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GestionReservationController implements Initializable {

    @FXML
    private Button btnajouter;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private TableView<Reservation> idTableView;

    @FXML
    private TextField inputsearch;

    @FXML
    private ChoiceBox<String> status;

    @FXML
    private TableColumn<Integer, Reservation> tvcidevent;

    @FXML
    private TableColumn<Integer, Reservation> tvcidreservation;

    @FXML
    private TableColumn<Integer, Reservation> tvciduser;

    @FXML
    private TableColumn<String, Reservation> tvcstatus;

    @FXML
    private TextField txtidevent;

    @FXML
    private TextField txtidreservation;

    @FXML
    private TextField txtiduser;
    IReservationDao reservationDao = new ReservationDaoImpl();
    IUserDao us =new UserDaoImpl();
    IEventService es=new EventServiceImpl();

    ObservableList<Reservation> listEt = FXCollections.observableArrayList();
    private static GestionReservationController instance;
    public GestionReservationController() { instance = this; }
    public static GestionReservationController getInstance() { return instance; }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Reserved");
        items.add("Available");
        status.setItems(items);
        listEt = reservationDao.getAll();
        System.out.println(listEt);
        tvcidreservation.setCellValueFactory(new PropertyValueFactory<Integer, Reservation>("id_reservation"));
        tvciduser.setCellValueFactory(new PropertyValueFactory<Integer, Reservation>("id_user"));
        tvcidevent.setCellValueFactory(new PropertyValueFactory<Integer, Reservation>("id_event"));
        tvcstatus.setCellValueFactory(new PropertyValueFactory<String, Reservation>("status"));
        idTableView.setItems(listEt);

        FilteredList<Reservation> fl = new FilteredList<>(listEt, b -> true);
        inputsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fl.setPredicate(reservation -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String inputsearch = newValue.toLowerCase();
                if (reservation.getStatus().toString().indexOf(inputsearch) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Reservation> sortedList = new SortedList<>(fl);
        sortedList.comparatorProperty().bind((idTableView.comparatorProperty()));
        idTableView.setItems(sortedList);

    }

    @FXML
    void ajouter() {

        if ( txtiduser.getText().isEmpty() || txtidevent.getText().isEmpty() || status.getValue()== null ){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();

        } else if (us.getById(Integer.parseInt(txtiduser.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("User id doesn't exist");
            err.showAndWait();
        } else if (es.getById(Integer.parseInt(txtidevent.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Event id doesn't exist");
            err.showAndWait();
        } else {
            Reservation reservation = new Reservation( Integer.parseInt(txtidevent.getText()), Integer.parseInt(txtiduser.getText()), status.getValue());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Add this Reservation ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                reservation = reservationDao.save(reservation);
                System.out.println(reservation);
                listEt.add(reservation);
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Reservation added successfully!");
                xx.showAndWait();
            }
        }
    }


    @FXML
    void modifier() {
        if (txtiduser.getText().isEmpty() || txtidevent.getText().isEmpty()||status.getValue().isEmpty()) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();


        } else if (!txtidevent.getText().matches("[0-9]*")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("event id must be number");
            err.showAndWait();
        } else if (!txtiduser.getText().matches("[0-9]*")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("user id must be number");
            err.showAndWait();
        } else if (us.getById(Integer.parseInt(txtiduser.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("User id doesn't exist");
            err.showAndWait();
        } else if (es.getById(Integer.parseInt(txtidevent.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("event id doesn't exist");
            err.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to MODIFY this Reservation ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Planning Modified successfully!");
                xx.showAndWait();

                Reservation reservation = reservationDao.getById(idTableView.getSelectionModel().getSelectedItem().getId_reservation());
//                reservation.setId_reservation(Integer.parseInt(txtidreservation.getText()));
                reservation.setId_user(Integer.parseInt(txtiduser.getText()));
                reservation.setId_event(Integer.parseInt(txtidevent.getText()));
                reservation.setStatus(status.getValue());

                reservationDao.update(reservation);

                for (int i = 0; i < listEt.size(); i++) {
                    if (listEt.get(i).getId_reservation() == reservation.getId_reservation()) {
                        listEt.set(i, reservation);
                    }
                }


            }
        }
    }

    @FXML
    void supprimer() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to delete this reservation ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Reservation deleted successfully!");
                xx.showAndWait();
            }
            int id_reservation = idTableView.getSelectionModel().getSelectedItem().getId_reservation();
            reservationDao.deleteById(id_reservation);

            for (int i = 0; i < listEt.size(); i++) {
                if (listEt.get(i).getId_reservation() == id_reservation) {
                    listEt.remove(i);
                }
            }
        }
    public void pdf(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
        fc.setTitle("save to pdf");
        fc.setInitialFileName("Reservations");


        WritableImage nodeshot = idTableView.snapshot(new SnapshotParameters(), null);
        File file = new File("Reservation.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
        } catch (IOException e) {

        }

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        PDImageXObject pdimage;
        PDPageContentStream content;

        try {
            pdimage = PDImageXObject.createFromFile("Reservations.png", doc);
            content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 100, 100);
            content.close();
            doc.addPage(page);
            doc.save("Reservations.pdf");
            doc.close();
            file.delete();

        } catch (IOException ex) {
        }



    }

    }

