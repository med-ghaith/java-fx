package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.services.IEventService;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EventinfoController implements Initializable {
    @FXML
    private BorderPane borderPaneevent;

    @FXML
    public TextArea desc;

    @FXML
    public Label end;

    @FXML
    public Label idEvent;

    @FXML
    public ImageView imgevent;

    @FXML
    public Label lblstartdate;

    @FXML
    public Label nbr;

    @FXML
    public Label price;

    private static EventinfoController instance;
    public EventinfoController() { instance = this;}
    public static EventinfoController getInstance() { return instance; }

    public BorderPane getBorderPaneevent() {
        return borderPaneevent;
    }

    public void setBorderPaneevent(BorderPane borderPaneevent) {
        this.borderPaneevent = borderPaneevent;
    }

    public TextArea getDesc() {
        return desc;
    }

    public void setDesc(TextArea desc) {
        this.desc = desc;
    }

    public Label getEnd() {
        return end;
    }

    public void setEnd(Label end) {
        this.end = end;
    }

    public Label getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Label idEvent) {
        this.idEvent = idEvent;
    }

    public ImageView getImgevent() {
        return imgevent;
    }

    public void setImgevent(ImageView imgevent) {
        this.imgevent = imgevent;
    }

    public Label getLblstartdate() {
        return lblstartdate;
    }

    public void setLblstartdate(Label lblstartdate) {
        this.lblstartdate = lblstartdate;
    }

    public Label getNbr() {
        return nbr;
    }

    public void setNbr(Label nbr) {
        this.nbr = nbr;
    }

    public Label getPrice() {
        return price;
    }

    public void setPrice(Label price) {
        this.price = price;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void reservation() {
        PlanningController.getInstance().getPnlOverview().getChildren().clear();
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/Reservation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlanningController.getInstance().getPnlOverview().getChildren().setAll(node);
        PlanningController.getInstance().getTitle().setText("Reservation");
        ReservationController.getInstance().lbl6.setText(idEvent.getText());
        PlanningController.getInstance().getPnlOverview().getChildren().setAll(node);

    }
    public void back() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Planning.fxml"));
       HomeController.getInstance().getBorderPane().setCenter(null);
        //borderPaneevent.getChildren().clear();
        HomeController.getInstance().getBorderPane().setCenter(pane);
       // borderPaneevent.setCenter(pane);
    }
}


