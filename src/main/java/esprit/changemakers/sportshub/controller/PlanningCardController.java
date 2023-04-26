package esprit.changemakers.sportshub.controller;

import esprit.changemakers.sportshub.dao.IPlanninglListener;
import esprit.changemakers.sportshub.entities.Event;
import esprit.changemakers.sportshub.entities.Planning;
import esprit.changemakers.sportshub.services.Impl.EventServiceImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlanningCardController implements Initializable {

    @FXML
    private BorderPane cardPlanning;

    @FXML
    private Label idplanning;

    @FXML
    private Label startdate;
    public int id;

    IPlanninglListener planninglistener;
    private Planning plannings;
    EventServiceImpl es =new EventServiceImpl();
    private static PlanningCardController instance;


    public PlanningCardController() {
        instance = this;
    }

    public static PlanningCardController getInstance() {
        return instance;
    }
    public void AddPlanning(Planning planning, IPlanninglListener planninglistener){
        this.plannings=planning;
        this.planninglistener = planninglistener;
        idplanning.setText(Integer.toString(planning.getId_planning()));
        startdate.setText(planning.getPlanning_date().toString());

    }

    @FXML
    private void click(MouseEvent event) {
        planninglistener.onclickplanninglistener(plannings);
    }




    public void ShowPlanningEvents() throws IOException {
      ObservableList<Event> eventPlanningList =es.getByPlanningId(Integer.parseInt(idplanning.getText()));
        id= Integer.parseInt(idplanning.getText());
        //System.out.println(id);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListViewEvents.fxml"));

        BorderPane pane = loader.load();

        GestionEventsController gestionEventsController = loader.getController();
        gestionEventsController.setIdPlanningEvent(id);



        System.out.println(eventPlanningList);
        HomeController.getInstance().getBorderPane().setCenter(pane);
        GestionEventsController.getInstance().setListevents(eventPlanningList);
        GestionEventsController.getInstance().loadAllEvents();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
