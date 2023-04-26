package esprit.changemakers.sportshub.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowReviewCoachController implements Initializable {


    @FXML
    private Label idComm;

    @FXML
    private Label idc;

    @FXML
    private Label userc;

    @FXML
    private Label datec;

    @FXML
    private Label commentairec;

    @FXML
    private JFXButton deletec;

    @FXML
    private Rating ratec;

    @FXML
    private Label allcomments;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setIdCom(String i){
        idComm.setText(i);
    }

    public void setDate(String d){
        datec.setText(d);
    }
    public void setComment(String com){
        commentairec.setText(com);
    }

    public void setId(String id){
        idc.setText(id);
    }

    public void setRate(float ra){
        ratec.setRating(ra);
    }

    public void setUser(String user){
        userc.setText(user);
    }

    public void deleteRev(ActionEvent event){
        //commentservice.deleteCommentGym(t);
    }
}
