package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IPlanningDao;
import esprit.changemakers.sportshub.dao.IUserDao;
import esprit.changemakers.sportshub.dao.Impl.PlanningDaoImpl;
import esprit.changemakers.sportshub.dao.Impl.UserDaoImpl;
import esprit.changemakers.sportshub.entities.Planning;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionPlanningController implements Initializable {
    @FXML
    private TableView<Planning> idTableView2;

    @FXML
    private TableColumn<Date, Planning> tvcdate2;
    @FXML
    private TableColumn<Integer, Planning> tvcidplanning2;
    @FXML
    private TableColumn<Integer, Planning> tvciduser2;
    @FXML
    private DatePicker dpstartdate;
    @FXML
    private TextField txtiduser;
    @FXML
    private TextField txtidplanning;
    @FXML
    private TextField inputsearch;

    IPlanningDao planningDao = new PlanningDaoImpl();
    IUserDao us =new UserDaoImpl();
    ObservableList<Planning> listEt = FXCollections.observableArrayList();
    private static GestionPlanningController instance;
    public GestionPlanningController() { instance = this; }
    public static GestionPlanningController getInstance() { return instance; }

    public void initialize(URL location, ResourceBundle resources) {

        listEt = planningDao.getAll();
        System.out.println(listEt);
        tvcidplanning2.setCellValueFactory(new PropertyValueFactory<Integer, Planning>("id_planning"));
        tvciduser2.setCellValueFactory(new PropertyValueFactory<Integer, Planning>("id_user"));
        tvcdate2.setCellValueFactory(new PropertyValueFactory<Date, Planning>("planning_date"));
        idTableView2.setItems(listEt);

        FilteredList<Planning> fl = new FilteredList<>(listEt, b -> true);
        inputsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            fl.setPredicate(planning -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String inputsearch = newValue.toLowerCase();
                if (planning.getPlanning_date().toString().indexOf(inputsearch) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Planning> sortedList = new SortedList<>(fl);
        sortedList.comparatorProperty().bind((idTableView2.comparatorProperty()));
        idTableView2.setItems(sortedList);
    }


    public void ajouter() {

        LocalDate pdate = dpstartdate.getValue();
        if (pdate == null || txtiduser.getText().isEmpty() ) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();

        } else if (!txtiduser.getText().matches("[0-9]*")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Planning id must be number");
            err.showAndWait();
        } else if (us.getById(Integer.parseInt(txtiduser.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("User id doesn't exist");
            err.showAndWait();
        } else {

            Planning planning = new Planning( Integer.parseInt(txtiduser.getText()), pdate);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Add this Planning ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                planningDao.save(planning);
                listEt.add(planning);
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Planning added successfully!");
                xx.showAndWait();
            }
        }
    }

    public void supprimer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you Sure you want to delete this Planning ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert xx = new Alert(Alert.AlertType.INFORMATION);
            xx.setContentText("Planning deleted successfully!");
            xx.showAndWait();
        }
        int id_planning = idTableView2.getSelectionModel().getSelectedItem().getId_planning();
        planningDao.deleteById(id_planning);

        for (int i = 0; i < listEt.size(); i++) {
            if (listEt.get(i).getId_planning() == id_planning) {
                listEt.remove(i);
            }
        }
    }

    public void modifier() {
        LocalDate pdate = dpstartdate.getValue();
        if (pdate == null || txtiduser.getText().isEmpty() ) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();
        } else if (!txtiduser.getText().matches("[0-9]*")) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Planning id must be number");
            err.showAndWait();

        } else if (us.getById(Integer.parseInt(txtiduser.getText())) == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("User id doesn't exist");
            err.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to MODIFY this Planning ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Planning Modified successfully!");
                xx.showAndWait();

                Planning planning = planningDao.getById(idTableView2.getSelectionModel().getSelectedItem().getId_planning());
                planning.setPlanning_date(pdate);
                planning.setId_user(Integer.parseInt(txtiduser.getText()));
               // planning.setId_planning(Integer.parseInt(txtidplanning.getText()));
                planningDao.update(planning);

                for (int i = 0; i < listEt.size(); i++) {
                    if (listEt.get(i).getId_planning() == planning.getId_planning()) {
                        listEt.set(i, planning);
                    }
                }


            }
        }
    }
    public void pdf(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
        fc.setTitle("save to pdf");
        fc.setInitialFileName("Planning");


        WritableImage nodeshot = idTableView2.snapshot(new SnapshotParameters(), null);
        File file = new File("Planning.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
        } catch (IOException e) {

        }

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        PDImageXObject pdimage;
        PDPageContentStream content;

        try {
            pdimage = PDImageXObject.createFromFile("Planning.png", doc);
            content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 100, 100);
            content.close();
            doc.addPage(page);
            doc.save("Planning.pdf");
            doc.close();
            file.delete();

        } catch (IOException ex) {
        }



    }
}
