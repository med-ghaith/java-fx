package esprit.changemakers.sportshub.controller;


import esprit.changemakers.sportshub.dao.IEventDao;
import esprit.changemakers.sportshub.dao.Impl.EventDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.services.IEventService;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PlanningController implements Initializable {

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private ImageView pic;
    @FXML
    private HBox hbLabels;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label Title ;

    @FXML
    private AnchorPane bp;

    @FXML
    public TextField search;

    @FXML
    private ChoiceBox<String> cbcateg;

    @FXML
    private ChoiceBox<String> cbdate;

    @FXML
    private ChoiceBox<Integer> cbprix;


    // private AnchorPane x;
    // Stage primaryStage;
    // private double x, y;

    // To make ur controller ( borderPane ) accessable from other controller u gotta add these three things ( thank me later )
    private static PlanningController instance;

    public PlanningController() {
        instance = this;
    }

    public static PlanningController getInstance() {
        return instance;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public VBox getPnItems() {
        return pnItems;
    }

    public void setPnItems(VBox pnItems) {
        this.pnItems = pnItems;
    }

    public HBox getHbLabels() {
        return hbLabels;
    }

    public Pane getPnlOverview() {
        return pnlOverview;
    }

    public void setPnlOverview(Pane pnlOverview) {
        this.pnlOverview = pnlOverview;
    }

    public AnchorPane getBp() {
        return bp;
    }

    public void setHbLabels(HBox hbLabels) {
        this.hbLabels = hbLabels;
    }
    public Label getTitle() {
        return Title;
    }

    public void setTitle(Label Title) {
        this.Title = Title;
    }

    public TextField getSearch() {
        return search;
    }

    public void setSearch(TextField search) {
        this.search = search;
    }

    ObservableList<Event> li = FXCollections.observableArrayList();
    ObservableList<Event> lidesc = FXCollections.observableArrayList();
    ObservableList<Event> liprix = FXCollections.observableArrayList();

    // IEventDao eventDao = new EventDaoImpl();
    IEventService eventService = new EventServiceImpl();
    // Event event1 = eventService.getById(3);
    // Event event2 = eventService.getById(6);
    // Event event3 = eventService.getById(8);
    // ItemController ic =new ItemController(event1);

    public void loadAndGet(Event event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item2.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ItemController.getInstance().idplanning.setText((String.valueOf(event.getFees())));
        ItemController.getInstance().desc.setText(event.getCategory());
        ItemController.getInstance().lblst.setText(event.getStart_date().toString());
        ItemController.getInstance().lblend.setText(event.getEnd_date().toString());
        ItemController.getInstance().nbr.setText(Integer.toString(event.getNombreReservation()));
        ItemController.getInstance().lbl6.setText((String.valueOf(event.getId_event())));
        pnItems.getChildren().add(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<Integer> lprix = FXCollections.observableArrayList();
        ObservableList<String> stdate = FXCollections.observableArrayList();


        li=eventService.getAll();
        for (int i=0;i< li.size();i++){
            items.add(eventService.getAll().get(i).getCategory());
            lprix.add(eventService.getAll().get(i).getFees());
            stdate.add(eventService.getAll().get(i).getStart_date().toString());
            cbcateg.setItems(items);
            cbprix.setItems(lprix);
            cbdate.setItems(stdate);
        }

        for (Event e : eventService.getAll()) {
            loadAndGet(e);
        }
      search.textProperty().addListener((observable, oldValue, newValue) -> {
       List <Event> listevent= eventService.getAll().stream().filter(e -> (e.getDescription().toLowerCase().contains(newValue)||e.getStart_date().toString().contains(newValue)||Integer.toString(e.getFees()).contains(newValue))).collect(Collectors.toList());
       pnItems.getChildren().clear();

          for (Event e : listevent) {
              loadAndGet(e);
          }


      });
cbdate.setOnAction(event -> {
    System.out.println(((ChoiceBox)event.getSource()).getValue());
    lidesc = eventService.getByStartDate(LocalDate.parse(cbdate.getValue()));
    pnItems.getChildren().clear();
    for (Event e : lidesc) {
        loadAndGet(e);
    }

});
        cbprix.setOnAction(event -> {
            System.out.println(((ChoiceBox)event.getSource()).getValue());
            lidesc = eventService.getByPrice(cbprix.getValue());
            pnItems.getChildren().clear();
            for (Event e : lidesc) {
                loadAndGet(e);
            }
        });
        cbcateg.setOnAction(event -> {
            System.out.println(((ChoiceBox)event.getSource()).getValue());
            lidesc = eventService.getByCategorie(cbcateg.getValue());
            pnItems.getChildren().clear();
            for (Event e : lidesc) {
                loadAndGet(e);
            }
        });
}




    public void affreservation() {
        PlanningController.getInstance().getPnItems().getChildren().clear();
        PlanningController.getInstance().getHbLabels().getChildren().clear();
        PlanningController.getInstance().getSearch().clear();

        PlanningController.getInstance().getTitle().setText("Reservations Management");
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/GestionReservation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlanningController.getInstance().getPnItems().getChildren().add(node);
    }

    public void affevent() {
        PlanningController.getInstance().getPnItems().getChildren().clear();
        PlanningController.getInstance().getHbLabels().getChildren().clear();
        PlanningController.getInstance().getSearch().clear();


        PlanningController.getInstance().getTitle().setText("Events Management");

        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/Event.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlanningController.getInstance().getPnItems().getChildren().add(node);
    }

    public void affplanning() {
        PlanningController.getInstance().getPnItems().getChildren().clear();
        PlanningController.getInstance().getHbLabels().getChildren().clear();
        PlanningController.getInstance().getTitle().setText("Planning Management");
        PlanningController.getInstance().getSearch().setOpacity(0);


        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/GestionPlanning.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlanningController.getInstance().getPnItems().getChildren().add(node);
    }

    public void affPlanningPrincipal() {
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/views/Planning.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.getChildren().clear();
        bp.getChildren().setAll(pane);
    }



    public void signout(ActionEvent actionEvent)  {

    }
}


