package esprit.changemakers.sportshub.controller;
import esprit.changemakers.sportshub.dao.IEventDao;
import esprit.changemakers.sportshub.dao.Impl.EventDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.services.IEventService;
import esprit.changemakers.sportshub.services.IPlanningService;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import esprit.changemakers.sportshub.dao.Eventlistener;
import esprit.changemakers.sportshub.services.Impl.PlanningServiceImpl;
//import esprit.changemakers.sportshub.services.UploadFileToServer;
import esprit.changemakers.sportshub.services.UploadFileToServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestionEventsController implements Initializable {

    ObservableList<Event> listevents = FXCollections.observableArrayList();
    IEventDao eventDao = new EventDaoImpl();
    IEventService eventService=new EventServiceImpl();
    IPlanningService ps = new PlanningServiceImpl();

    @FXML
    private BorderPane gestionevents;
    public Eventlistener eventlistener;
    @FXML
    private GridPane grid;
    @FXML
    private TextArea description;

    @FXML
    private DatePicker enddate;

    @FXML
    private ImageView eventimage;

    @FXML
    private TextField idplanning;

    @FXML
    private TextField categori;
    @FXML
    private TextField nbrres;
    @FXML
    private TextField prixinput;
    @FXML
    private DatePicker startdate;
    @FXML
    private TextField search;
    @FXML
    private Label idplanningevents;
    File file;
    private int idPlanningEvent;
    //Image img = new Image("http://localhost/sports-hub-images/dd.png");


    // To make ur controller ( borderPane ) accessible from other controller u gotta add these three things ( thank me later )
    private static GestionEventsController instance;
    public GestionEventsController() {
        instance = this;
    }
    public static GestionEventsController getInstance() {
        return instance;
    }

    public int id_event ;
    @FXML
    void ajouter() throws IOException{
        LocalDate start = startdate.getValue();
        LocalDate end = enddate.getValue();


        if (start == null || end == null || categori.getText().isEmpty() || description.getText().isEmpty()|| prixinput.getText().isEmpty() || nbrres.getText().isEmpty() ) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();
        }else if(startdate.getValue().compareTo(LocalDate.now()) <0 ){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Date must be in the present or future");
            err.showAndWait();
        } else if (start.compareTo(end) > 0) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("event can't have ending date before start date");
            err.showAndWait();

        } else {
           UploadFileToServer.uploadFile(file.getAbsolutePath());
            // System.out.println(file.getName());
            Event event = new Event(start, end, description.getText(),idPlanningEvent,Integer.parseInt(nbrres.getText()),Integer.parseInt(prixinput.getText()),categori.getText(),"http://localhost/sports-hub-images/"+file.getName());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Add this Event ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Event added successfully!");
                xx.showAndWait();
                eventDao.save(event);
                listevents.add(event);
loadAllEvents();
            }

        }

    }

    @FXML
    void importimg(ActionEvent event) {
        FileChooser fc = new FileChooser();
        {
            try{
                FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(.jpg)", ".jpg");
                FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("JPEG files(.jpeg)", ".jpeg");
                FileChooser.ExtensionFilter ext3 = new FileChooser.ExtensionFilter("PNG files(.png)", ".png");
                //fc.getExtensionFilters().addAll(ext1, ext2, ext3);

                file = fc.showOpenDialog(AddProductController.getpStage());
                //System.out.println(file.getPath());
                File file1 = new File(file.getPath());
                BufferedImage bf;
                bf = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bf, null);
                eventimage.setImage(image);
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public int getIdPlanningEvent() {
        return idPlanningEvent;
    }

    public void setIdPlanningEvent(int idPlanningEvent) {
        this.idPlanningEvent = idPlanningEvent;
    }

    @FXML
    void modifier() {
        LocalDate start = startdate.getValue();
        LocalDate end = enddate.getValue();

        if (start == null || end == null || categori.getText().isEmpty() || description.getText().isEmpty()|| prixinput.getText().isEmpty() || nbrres.getText().isEmpty() ) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("please fill all the fields");
            err.showAndWait();
        } else if(startdate.getValue().compareTo(LocalDate.now()) <0 ){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Date must be in the present or future");
            err.showAndWait();
        }else if (start.compareTo(end) > 0) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("event can't have ending date before start date");
            err.showAndWait();


        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you Sure you want to Modify this Event ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert xx = new Alert(Alert.AlertType.INFORMATION);
                xx.setContentText("Event Modified successfully!");
                xx.showAndWait();

                Event event = eventDao.getById(id_event);
                event.setStart_date(start);
                event.setEnd_date(end);
                event.setDescription(description.getText());
                event.setPlanning_id(idPlanningEvent);
                event.setNombreReservation(Integer.parseInt(nbrres.getText()));
                event.setFees(Integer.parseInt(prixinput.getText()));
                event.setCategory(categori.getText());
                event.setImageUrl("http://localhost/sports-hub-images/"+file.getName());


                eventDao.update(event);
                for (int i = 0; i < listevents.size(); i++) {
                    if (listevents.get(i).getId_event() == event.getId_event()) {
                        listevents.set(i, event);
                    }
                }
                loadAllEvents();

            }

        }
    }

    @FXML
    void supprimer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you Sure you want to delete this Event ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert xx = new Alert(Alert.AlertType.INFORMATION);
            xx.setContentText("Event deleted successfully!");
            xx.showAndWait();

            eventDao.deleteById(id_event);
            for (int i = 0; i < listevents.size(); i++) {
                if (listevents.get(i).getId_event() == id_event) {
                    listevents.remove(i);
                }

            }
            grid.getChildren().clear();
           loadAllEvents();

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
          //  System.out.println("textfield changed from " + oldValue + " to " + newValue);

            List<Event> listevent= eventService.getAll().stream().filter(e -> (e.getCategory().toLowerCase().contains(newValue)||e.getStart_date().toString().contains(newValue)||Integer.toString(e.getFees()).contains(newValue))).collect(Collectors.toList());
            System.out.println(listevent);
            grid.getChildren().clear();
            int column =1;
            int row =1;
            for (Event e : listevent) {

                FXMLLoader cards = new FXMLLoader();
                cards.setLocation(getClass().getResource("/views/CardEvent.fxml"));
                try {
                    BorderPane borderPane = cards.load();
                    CardEventController eventcard = cards.getController();
                    eventcard.AddEvent(e, eventlistener);
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



    public void loadAllEvents(){
        //idplanningevents.setText(""+idPlanningEvent);
        try {
            // TODO
            ShowEvents();
        } catch (SQLException ex) {
        } catch (IOException ex) {
        }
    }
    private void ShowEvents() throws SQLException, IOException {
        EventServiceImpl events = new EventServiceImpl();

        if (events.getAll().size() > 0) {

           // setChosenEvent(events.getAll().get(0));
            eventlistener = new Eventlistener() {

                @Override
                public void onclickeventlistener(Event event) {
                    try {
                        setChosenEvent(event);
                        id_event=event.getId_event();

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

        //for (int i = 0; i < events.getAll().size(); i++) {
        System.out.println(listevents);
        for (int i = 0; i < listevents.size(); i++) {
            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("/views/CardEvent.fxml"));
            try {
                BorderPane borderPane = cards.load();
                CardEventController eventcard = cards.getController();
                //eventcard.AddEvent(events.getAll().get(i), eventlistener);
                eventcard.AddEvent(listevents.get(i), eventlistener);
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

    public ObservableList<Event> getListevents() {
        return listevents;
    }

    public void setListevents(ObservableList<Event> listevents) {
        this.listevents = listevents;
    }

    private void setChosenEvent(Event event) throws SQLException, IOException {
        description.setText(event.getDescription());
        startdate.setValue(event.getStart_date());
        enddate.setValue(event.getEnd_date());
        categori.setText(event.getCategory());
        nbrres.setText(Integer.toString(event.getNombreReservation()));
        prixinput.setText(Integer.toString(event.getFees()));

        eventimage.setImage(new Image(event.getImageUrl()));
        //UploadFileToServer.uploadFile("");

    }


    public void back(ActionEvent actionEvent) throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getResource("/views/GestPlanning.fxml"));

        // GestPlanningController.getInstance()..clear();
      //  gestionevents.getChildren().clear();
        HomeController.getInstance().getBorderPane().setCenter(null);
        //borderPaneevent.getChildren().clear();
        HomeController.getInstance().getBorderPane().setCenter(pane);

    }
    public void pdf(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
        fc.setTitle("save to pdf");
        fc.setInitialFileName("Events");


        WritableImage nodeshot = grid.snapshot(new SnapshotParameters(), null);
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

