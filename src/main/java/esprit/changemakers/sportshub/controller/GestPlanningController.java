package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IPlanningDao;
import esprit.changemakers.sportshub.dao.IPlanninglListener;
import esprit.changemakers.sportshub.dao.Impl.PlanningDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.entities.Planning;
import esprit.changemakers.sportshub.services.IPlanningService;
import esprit.changemakers.sportshub.services.IUserService;
import esprit.changemakers.sportshub.services.Impl.PlanningServiceImpl;
import esprit.changemakers.sportshub.services.Impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestPlanningController implements Initializable {

    ObservableList<Planning> listplanning = FXCollections.observableArrayList();
    IPlanningService ps = new PlanningServiceImpl();
    IUserService us = new UserServiceImpl();
    IPlanningDao planningDao = new PlanningDaoImpl();
    PlanningServiceImpl plannings = new PlanningServiceImpl();
    private IPlanninglListener planninglistener;
    private static GestPlanningController instance;


     public GestPlanningController() {
    instance = this;
    }

    public static GestPlanningController getInstance() {
        return instance;
    }


    @FXML
    private BorderPane gestionplanning;

    @FXML
    public GridPane grid;
    @FXML
    private TextField search;
    @FXML
    private DatePicker startdate;
    ObservableList<Planning> x ;

    private int iduser;
;



    public int id_planning;

    @FXML
    void ajouter() {
        LocalDate start = startdate.getValue();
        if (start == null ) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();


        }else if(startdate.getValue().compareTo(LocalDate.now()) <0 ){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Date must be in the present or future");
            err.showAndWait();
        } else {
            Planning planning = new Planning(iduser, start);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Add this Planning ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Planning added successfully!");
                xx.showAndWait();
                ps.create(planning);
                listplanning.add(planning);
                loadAllPlannings();

            }
            loadAllPlannings();

        }

    }

    @FXML
    void importimg(ActionEvent event) {

    }

    @FXML
    void modifier() {
        LocalDate start = startdate.getValue();

        if (start == null ) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();

        }else if(startdate.getValue().compareTo(LocalDate.now()) <0 ){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Date must be in the present or future");
            err.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Modify this Planning ?");
            Optional<ButtonType> result = alert.showAndWait();

            Planning planning = ps.getById(id_planning);
            planning.setPlanning_date(start);
            ps.update(planning);
            for (int i = 0; i < listplanning.size(); i++) {
                if (listplanning.get(i).getId_planning() == planning.getId_planning()) {
                    listplanning.set(i, planning);
                }
            }
            loadAllPlannings();

        }

    }


    @FXML
    void supprimer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you Sure you want to delete this Planning ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert xx = new Alert(Alert.AlertType.INFORMATION);
            xx.setContentText("Planning deleted successfully!");
            xx.showAndWait();

            planningDao.deleteById(id_planning);

            for (int i = 0; i < listplanning.size(); i++) {
                if (listplanning.get(i).getId_planning() == id_planning) {
                    listplanning.remove(i);
                }
            }

        }
        loadAllPlannings();

    }

    public ObservableList<Planning> init(){
        if (HomeController.getInstance().getIdUser() != 0){
            iduser = HomeController.getInstance().getIdUser();
        }
        listplanning=plannings.getByUserId(iduser);
        System.out.println(listplanning);
        return listplanning;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllPlannings();
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Planning> listplanning = ps.getByUserId(iduser).stream().filter(e -> ( e.getPlanning_date().toString().contains(newValue))).collect(Collectors.toList());
            System.out.println(listplanning);
            grid.getChildren().clear();
           int column = 1;
           int row = 1;
            for (Planning e : listplanning) {
                       FXMLLoader cards = new FXMLLoader();
                cards.setLocation(getClass().getResource("/views/CardPlanning.fxml"));
                try {
                    BorderPane borderPane = cards.load();
                    PlanningCardController planningcard = cards.getController();
                    planningcard.AddPlanning(e, planninglistener);

                    if (column == 4) {
                        column = 1;
                        row++;
                    }
                    grid.add(borderPane, column++, row); //(child,column,row)
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(borderPane, new javafx.geometry.Insets(10));

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
         }
        });
    }


    public void loadAllPlannings() {

        x=init();

        try {
            // TODO
            ShowPlannings();
        } catch (SQLException ex) {
        } catch (IOException ex) {
        }
    }
    private void ShowPlannings() throws SQLException, IOException {

        if (x.size() > 0) {

            //setChosenPlanning(x.get(0));
            planninglistener = new IPlanninglListener() {

                @Override
                public void onclickplanninglistener(Planning planning) {
                    try {
                        setChosenPlanning(planning);
                        id_planning = planning.getId_planning();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            };

        }

        int column = 1;
        int row = 1;
        for (int i = 0; i < x.size(); i++) {

            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("/views/CardPlanning.fxml"));
            try {
                BorderPane borderPane = cards.load();
                PlanningCardController planningcard = cards.getController();
                planningcard.AddPlanning(x.get(i), planninglistener);

                if (column == 4) {
                    column = 1;
                    row++;
                }
                grid.add(borderPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(borderPane, new javafx.geometry.Insets(10));

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }


    private void setChosenPlanning(Planning planning) throws SQLException, IOException {
      //  userid.setText(Integer.toString(planning.getId_user()));
        startdate.setValue(planning.getPlanning_date());
        System.out.println(startdate.getValue());
    }


    public void pdf(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
        fc.setTitle("save to pdf");
        fc.setInitialFileName("Planning");


        WritableImage nodeshot = grid.snapshot(new SnapshotParameters(), null);
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