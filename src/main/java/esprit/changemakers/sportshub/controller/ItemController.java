package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IEventDao;
import esprit.changemakers.sportshub.dao.Impl.EventDaoImpl;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.services.IEventService;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemController  {
    @FXML
    public Label desc;

    @FXML
    public Label idplanning;

    @FXML
    public HBox itemC;

    @FXML
    public Label lblend;

    @FXML
    public Label lblst;
    @FXML
    public Label lbl6;
    @FXML
    public Label nbr;
@FXML
public Button btn;

    IEventService es = new EventServiceImpl();

  //  public Stage primaryStage ;
   // public Scene scene;
    //public Parent root;

    // To make ur controller ( borderPane ) accessable from other controller u gotta add these three things ( thank me later )
    private static ItemController instance;
    public ItemController() { instance = this; }
    public static ItemController getInstance() { return instance; }


    public void afficherEvent(){
        PlanningController.getInstance().getPnItems().getChildren().clear();
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/Item.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventinfoController.getInstance().idEvent.setText(lbl6.getText());
        EventinfoController.getInstance().desc.setText(es.getById(Integer.parseInt(lbl6.getText())).getDescription());
        EventinfoController.getInstance().lblstartdate.setText((es.getById(Integer.parseInt(lbl6.getText())).getStart_date()).toString());
        EventinfoController.getInstance().end.setText((es.getById(Integer.parseInt(lbl6.getText())).getEnd_date()).toString());
        EventinfoController.getInstance().nbr.setText(Integer.toString(es.getById(Integer.parseInt(lbl6.getText())).getNombreReservation()));
        EventinfoController.getInstance().price.setText(Integer.toString(es.getById(Integer.parseInt(lbl6.getText())).getFees()));
        EventinfoController.getInstance().imgevent.setImage(new Image(es.getById(Integer.parseInt(lbl6.getText())).getImageUrl()));
        PlanningController.getInstance().getPnlOverview().getChildren().setAll(node);

     /*   PlanningController.getInstance().getHbLabels().getChildren().clear();
        PlanningController.getInstance().getSearch().clear();
        PlanningController.getInstance().getTitle().setText("Event Details");
        PlanningController.getInstance().getScrollPane().setContent(node);
        PlanningController.getInstance().getScrollPane().setFitToWidth(true);
*/
    }


    /*
    IEventDao eventDao = new EventDaoImpl();
/*
    public void affEvent() throws IOException {
        //BorderPane pane = FXMLLoader.load(getClass().getResource("/views/EventInfo.fxml"));
        //borderPaneevent.setCenter(pane);
    }



/*

    public Node[] aff() {
        Node[] nodes = new Node[10];

        for (int i = 0; i < eventDao.getAll().size(); i++) {

            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("/views/Item2.fxml"));
                itemC=(HBox) nodes[i];

               // itemC.getChildren().setAll().;
                System.out.println(itemC.getChildren().contains(desc));


                //give the items some effect
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }

    public void athbetWoujoudek(Event event){
        System.out.println(event);
    }*/

}