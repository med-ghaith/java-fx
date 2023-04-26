package esprit.changemakers.sportshub.controller;
import esprit.changemakers.sportshub.dao.Eventlistener;
import esprit.changemakers.sportshub.entities.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
public class CardEventController {
    @FXML
    public BorderPane cardevent;

    @FXML
    public TextArea descevent;

    @FXML
    public Label enddate;

    @FXML
    public ImageView eventimgcard;

    @FXML
    public Label name;

    @FXML
    public Label nbrreservation;

    @FXML
    public Label prix;

    @FXML
    public Label startdate;

    Eventlistener eventlistener;
    private Event events;
    //Image img = new Image("http://localhost/sports-hub-images/dd.png");
    private static CardEventController instance;
    public CardEventController() {
        instance = this;
    }
    public static CardEventController getInstance() {
        return instance;
    }
    public void AddEvent(Event event, Eventlistener eventlistener){
        //product.getImage().getBinaryStream();
        this.events=event;
        this.eventlistener = eventlistener;
//        labelNom.setText(produit.getDesignation());
        name.setText(event.getCategory());
        startdate.setText(event.getStart_date().toString());
        enddate.setText(event.getEnd_date().toString());
        nbrreservation.setText(Integer.toString(event.getNombreReservation()));
        prix.setText(Integer.toString(event.getFees()));
        descevent.setText(event.getDescription());
        // category.getText()(product.getCategory().toString());
        eventimgcard.setImage(new Image(event.getImageUrl()));
        System.out.println(event.toString());
    }

    @FXML
    private void click(MouseEvent event) {
        eventlistener.onclickeventlistener(events);
    }
}


